package br.com.creativedrivebrasil.usermicroservice.services.impl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;
import br.com.creativedrivebrasil.usermicroservice.repositories.UserRepository;
import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserTypeDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.config.SpringUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.repository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid credentials!");
			
		} else {
			return new SpringUser(new UserDTO(user.getId(), 
											  user.getName(), 
											  user.getEmail(), 
											  user.getPassword(), 
											  user.getAddress(),
											  user.getTelephone(),
											  UserTypeDTO.findById(user.getIdUserType())));
		}
	}

}
