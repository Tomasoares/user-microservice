package br.com.creativedrivebrasil.usermicroservicer.resources;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.creativedrivebrasil.usermicroservicer.model.User;
import br.com.creativedrivebrasil.usermicroservicer.model.filter.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		return ResponseEntity.status(BAD_REQUEST).body(null); 
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> get(@PathVariable("userId") Long userId) {
		
		User user = this.service.get(userId);
		
		if (user == null) {
			return ResponseEntity.status(NOT_FOUND).body(null); 
		}
		
		return ResponseEntity.status(OK).body(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(GetAllUserFilter filters) {
	
		return ResponseEntity.status(BAD_REQUEST).body(null); 
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable("userId") Long userId) {
		return ResponseEntity.status(BAD_REQUEST).body(null); 
	}
	
	
	
}
