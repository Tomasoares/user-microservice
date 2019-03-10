package br.com.creativedrivebrasil.usermicroservice.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;
import br.com.creativedrivebrasil.usermicroservice.repositories.UserRepository;
import br.com.creativedrivebrasil.usermicroservice.services.api.UserService;
import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public void save(br.com.creativedrivebrasil.usermicroservice.shared.UserDTO user) {
		User dto = new User(user.getId(), 
							user.getName(), 
							user.getEmail(), 
							user.getPassword(), 
							user.getAddress(), 
							user.getTelephone(), 
							UserUtils.getUserTypeId(user.getType()));
		
		this.repository.save(dto);
	}

	@Override
	public UserDTO get(String id) {
		Optional<User> dto = this.repository.findById(id);
		
		if (!dto.isPresent()) {
			return null;
		}
		
		User userDb = dto.get();
		
		UserDTO foundUser = new UserDTO(userDb.getId(),
									    userDb.getName(),
									    userDb.getEmail(),
									    userDb.getPassword(),
									    userDb.getAddress(),
									    userDb.getTelephone(),
									    UserUtils.getUserTypeDTO(userDb.getIdUserType()));
		
		return foundUser;
	}

	@Override
	public UserPageDTO getAll(GetAllUserFilter getAllUserFilter) {
		// TODO Auto-generated method stub
		return new UserPageDTO();
	}

	@Override
	public void delete(String id) {
		this.repository.deleteById(id);
	}

}
