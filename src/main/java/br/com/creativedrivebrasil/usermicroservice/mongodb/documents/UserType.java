package br.com.creativedrivebrasil.usermicroservice.mongodb.documents;

import java.util.Objects;

public enum UserType {

	ADMIN(1),
	USER(2);
	
	private int id;
	
	UserType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static UserType findById(int id) {
		for (UserType type : UserType.values()) {
			if (Objects.equals(id, type.id)) {
				return type;
			}
		}
		
		return null;
	}
}
