package br.com.vagnux.DynamicsEntity.Services;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Interfaces.EntityInterface;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsType;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsTypeRepository;

@Component
public class EntitiesFieldsTypesServices implements EntityInterface {

	@Autowired
	EntitiesFieldsTypeRepository entitiesFieldsTypeRepository;

	public EntitiesFieldsTypesServices() {
		// TODO Auto-generated constructor stub
	}

	public List<EntitiesFieldsType> findAll() {
		return entitiesFieldsTypeRepository.findAll();
	}

	public String saveField(HashMap<String, String> field) throws FieldsValuesException {

		EntitiesFieldsType fieldType = new EntitiesFieldsType();
		fieldType.setId(UUID.randomUUID().toString());

		fieldType.setFieldName(field.get("FieldName").trim());
		fieldType.setFieldregexValidation(field.get("FieldRegexValidation").trim());
		fieldType.setFieldRequired(Boolean.parseBoolean(field.get("FieldRequired").trim()));
		fieldType.setFieldType("String");

		if (field.get("FieldType").trim().equals("Integer")) {
			fieldType.setFieldType("Int");
		}
		if (field.get("FieldType").trim().equals("Float") || field.get("FieldType").trim().equals("Real")
				|| field.get("FieldRequired").trim().equals("Decimal")) {
			fieldType.setFieldType("Float");
		}
		if (field.get("FieldType").trim().equals("DateTime") || field.get("FieldType").trim().equals("Date")) {
			fieldType.setFieldType("DateTime");
		}
		entitiesFieldsTypeRepository.save(fieldType);

		return "Data saved";
	}

}
