package br.com.vagnux.DynamicsEntity.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsType;
import br.com.vagnux.DynamicsEntity.Services.EntitiesFieldsTypesServices;

@RestController
@RequestMapping(value = "/fieldstypes")
public class EntitiesFieldsTypesController {

	@Autowired
	EntitiesFieldsTypesServices entitiesFieldsTypesServices;

	public EntitiesFieldsTypesController() {

	}

	@GetMapping
	public ResponseEntity<List<EntitiesFieldsType>> findAll() {
		List<EntitiesFieldsType> list = entitiesFieldsTypesServices.findAll();
		return ResponseEntity.ok().body(list);

	}
	
	@PostMapping
	public ResponseEntity<HashMap<String, String>> newValues(@RequestBody HashMap<String, String> values) {
		HashMap<String, String> response = new HashMap<String, String>();
		
		try {
			String responseSave = entitiesFieldsTypesServices.saveField(values);
			response.put(values.get("FieldName"), "Data saved");
		} catch (FieldsValuesException e) {
			
			response.put(values.get("FieldName"),e.toString() );
		}	
		
		
		if (response.containsValue("Data saved")) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

}
