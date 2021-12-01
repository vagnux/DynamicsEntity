package br.com.vagnux.DynamicsEntity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.vagnux.DynamicsEntity.Model.EntitiesLines;

@Repository
public interface EntitiesLinesRepository extends JpaRepository<EntitiesLines, String> { 
   
	@Query(value = "SELECT entities_lines.* FROM entities_lines "
			+ "join entities_fields_relantionship on entities_fields_relantionship.id = entities_lines.entities_fields_relationship_id "
			+ "where "
			+ "entities_fields_relantionship.entities_id = ?1 "
			+ "and entities_lines.id = ?2 ", nativeQuery = true)
	Optional<EntitiesLines> findByEntityLine(String entityId,String lineId);
	
	@Query(value = "SELECT entities_lines.* FROM entities_lines "
			+ "and entities_lines.id = ?2 ", nativeQuery = true)
	Optional<EntitiesLines> findByEntityLine(String lineId);
	
}
