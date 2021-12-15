package br.com.vagnux.DynamicsEntity.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vagnux.DynamicsEntity.Components.KeyValueResponse;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsValues;
import br.com.vagnux.DynamicsEntity.Model.Pages;

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

	@Query(value = "SELECT \n"
			+ "entities_lines.id AS reference,\n"
			+ "entities_fields_type.field_name AS field,\n"
			+ "IFNULL(entities_fields_values.float_value,\n"
			+ "IFNULL(entities_fields_values.integer_value,\n"
			+ "IFNULL(entities_fields_values.local_date_time_value,\n"
			+ "IFNULL(entities_fields_values.string_value,\n"
			+ "NULL)))) AS value \n"
			+ "FROM \n"
			+ "entities_fields_values \n"
			+ "JOIN \n"
			+ "entities_lines ON entities_lines.id = entities_fields_values.entities_fields_relationship_id \n"
			+ "AND entities_lines.entities_fields_relationship_id = entities_fields_values.entities_line_id \n"
			+ "JOIN \n"
			+ "entities_fields_relantionship ON entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id \n"
			+ "JOIN \n"
			+ "entities_fields_type ON entities_fields_type.id = entities_fields_relantionship.entities_fields_types_id \n"
			+ "JOIN \n"
			+ "entities ON entities.id = entities_fields_relantionship.entities_id \n"
			+ "WHERE \n"
			+ "entities_lines.id IN (SELECT \n"
			+ "id\n"
			+ "FROM \n"
			+ "(SELECT \n"
			+ "entities_fields_relationship_id AS id, created_at\n"
			+ "FROM \n"
			+ "entities_fields_values \n"
			+ "WHERE \n"
			+ "string_value LIKE ?1 \n"
			+ "group by entities_fields_relationship_id , created_at\n"
			+ "order by entities_fields_values.created_at asc\n"
			+ "limit ?3 ,50 \n"
			+ ") AS X) \n"
			+ "AND entities.id = ?2 and entities_lines.deleted_at is null \n"
			+ "", nativeQuery = true)
	List<KeyValueResponse> findValuesbyString(String searchTerm, String entityId, Integer pageStart);
	
	
	@Query(value = "SELECT \n"
			+ " round(count(distinct entities_lines.id)/50) as totalElements \n"
			+ "FROM \n"
			+ "entities_fields_values \n"
			+ "JOIN \n"
			+ "entities_lines ON entities_lines.id = entities_fields_values.entities_fields_relationship_id \n"
			+ "AND entities_lines.entities_fields_relationship_id = entities_fields_values.entities_line_id \n"
			+ "JOIN \n"
			+ "entities_fields_relantionship ON entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id \n"
			+ "JOIN \n"
			+ "entities_fields_type ON entities_fields_type.id = entities_fields_relantionship.entities_fields_types_id \n"
			+ "JOIN \n"
			+ "entities ON entities.id = entities_fields_relantionship.entities_id \n"
			+ "WHERE \n"
			+ "entities_lines.id IN (SELECT \n"
			+ "* \n"
			+ "FROM \n"
			+ "(SELECT \n"
			+ "entities_fields_relationship_id \n"
			+ "FROM \n"
			+ "entities_fields_values \n"
			+ "WHERE \n"
			+ "string_value LIKE ?1 \n"
			+ "group by entities_fields_relationship_id \n"
			+ ") AS X) \n"
			+ "AND entities.id = ?2 and entities_lines.deleted_at is null \n"
			+ "", nativeQuery = true)
	Long findValuesbyStringTotalPage(String searchTerm, String entityId);
	
}
