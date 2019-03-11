package br.com.creativedrivebrasil.usermicroservice.resources;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.services.api.UserService;
import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserPageDTO;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> get(@PathVariable("userId") String userId) {
		
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

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
		if (user.getId() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		this.service.save(user);
		return ResponseEntity.status(OK).body(user);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO user, @PathVariable("userId") String userId) {
		user.setId(userId);
		this.service.save(user);
		
		return ResponseEntity.status(OK).body(user); 
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDTO> delete(@PathVariable("userId") String userId) {
		UserDTO user = this.service.get(userId);
		this.service.delete(userId);
		return ResponseEntity.status(OK).body(user);
	}
}
