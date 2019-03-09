package br.com.creativedrivebrasil.usermicroservicer.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeDTO {
	ADMIN,
	USER;
	
	@JsonValue
	public String getUserType() {
		return this.name();
	}
	
}
