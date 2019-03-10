package br.com.creativedrivebrasil.usermicroservicer.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.model.filters.OrderType;
import br.com.creativedrivebrasil.usermicroservicer.services.api.UserService;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserTypeDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("When testing User Rest API resource")
class UserResourceTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private UserService storeService;
	
	@Nested
	@DisplayName("the method get")
	class testGetResource {
		
		@Test
		@DisplayName("when trying to retrieve successfully the pathVariable userId")
		void testGetUserSuccessful() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO(a.getArgument(0), "", "", "", "", "", null));
			
			ResponseEntity<UserDTO> response = restTemplate.getForEntity("/users/1", UserDTO.class);
			
			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertEquals("shoudl've returned an User with the same ID,", Long.valueOf(1L), response.getBody().getId());
		}

		@Test
		@DisplayName("when trying to retrieve an unexisting user")
		void testGetUserNotFound() {
			when(storeService.get(anyString())).thenAnswer(a -> null);
			
			ResponseEntity<UserDTO> response = restTemplate.getForEntity("/users/2", UserDTO.class);
			
			assertEquals("should've returned NOT FOUND status,", NOT_FOUND, response.getStatusCode());
			assertNull("should've returned an empty body,", response.getBody());
		}
	}

	@Nested
	@DisplayName("the method create")
	class testCreateResource {
		
		@Test
		@DisplayName("when trying to sucessfully retrieve an User as the body of the request")
		void testPostNewUser() {
			doNothing().when(storeService).save(any());
			
			UserDTO user = new UserDTO("1", "tomas", "tomas@gmail.com", "123345", "address", "23467312", UserTypeDTO.ADMIN);
			
			ResponseEntity<UserDTO> response = restTemplate.postForEntity("/users", user, UserDTO.class);
			
			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			
			if (response.getBody() != null) {
				assertThat(response.getBody()).isEqualToComparingFieldByFieldRecursively(user);
			}
		}

		@Test
		@DisplayName("when trying to create an unexisting user")
		void testPostNullUser() {
			doNothing().when(storeService).save(any());
			
			ResponseEntity<UserDTO> response = restTemplate.postForEntity("/users", null, UserDTO.class);
			
			assertEquals("should've returned UNSUPPORTED MEDIA TYPE status,", UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
		}
	}
	
	@Nested
	@DisplayName("the method update")
	class testUpdateResource {
		
		@Test
		@DisplayName("when trying to sucessfully retrieve an User as the body of the request")
		void testPutExistingUser() {
			doNothing().when(storeService).save(any());
			
			UserDTO user = new UserDTO("1", "tomas", "tomas@gmail.com", "123345", "address", "23467312", UserTypeDTO.ADMIN);
			HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(user);
			
			ResponseEntity<UserDTO> response = restTemplate.exchange("/users/1", PUT, entity, UserDTO.class);
			
			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(response.getBody()).isEqualToComparingFieldByFieldRecursively(user);
		}

		@Test
		@DisplayName("when trying to update a null user")
		void testPutNullUser() {
			doNothing().when(storeService).save(any());

			UserDTO user = null;
			
			ResponseEntity<UserDTO> response = restTemplate.exchange("/users/1", PUT, new HttpEntity<UserDTO>(user), UserDTO.class);
			
			assertEquals("should've returned BAD_REQUEST status,", BAD_REQUEST, response.getStatusCode());
		}
	}
	
	@Nested
	@DisplayName("the method getAll")
	class testeGetAll {
		
		@Test
		@DisplayName("when trying to check if all parameters have been successfully fulfilled")
		void testAppliedFilters() {
			int[] checkParams = {0};
			
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> {
				GetAllUserFilter user = a.getArgument(0);
				if (user.address != null) checkParams[0]+=1;
				if (user.email != null) checkParams[0]+=2;
				if (user.name != null) checkParams[0]+=4;
				if (user.order != null) checkParams[0]+=8;
				if (user.pageOffset != null) checkParams[0]+=16;
				if (user.pageSize != null) checkParams[0]+=32;
				if (user.telephone != null) checkParams[0]+=64;
				if (user.type != null) checkParams[0]+=128;
				
				return null;
			});
			
			String uri = "/users";
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
			        .queryParam("name", "Tomas")
			        .queryParam("email", "tomas@gmail.com")
			        .queryParam("address", "address")
			        .queryParam("telephone", "telephone")
			        .queryParam("type", UserTypeDTO.USER.name())
			        .queryParam("order", OrderType.ASC.name())
			        .queryParam("pageSize", "10")
			        .queryParam("pageOffset", "2");
			
			String finalUri = builder.toUriString();
			restTemplate.exchange(finalUri, GET, null, UserPageDTO.class);
			
			
			int countAllParams = 1+2+4+8+16+32+64+128;
			assertEquals("all parameters should be fulfilled", countAllParams, checkParams[0]);
		}

		@Test
		@DisplayName("when trying to retrieve a list with some users")
		void testFoundUser() {
			UserPageDTO page = new UserPageDTO(Arrays.asList(new UserDTO("1", "2", "3", "4", "5", "6", UserTypeDTO.ADMIN),
															 new UserDTO("2", "3", "4", "5", "6", "7", UserTypeDTO.ADMIN)), 1, 2);
			
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> page);
			
			ResponseEntity<UserPageDTO> response = restTemplate.getForEntity("/users", UserPageDTO.class);
			
			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(page).isEqualToComparingFieldByFieldRecursively(response.getBody());
		}

		@Test
		@DisplayName("when trying to retrieve a list of users with no results")
		void testNotFoundAnyUser() {
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> null);

			ResponseEntity<UserPageDTO> response = restTemplate.getForEntity("/users", UserPageDTO.class);
			
			assertEquals("should've returned NOT_FOUND status,", NOT_FOUND, response.getStatusCode());
			assertNull("should've returned a null body,", response.getBody());
		}
	}
}
