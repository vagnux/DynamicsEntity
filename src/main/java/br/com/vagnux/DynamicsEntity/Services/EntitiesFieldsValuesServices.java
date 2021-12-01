package br.com.vagnux.DynamicsEntity.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.vagnux.DynamicsEntity.Components.KeyValueResponse;
import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Interfaces.EntityInterface;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsRelantionship;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsValues;
import br.com.vagnux.DynamicsEntity.Model.EntitiesLines;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsRelationshipRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsTypeRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsValuesRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesLinesRepository;

@Component
public class EntitiesFieldsValuesServices implements EntityInterface {

	@Autowired
	EntitiesFieldsValuesRepository entitiesFieldsValuesRepository;

	@Autowired
	EntitiesLinesRepository entitiesLinesRepository;

	@Autowired
	EntitiesFieldsRelationshipRepository entitiesFieldsRelationshipRepository;

	@Autowired
	EntitiesFieldsTypeRepository entitiesFieldsTypeRepository;

	public EntitiesFieldsValuesServices() {
		// TODO Auto-generated constructor stub
	}

	public Boolean deleteLine(String lineId) throws FieldsValuesException {
		Optional<EntitiesLines> lineFind = entitiesLinesRepository.findByEntityLine(lineId);
		if (lineFind.isEmpty()) {
			throw new FieldsValuesException("field or line not exist");
		}
		EntitiesLines line = lineFind.get();
		line.setDeletedAt(LocalDateTime.now());
		entitiesLinesRepository.save(line);
		return true;
	}

	public String saveLine(String line, String entityId, Object keyAndValues) throws FieldsValuesException {

		ArrayList<EntitiesFieldsValues> fields = new ArrayList<EntitiesFieldsValues>();

		List<EntitiesFieldsRelantionship> fieldList = this.geFieldList(entityId);
		HashMap<String, String> postLines = (HashMap<String, String>) this.convertObjectToHash(keyAndValues);

		for (EntitiesFieldsRelantionship mapper : fieldList) {

			if (postLines.get(mapper.getEntitiesFieldsType().getFieldName()) != null) {

				this.validFieldValues(mapper.getEntitiesFieldsType().getFieldregexValidation(),
						mapper.getEntitiesFieldsType().getFieldName(),
						postLines.get(mapper.getEntitiesFieldsType().getFieldName()));

				Optional<EntitiesFieldsValues> colSearch = entitiesFieldsValuesRepository
						.findEntitiesFieldsValues(entityId, line, mapper.getEntitiesFieldsType().getFieldName());
				if (colSearch.isEmpty()) {
					throw new FieldsValuesException("field or line not exist");
				}

				EntitiesFieldsValues valueCol = colSearch.get();
				if (!valueCol.getStringValue().equals(postLines.get(mapper.getEntitiesFieldsType().getFieldName()))) {

					valueCol = this.setRightDataInput(valueCol,
							postLines.get(mapper.getEntitiesFieldsType().getFieldName()),
							mapper.getEntitiesFieldsType().getFieldType());

					fields.add(valueCol);
				}
				valueCol = null;
				colSearch = null;
			} else if (mapper.getEntitiesFieldsType().isFieldRequired()) {
				throw new FieldsValuesException(
						"value of " + mapper.getEntitiesFieldsType().getFieldName() + " is required");
			}

		}

		entitiesFieldsValuesRepository.saveAll(fields);
		return line;
	}

	public String saveLine(String entityId, Object keyAndValues) throws FieldsValuesException {
		String line = UUID.randomUUID().toString();
		ArrayList<EntitiesFieldsValues> fields = new ArrayList<EntitiesFieldsValues>();

		List<EntitiesFieldsRelantionship> fieldList = this.geFieldList(entityId);
		HashMap<String, String> postLines = (HashMap<String, String>) this.convertObjectToHash(keyAndValues);

		for (EntitiesFieldsRelantionship mapper : fieldList) {

			if (postLines.get(mapper.getEntitiesFieldsType().getFieldName()) != null) {

				this.validFieldValues(mapper.getEntitiesFieldsType().getFieldregexValidation(),
						mapper.getEntitiesFieldsType().getFieldName(),
						postLines.get(mapper.getEntitiesFieldsType().getFieldName()));

				EntitiesLines col = new EntitiesLines(mapper, line);
				entitiesLinesRepository.save(col);
				EntitiesFieldsValues valueCol = this.setRightDataInput(col, UUID.randomUUID().toString(),
						postLines.get(mapper.getEntitiesFieldsType().getFieldName()),
						mapper.getEntitiesFieldsType().getFieldType());
				col = null;
				fields.add(valueCol);
			} else if (mapper.getEntitiesFieldsType().isFieldRequired()) {
				throw new FieldsValuesException(
						"value of " + mapper.getEntitiesFieldsType().getFieldName() + " is required");
			}

		}

		entitiesFieldsValuesRepository.saveAll(fields);
		return line;
	}

	public HashMap<String, HashMap<String, String>>findValuesByEntityId(String entityId) {
		List<KeyValueResponse> list = entitiesFieldsValuesRepository.findValuesByEntityId(entityId);

		HashMap<String, HashMap<String, String>> lines = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			KeyValueResponse kr = list.get(i);

			if (!lines.containsKey(kr.getReference())) {
				HashMap<String, String> colunn = new HashMap<>();
				colunn.put(kr.getField(), kr.getValue());
				lines.put(kr.getReference(), colunn);
				colunn = null;
			} else {
				HashMap<String, String> colunn = lines.get(kr.getReference());
				if (colunn.containsKey(kr.getField())) {
					colunn.replace(kr.getField(), kr.getValue());
				} else {
					colunn.put(kr.getField(), kr.getValue());
				}
				lines.replace(kr.getReference(), colunn);
				colunn = null;
			}
		}

		return lines;
	}




	private EntitiesFieldsValues setRightDataInput(EntitiesFieldsValues valueCol, String value, String type) {

		if (type.contentEquals("int") || type.contentEquals("integer")) {
			valueCol.setIntegerValue(Integer.parseInt(value));
			return valueCol;
		}
		if (type.contentEquals("float") || type.contentEquals("decimal") || type.contentEquals("real")) {
			valueCol.setFloatValue(Float.parseFloat(value));
			return valueCol;
		}
		if (type.contentEquals("LocalDateTime") || type.contentEquals("date") || type.contentEquals("datetime")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			valueCol.setLocalDateTimeValue(LocalDateTime.parse(value, formatter));
			return valueCol;
		}
		valueCol.setStringValue(value);
		return valueCol;

	}

	private EntitiesFieldsValues setRightDataInput(EntitiesLines col, String uuid, String value, String type) {

		if (type.contentEquals("int") || type.contentEquals("integer")) {
			return new EntitiesFieldsValues(col, uuid, null, Integer.parseInt(value), null, null);
		}
		if (type.contentEquals("float") || type.contentEquals("decimal") || type.contentEquals("real")) {
			return new EntitiesFieldsValues(col, uuid, null, null, Float.parseFloat(value), null);
		}
		if (type.contentEquals("LocalDateTime") || type.contentEquals("date") || type.contentEquals("datetime")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return new EntitiesFieldsValues(col, uuid, null, null, null, LocalDateTime.parse(value, formatter));
		}

		return new EntitiesFieldsValues(col, uuid, value, null, null, null);

	}

	private List<EntitiesFieldsRelantionship> geFieldList(String entityId) throws FieldsValuesException {
		List<EntitiesFieldsRelantionship> fieldList = entitiesFieldsRelationshipRepository.getListFiedId(entityId);
		if (fieldList.isEmpty()) {
			throw new FieldsValuesException("Invalid entity");
		}
		return fieldList;
	}

	private void validFieldValues(String regex, String fieldName, String value) throws FieldsValuesException {
		boolean validInput = true;
		if (regex != null && regex.trim().length() > 0 && value.length() > 0) {
			validInput = Pattern.compile(regex).matcher(value).matches();
		}
		if (validInput != true) {
			throw new FieldsValuesException("value of " + fieldName + " is not valid");
		}
	}

	private Map<String, String> convertObjectToHash(Object fiedObject) throws FieldsValuesException {

		String obj = fiedObject.toString();
		obj = obj.replace("{", "");
		obj = obj.replace("}", "");

		Map<String, String> map = Arrays.stream(obj.split(",")).map(entry -> entry.split("="))
				.collect(Collectors.toMap(entry -> entry[0].trim(), entry -> entry[1]));

		if (map.isEmpty()) {
			throw new FieldsValuesException("No values sended");
		}

		return map;

	}

}
