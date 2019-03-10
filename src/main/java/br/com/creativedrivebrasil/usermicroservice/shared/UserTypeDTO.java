package br.com.creativedrivebrasil.usermicroservice.shared;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeDTO {
	ADMIN(1),
	USER(2);
	
	private int id;

	UserTypeDTO(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	@JsonValue
	public String getUserType() {
		return this.name();
	}

	public static UserTypeDTO findById(Integer idUserType) {
		for (UserTypeDTO type : UserTypeDTO.values()) {
			if (Objects.equals(type.getId(), idUserType)) {
				return type;
			}
		}

		return null;
	}
	
}
