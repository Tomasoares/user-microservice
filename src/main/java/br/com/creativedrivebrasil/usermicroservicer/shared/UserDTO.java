package br.com.creativedrivebrasil.usermicroservicer.shared;

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
public class UserDTO {

	private String id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String telephone;
	private UserTypeDTO type;
	
	
}
