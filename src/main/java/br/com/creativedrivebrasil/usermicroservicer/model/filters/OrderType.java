package br.com.creativedrivebrasil.usermicroservicer.model.filters;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
	ASC,
	DESC;
	
	@JsonValue
	public String getUserType() {
		return this.name();
	}
}
