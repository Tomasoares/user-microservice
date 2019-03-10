package br.com.creativedrivebrasil.usermicroservicer.repositories;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;

public interface UserRepositoryCustom {
	
	List<User> getAll(GetAllUserFilter filter);

}
