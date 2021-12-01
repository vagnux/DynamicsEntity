package br.com.vagnux.DynamicsEntity.Repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vagnux.DynamicsEntity.Components.KeyValueResponse;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsRelantionship;

public interface EntitiesFieldsRelationshipRepository extends JpaRepository<EntitiesFieldsRelantionship, String> {

	@Query(value = "SELECT  entities_fields_relantionship.* FROM entities_fields_relantionship join  entities_fields_type on  entities_fields_type.id = entities_fields_relantionship.entities_fields_types_id 	where entities_fields_relantionship.entities_id = ?1 ", nativeQuery = true)
	List<EntitiesFieldsRelantionship> getListFiedId(String entityId);

}
