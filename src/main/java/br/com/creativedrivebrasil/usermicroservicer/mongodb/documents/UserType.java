package br.com.creativedrivebrasil.usermicroservicer.mongodb.documents;

public enum UserType {

	USER(1),
	ADMIN(2);
	
	private int id;
	
	UserType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
