package br.com.creativedrivebrasil.usermicroservicer.services.impl;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservicer.dto.UserDTO;
import br.com.creativedrivebrasil.usermicroservicer.dto.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void create(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDTO get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPageDTO getAll(GetAllUserFilter getAllUserFilter) {
		// TODO Auto-generated method stub
		return new UserPageDTO();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
