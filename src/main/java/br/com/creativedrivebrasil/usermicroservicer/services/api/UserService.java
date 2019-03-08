package br.com.creativedrivebrasil.usermicroservicer.services.api;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservicer.model.User;
import br.com.creativedrivebrasil.usermicroservicer.model.filter.GetAllUserFilter;

@Service
public interface UserService {

	void create(User user);
	
	void update(User user);
	
	User get(Long id);
	
	List<User> getAll(GetAllUserFilter getAllUserFilter);
	
	void delete(Long id);
	
}
