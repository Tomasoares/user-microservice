package br.com.creativedrivebrasil.usermicroservicer.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.creativedrivebrasil.usermicroservicer.model.User;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserResourceTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private UserService storeService;

	@Test
	void testGetUserSuccessful() {
		when(storeService.get(anyLong())).thenAnswer(a -> {
			Long givenId = a.getArgument(0);
			
			if (Objects.equals(givenId, 1L)) {
				return new User(1L, "", "", "", "", "", null);
			}
			
			return null;
		});
		
		ResponseEntity<User> response = restTemplate.getForEntity("/users/1", User.class);
		
		System.out.println(response.getStatusCode());
		
		assertEquals(response.getStatusCode(), OK);
		assertEquals(response.getBody().getId(), Long.valueOf(1L));
	}

	@Test
	void testGetUserNotFound() {
		when(storeService.get(anyLong())).thenAnswer(a -> null);
		
		ResponseEntity<User> response = restTemplate.getForEntity("/users/2", User.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertNull(response.getBody());
	}
}
