package com.yonyou.aco.exception;

public class AgeDeleteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AgeDeleteException() {
		super();
	}

	public AgeDeleteException(String message) {
		super(message);
	}

	public AgeDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public AgeDeleteException(Throwable cause) {
		super(cause);
	}

	protected AgeDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}