package br.com.creativedrivebrasil.usermicroservicer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String telephone;
	private UserType type;
	
	
}
