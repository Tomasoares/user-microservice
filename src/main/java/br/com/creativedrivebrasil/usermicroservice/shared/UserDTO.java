package br.com.creativedrivebrasil.usermicroservice.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO {

	private String id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String telephone;
	private UserTypeDTO type;
	
	
}
