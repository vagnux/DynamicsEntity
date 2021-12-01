package br.com.vagnux.DynamicsEntity.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.DynamicsEntity.Exceptions.EntityException;
import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Model.Entities;
import br.com.vagnux.DynamicsEntity.Services.EntitiesServices;

@RestController
@RequestMapping(value = "/entity")
public class EntitiesController {

	@Autowired
	private EntitiesServices entitiesServices;

	public EntitiesController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping
	public ResponseEntity<List<Entities>> findAll() {
		List<Entities> list = entitiesServices.findAll();
		return ResponseEntity.ok().body(list);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Entities> getEntity(@PathVariable String id) {
		Optional<Entities> entity = entitiesServices.findById(id);
		if ( entity.isPresent()) {
			return ResponseEntity.ok().body(entity.get());
		}else {
			return ((BodyBuilder) ResponseEntity.notFound()).body(null);
		}

	}
	
	@PostMapping
	public ResponseEntity<HashMap<String, String>> newValues(@RequestBody HashMap<String, Object> values) {
		HashMap<String, String> response = new HashMap<String, String>();
		
		try {
			String resp = entitiesServices.save(values);
			response.put(values.get("name").toString(), "Data saved");
		} catch (EntityException e) {
			response.put(values.get("name").toString(),e.toString() );
		}
		
		if (response.containsValue("Data saved")) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

}
