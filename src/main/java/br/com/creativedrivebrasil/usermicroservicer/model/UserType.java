package br.com.creativedrivebrasil.usermicroservicer.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {
	ADMIN,
	USER;
	
	@JsonValue
	public String getUserType() {
		return this.name();
	}
	
}
