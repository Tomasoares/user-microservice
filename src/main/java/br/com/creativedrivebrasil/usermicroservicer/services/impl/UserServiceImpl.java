package br.com.creativedrivebrasil.usermicroservicer.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservicer.model.User;
import br.com.creativedrivebrasil.usermicroservicer.model.filter.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll(GetAllUserFilter getAllUserFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
