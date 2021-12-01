package br.com.vagnux.DynamicsEntity.Components;

public class GenericResponse<T> {
    private String message;
    private String error;
    private T data;
 
    public GenericResponse(String message) {
        super();
        this.message = message;
    }
 
    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }
    
    public GenericResponse(String message, String error, T data) {
        super();
        this.message = message;
        this.error = error;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
}