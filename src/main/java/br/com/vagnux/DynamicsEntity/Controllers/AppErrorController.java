package br.com.vagnux.DynamicsEntity.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.vagnux.DynamicsEntity.Components.GenericResponse;

@Controller
public class AppErrorController implements ErrorController {

	@RequestMapping("/error")
	public ResponseEntity<?> handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		String returnMessage;
		String errorCode;
		if (exception == null) {
			if (statusCode == 401) {
				returnMessage = "Access Denied";
				errorCode = statusCode.toString();
			} else {
				returnMessage = "System Panic! ";
				errorCode = "666";
			}
		} else {
			returnMessage = exception.getMessage();
			errorCode = statusCode.toString();
		}
		GenericResponse<?> resp = new GenericResponse<Object>(returnMessage, errorCode);
		return ResponseEntity.ok().body(resp);
	}

	public String getErrorPath() {
		return "/error";
	}

}