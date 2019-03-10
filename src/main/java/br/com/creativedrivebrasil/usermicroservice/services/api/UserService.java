package br.com.creativedrivebrasil.usermicroservice.services.api;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserPageDTO;

@Service
public interface UserService {

	void save(UserDTO user);
	
	UserPageDTO getAll(GetAllUserFilter getAllUserFilter);

	UserDTO get(String id);

	void delete(String id);
	
}
