package br.com.creativedrivebrasil.usermicroservice.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")
public class User {

	@Id
	private String id;
	
	@NonNull
	private String name;

	@NonNull
	@Indexed(unique=true)
	private String email;

	@NonNull
	private String password;
	
	private String address;
	
	private String telephone;

	@NonNull
	private Integer idUserType;
	
}
