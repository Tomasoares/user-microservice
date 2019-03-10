package br.com.creativedrivebrasil.usermicroservicer.services.api;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserPageDTO;

@Service
public interface UserService {

	void save(UserDTO user);
	
	UserPageDTO getAll(GetAllUserFilter getAllUserFilter);

	UserDTO get(String id);

	void delete(String id);
	
}
