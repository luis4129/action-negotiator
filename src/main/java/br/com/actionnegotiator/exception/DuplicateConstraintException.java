package br.com.actionnegotiator.exception;

public class DuplicateConstraintException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateConstraintException(String message) {
		super(message);
	}

}
