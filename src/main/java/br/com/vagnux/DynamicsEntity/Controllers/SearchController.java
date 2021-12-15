package br.com.vagnux.DynamicsEntity.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vagnux.DynamicsEntity.Exceptions.FieldsValuesException;
import br.com.vagnux.DynamicsEntity.Exceptions.SearchException;
import br.com.vagnux.DynamicsEntity.Services.SearchServices;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

	@Autowired
	private SearchServices searchServices;

	public SearchController() {
		// TODO Auto-generated constructor stub
	}

	private String getValuesString(HashMap<String, Object> values, String key) throws SearchException {
		try {
			if (values.containsKey(key)) {
				return (String) values.get(key);
			}
			return null;
		} catch (Exception e) {
			throw new SearchException("Invalid " + key + " value");
		}
	}

	private Integer getValuesInteger(HashMap<String, Object> values, String key) throws SearchException {
		try {
			if (values.containsKey(key)) {
				return (Integer) values.get(key);
			}
			return 1;
		} catch (Exception e) {
			throw new SearchException("Invalid " + key + " value");
		}
	}

	private Boolean getValuesBoolean(HashMap<String, Object> values, String key) throws SearchException {
		try {
			if (values.containsKey(key)) {
				return (Boolean) values.get(key);
			}
			return false;
		} catch (Exception e) {
			throw new SearchException("Invalid " + key + " value");
		}
	}

	private ArrayList<String> getValuesArray(HashMap<String, Object> values, String key) {
		ArrayList<String> fields = new ArrayList<>();
		if (values.containsKey(key)) {
			@SuppressWarnings("unchecked")
			// List<Map<Object, Object>> fieldList = (List<Map<Object, Object>>)
			// values.get(key);
			List<Object> fieldList = (List<Object>) values.get(key);

			for (int i = 0; i < fieldList.size(); i++) {
				fields.add(fieldList.get(i).toString());
			}

		}
		return fields;
	}

	@PostMapping
	public ResponseEntity<HashMap<String, Object>> search(@RequestBody HashMap<String, Object> values) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String entityId;
		String searchString;
		Integer page =1;
		ArrayList<String> fieldList = new ArrayList<String>();
		try {
			 entityId = this.getValuesString(values, "entityId");
			 searchString = this.getValuesString(values, "searchString");
			 page = this.getValuesInteger(values, "page");
			fieldList = this.getValuesArray(values, "fields");
		} catch (SearchException e) {
			response.put("Error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		try {
			Boolean totalElements = this.getValuesBoolean(values, "totalElements");
			if (totalElements == true) {
				Long totalPages = searchServices.getTotalPages(entityId, searchString);
				response.put("totalElements", totalPages);
			}

			HashMap<String, HashMap<String, String>> result = searchServices.searchValues(entityId, page, fieldList,
					searchString);

			response.put("result", result);

			// response.put(values.get("name").toString(), "Data saved");
		} catch (Exception e) {
			response.put("Error", e.toString());
			return ResponseEntity.badRequest().body(response);

		}

		return ResponseEntity.ok().body(response);

	}

}
