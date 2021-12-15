package br.com.vagnux.DynamicsEntity.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vagnux.DynamicsEntity.Components.KeyValueResponse;
import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Interfaces.EntityInterface;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsRelantionship;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsValues;
import br.com.vagnux.DynamicsEntity.Model.EntitiesLines;
import br.com.vagnux.DynamicsEntity.Model.Pages;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsRelationshipRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsTypeRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsValuesRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesLinesRepository;

@Component
public class SearchServices implements EntityInterface {

	@Autowired
	EntitiesFieldsValuesRepository entitiesFieldsValuesRepository;

	@Autowired
	EntitiesLinesRepository entitiesLinesRepository;

	@Autowired
	EntitiesFieldsRelationshipRepository entitiesFieldsRelationshipRepository;

	@Autowired
	EntitiesFieldsTypeRepository entitiesFieldsTypeRepository;

	public SearchServices() {
		// TODO Auto-generated constructor stub
	}

	public Long getTotalPages(String entityId, String searchTerm) {

		Long pages = entitiesFieldsValuesRepository.findValuesbyStringTotalPage(searchTerm, entityId);
		return pages;
	}

	public HashMap<String, HashMap<String, String>> searchValues(String entityId, Integer pageNumber,
			ArrayList<String> fieldList, String searchString) {

		searchString = "%" + searchString + "%";
		if (pageNumber > 1) {
			pageNumber = pageNumber * 50;
		}

		List<KeyValueResponse> list = entitiesFieldsValuesRepository.findValuesbyString(searchString, entityId,
				pageNumber);

		HashMap<String, HashMap<String, String>> lines = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			KeyValueResponse kr = list.get(i);

			if (!lines.containsKey(kr.getReference())) {
				HashMap<String, String> colunn = new HashMap<>();
				if (fieldList.contains(kr.getField()) == true || fieldList.size() == 0) {
					colunn.put(kr.getField(), kr.getValue());
					lines.put(kr.getReference(), colunn);

				}
				colunn = null;

			} else {
				HashMap<String, String> colunn = lines.get(kr.getReference());
				if (colunn.containsKey(kr.getField())) {
					colunn.replace(kr.getField(), kr.getValue());
				} else {
					if (fieldList.contains(kr.getField()) == true || fieldList.size() == 0) {
						colunn.put(kr.getField(), kr.getValue());
					}
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
