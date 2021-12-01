package br.com.vagnux.DynamicsEntity.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Services.EntitiesFieldsValuesServices;
import br.com.vagnux.DynamicsEntity.Services.EntitiesServices;

@RestController
@RequestMapping(value = "/values")
public class EntitiesFieldsValuesController {

	@Autowired
	private EntitiesFieldsValuesServices entitiesFieldsValuesServices;

	@Autowired
	private EntitiesServices entitiesServices;

	public EntitiesFieldsValuesController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, HashMap<String, String>>> getValues(@PathVariable String id) {

		Map<String, HashMap<String, String>>  values = entitiesFieldsValuesServices.findValuesByEntityId(id);

		return ResponseEntity.ok().body(values);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HashMap<String, String>> replaceValues(@PathVariable String id,
			@RequestBody HashMap<String, Object> values) {

		HashMap<String, String> response = new HashMap<String, String>();
		values.forEach((entityId, lineObject) -> {
			if (!Optional.of(entitiesServices.findById(entityId)).isEmpty()) {
				try {
					entityId = entitiesFieldsValuesServices.saveLine(id, entityId, lineObject);
					response.put(entityId, "Data saved");
				} catch (FieldsValuesException e) {
					response.put(entityId, e.getMessage());
				}
			} else {
				response.put(entityId, "Entity not exist");
			}
		});

		if (response.containsValue("Data saved")) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping
	public ResponseEntity<HashMap<String, String>> newValues(@RequestBody HashMap<String, Object> values) {
		HashMap<String, String> response = new HashMap<String, String>();
		values.forEach((entityId, lineObject) -> {
			if (!Optional.of(entitiesServices.findById(entityId)).isEmpty()) {
				try {
					entityId = entitiesFieldsValuesServices.saveLine(entityId, lineObject);
					response.put(entityId, "Data saved");
				} catch (FieldsValuesException e) {
					response.put(entityId, e.getMessage());
				}
			} else {
				response.put(entityId, "Entity not exist");
			}
		});

		if (response.containsValue("Data saved")) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HashMap<String, String>> deleteValue(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<String, String>();
		try {
			Boolean resp = entitiesFieldsValuesServices.deleteLine(id);
			response.put(id, "line deleted");
		} catch (FieldsValuesException e) {
			response.put(id, e.getMessage());
		}
		if (response.containsValue("Data saved")) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

}
