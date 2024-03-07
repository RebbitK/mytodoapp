package com.sparta.mytodoapp.exception;


public class NoPermissionException extends RuntimeException {
	public NoPermissionException(String msg){
		super(msg);
	}
}
