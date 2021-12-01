package br.com.vagnux.DynamicsEntity.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vagnux.DynamicsEntity.Components.KeyValueResponse;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsValues;

public interface EntitiesFieldsValuesRepository extends JpaRepository<EntitiesFieldsValues, String> {

	@Query(value = "SELECT  entities_lines.id as reference,  entities_fields_type.field_name as field ,  ifnull(entities_fields_values.float_value,  ifnull(entities_fields_values.integer_value, ifnull ( entities_fields_values.local_date_time_value, ifnull (entities_fields_values.string_value, null)))) as value "
			+ "FROM entities_fields_values "
			+ "join entities_lines on entities_lines.id = entities_fields_values.entities_fields_relationship_id and entities_lines.entities_fields_relationship_id = entities_fields_values.entities_line_id "
			+ "join entities_fields_relantionship on entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id "
			+ "join entities_fields_type on entities_fields_type.id = entities_fields_relantionship.entities_fields_types_id "
			+ "join entities on entities.id = entities_fields_relantionship.entities_id "
			+ "where entities.id = ?1 ", nativeQuery = true)
	List<KeyValueResponse> findValuesByEntityId(String entityId);

	@Query(value = "SELECT entities_fields_values.*\n" + "FROM entities_fields_values\n"
			+ "join entities_lines   on entities_lines.entities_fields_relationship_id  = entities_fields_values.entities_line_id \n"
			+ "and entities_fields_values.entities_fields_relationship_id = entities_lines.id\n"
			+ "join entities_fields_relantionship on entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id \n"
			+ "join entities_fields_type eft on eft.id = entities_fields_relantionship.entities_fields_types_id \n"
			+ "where\n" + "entities_fields_relantionship.entities_id = ?1\n" + "and entities_lines.id = ?2\n"
			+ "and eft.field_name = ?3\n" + "limit 1", nativeQuery = true)
	Optional<EntitiesFieldsValues> findEntitiesFieldsValues(String entityId, String lineId, String fieldName);
}