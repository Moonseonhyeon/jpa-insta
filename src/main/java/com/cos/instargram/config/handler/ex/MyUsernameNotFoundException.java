package com.cos.instargram.config.handler.ex;

public class MyUsernameNotFoundException extends RuntimeException{

	
	private String message;

	public MyUsernameNotFoundException() {
		this.message = super.getMessage();
	}

	public MyUsernameNotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}	
}
