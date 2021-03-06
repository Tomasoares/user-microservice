package br.com.creativedrivebrasil.usermicroservice.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void save(br.com.creativedrivebrasil.usermicroservice.shared.UserDTO user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		User dto = new User(user.getId(), 
							user.getName(), 
							user.getEmail(), 
							encodedPassword, 
							user.getAddress(), 
							user.getTelephone(), 
							UserUtils.getUserTypeId(user.getType()));
		
		this.repository.save(dto);
		
		user.setId(dto.getId());
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
	public UserPageDTO getAll(GetAllUserFilter filter) {
		List<User> userList = this.repository.getPage(filter);
		
		UserPageDTO page = new UserPageDTO();
		
		page.setPageOffset(filter.getPageOffset());
		page.setPageSize(filter.getPageSize());
		page.setUsers(userList.stream().map(db -> new UserDTO(db.getId(),
														      db.getName(),
														      db.getEmail(),
														      db.getPassword(),
														      db.getAddress(),
														      db.getTelephone(),
															  UserUtils.getUserTypeDTO(db.getIdUserType()))).collect(Collectors.toList()));
		
		return page;
	}

	@Override
	public void delete(String id) {
		this.repository.deleteById(id);
	}

}
