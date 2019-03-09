package br.com.creativedrivebrasil.usermicroservicer.resources;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.creativedrivebrasil.usermicroservicer.dto.UserDTO;
import br.com.creativedrivebrasil.usermicroservicer.dto.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
		if (user == null) {
			return ResponseEntity.status(BAD_REQUEST).body(null);
		}
		
		this.service.create(user);
		
		return ResponseEntity.status(OK).body(user);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> get(@PathVariable("userId") Long userId) {
		
		UserDTO user = this.service.get(userId);
		
		if (user == null) {
			return ResponseEntity.status(NOT_FOUND).body(null); 
		}
		
		return ResponseEntity.status(OK).body(user);
	}
	
	@GetMapping
	public ResponseEntity<UserPageDTO> getAll(@ModelAttribute GetAllUserFilter filters) {
		UserPageDTO page = this.service.getAll(filters);
		
		if (page == null || page.getUsers().size() == 0) {
			return ResponseEntity.status(NOT_FOUND).body(page);
		}
		
		return ResponseEntity.status(OK).body(page); 
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO user, @PathVariable("userId") Long userId) {
		if (user == null) {
			return ResponseEntity.status(NOT_FOUND).body(null); 
		}
		
		user.setId(userId);
		this.service.update(user);
		
		return ResponseEntity.status(OK).body(user); 
	}
	
	
	
}
