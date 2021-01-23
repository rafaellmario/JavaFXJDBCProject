package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException  extends RuntimeException {
	// serialization 
	private static final long serialVersionUID = 1L;
	
	// Attributes 
	private Map<String, String> errors = new HashMap<>();
	
	// Constructors
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors(){
		return this.errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		this.errors.put(fieldName, errorMessage);
	}
	
	
}
