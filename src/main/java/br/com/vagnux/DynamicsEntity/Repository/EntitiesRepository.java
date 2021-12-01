package br.com.vagnux.DynamicsEntity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vagnux.DynamicsEntity.Model.Entities;

@Repository
public interface EntitiesRepository extends JpaRepository<Entities, String> { 
   
	
	
}
