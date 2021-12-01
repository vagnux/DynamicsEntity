package br.com.vagnux.DynamicsEntity.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vagnux.DynamicsEntity.Exceptions.EntityException;
import br.com.vagnux.DynamicsEntity.Interfaces.EntityInterface;
import br.com.vagnux.DynamicsEntity.Model.Entities;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsRelantionship;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsType;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsRelationshipRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsTypeRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesRepository;

@Component
public class EntitiesServices implements EntityInterface {

	@Autowired
	EntitiesRepository entityRepository;

	@Autowired
	EntitiesFieldsTypeRepository entitiesFieldsTypeRepository;

	@Autowired
	private EntitiesFieldsRelationshipRepository entitiesFieldsRelationshipRepository;

	public EntitiesServices() {
		// TODO Auto-generated constructor stub
	}

	public List<Entities> findAll() {
		return entityRepository.findAll();
	}

	public Optional<Entities> findById(String id) {
		Optional<Entities> entity = entityRepository.findById(id);
		return entity;
	}

	public String save(HashMap<String, Object> values) throws EntityException {

		List<String> fields = this.convertObjectToList(values.get("fields"));

		Entities entity = new Entities(UUID.randomUUID().toString(), values.get("name").toString());
		entityRepository.save(entity);

		for (int i = 0; i < fields.size(); i++) {
			String fieldId = fields.get(i);
			Optional<EntitiesFieldsType> fieldTypeSearched = entitiesFieldsTypeRepository.findById(fieldId);
			if (fieldTypeSearched.isEmpty()) {
				throw new EntityException("Invalid field code");
			}
			EntitiesFieldsType fieldType = fieldTypeSearched.get();
			EntitiesFieldsRelantionship entityField = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(),
					entity, fieldType);
			entitiesFieldsRelationshipRepository.save(entityField);
			entityField = null;
			fieldType = null;
			fieldTypeSearched = null;
			fieldId = null;
		}

		return values.get("name").toString();
	}

	private List<String> convertObjectToList(Object fiedObject) throws EntityException {

		String obj = fiedObject.toString();
		obj = obj.replace("[", "");
		obj = obj.replace("]", "");
		obj = obj.replace("\"", "");
		obj = obj.replace(" ", "");

		List<String> map = new ArrayList<String>(Arrays.asList(obj.split(",")));

		if (map.isEmpty()) {
			throw new EntityException("No fields sended");
		}

		return map;

	}

}
