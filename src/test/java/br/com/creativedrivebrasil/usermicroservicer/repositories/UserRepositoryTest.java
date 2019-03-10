package br.com.creativedrivebrasil.usermicroservicer.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;
import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.UserType;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("When testing User Repository API resource")
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Test
	@DisplayName("when trying to save a user to the database")
	void testeSaveUser() {
		User user = new User(null, "Tomas", "tomas@gmail.com", "12345", "25 november", "3746853", UserType.ADMIN.getId());
		
		this.repository.save(user);
		
		assertNotNull(user.getId(), "should've returned an generated user ID");
		
		this.repository.deleteById(user.getId());
	}
	
	@Test
	@DisplayName("when trying to get a user to the database")
	void testeGetUser() {
		User user = new User(null, "Tomas", "tomas@gmail.com", "12345", "25 november", "3746853", UserType.ADMIN.getId());
		this.repository.save(user);
		
		Optional<User> findById = this.repository.findById(user.getId());
		
		assertNotNull(findById.get(), "should've returned an user");
		assertThat(findById.get()).isEqualToComparingFieldByFieldRecursively(user);
		
		this.repository.deleteById(user.getId());
	}
	
	@Test
	@DisplayName("when trying to update a user to the database")
	void testeUpdateUser() {
		User user = new User(null, "Tomas", "tomas@gmail.com", "12345", "25 november", "3746853", UserType.ADMIN.getId());
		this.repository.save(user);
		user.setName("tomas2");
		this.repository.save(user);
		
		Optional<User> updatedUser = this.repository.findById(user.getId());
		
		assertTrue("should've retrieved successfully the updated user", updatedUser.isPresent());
		assertEquals(updatedUser.get().getName(), "tomas2", "should've returned the new user name");
		
		this.repository.deleteById(user.getId());
	}
}
