package br.com.vagnux.DynamicsEntity.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsType;

public interface EntitiesFieldsTypeRepository extends JpaRepository<EntitiesFieldsType, String> {

	@Query(value = "SELECT  entities_fields_type.* " + "FROM entities_fields_values "
			+ "join entities_lines on entities_lines.id = entities_fields_values.entities_fields_relationship_id and entities_lines.entities_fields_relationship_id = entities_fields_values.entities_line_id "
			+ "join entities_fields_relantionship on entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id "
			+ "join entities_fields_type on entities_fields_type.id = entities_fields_relantionship.entities_fields_types_id "
			+ "join entities on entities.id = entities_fields_relantionship.entities_id "
			+ "where entities.id = ?1 ", nativeQuery = true)
	List<EntitiesFieldsType> getFieldList(String entityId);

}
