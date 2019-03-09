package br.com.creativedrivebrasil.usermicroservicer.mongodb.documents;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String id;
	
	private String name;
	private String email;
	private String password;
	private String address;
	private String telephone;
	
}
