package br.com.creativedrivebrasil.usermicroservicer.services.api;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservicer.dto.UserDTO;
import br.com.creativedrivebrasil.usermicroservicer.dto.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;

@Service
public interface UserService {

	void create(UserDTO user);
	
	void update(UserDTO user);
	
	UserDTO get(Long id);
	
	UserPageDTO getAll(GetAllUserFilter getAllUserFilter);
	
	void delete(Long id);
	
}
