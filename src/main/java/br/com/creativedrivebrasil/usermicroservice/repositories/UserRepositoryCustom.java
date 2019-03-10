package br.com.creativedrivebrasil.usermicroservice.repositories;

import java.util.List;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;

public interface UserRepositoryCustom {
	
	List<User> getAll(GetAllUserFilter filter);

}
