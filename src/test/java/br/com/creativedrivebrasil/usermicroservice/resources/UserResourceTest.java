package br.com.creativedrivebrasil.usermicroservice.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.model.filters.OrderType;
import br.com.creativedrivebrasil.usermicroservice.services.api.UserService;
import br.com.creativedrivebrasil.usermicroservice.shared.UserDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserPageDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.UserTypeDTO;
import br.com.creativedrivebrasil.usermicroservice.shared.config.SpringUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("When testing User Rest API resource")
class UserResourceTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private UserService storeService;

	private TestRestTemplate restWithAdminUser() {
		String username = "creativedriver@gmail.com";
		String password = "cr34t1v3dr1v3r";
		
		return restTemplate.withBasicAuth(username, password);
	}

	private TestRestTemplate restWithNormalUser() {
		String username = "regularuser@gmail.com";
		String password = "cr34t1v3dr1v3r";
		
		return restTemplate.withBasicAuth(username, password);
	}

	private TestRestTemplate restWithoutAuth() {
		return restTemplate;
	}
	
	
	
	@Nested
	@DisplayName("the authentication roles") 
	class testAuthentication {

		@Test
		@DisplayName("when trying to access a resource without authentication")
		void testNoAuth() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());
			
			ResponseEntity<UserDTO> response = restWithoutAuth().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned Unauthorized status,", UNAUTHORIZED, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access a resource with USER role authentication")
		void testUserAuth() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());
			
			ResponseEntity<UserDTO> response = restWithNormalUser().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access a resource with ADMIN role authentication")
		void testAdminAuth() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());
			
			ResponseEntity<UserDTO> response = restWithAdminUser().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}
	}
	
	

	@Nested
	@DisplayName("the method get")
	class testGetResource {

		@Test
		@DisplayName("when trying to retrieve successfully the pathVariable userId")
		void testGetUserSuccessful() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO(a.getArgument(0), "", "", "", "", "", null));
			
			ResponseEntity<UserDTO> response = restWithAdminUser().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertEquals("shoudl've returned an User with the same ID,", "1", response.getBody().getId());
		}

		@Test
		@DisplayName("when trying to retrieve an unexisting user")
		void testGetUserNotFound() {
			when(storeService.get(anyString())).thenAnswer(a -> null);

			ResponseEntity<UserDTO> response = restWithAdminUser().getForEntity("/users/2", UserDTO.class);

			assertEquals("should've returned NOT FOUND status,", NOT_FOUND, response.getStatusCode());
			assertNull("should've returned an empty body,", response.getBody());
		}

		@Test
		@DisplayName("when trying to access with USER role authentication")
		void testUserAuth() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());
			
			ResponseEntity<UserDTO> response = restWithNormalUser().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with ADMIN role authentication")
		void testAdminAuth() {
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());
			
			ResponseEntity<UserDTO> response = restWithAdminUser().getForEntity("/users/1", UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}
	}
	
	

	@Nested
	@DisplayName("the method create")
	class testCreateResource {

		@Test
		@DisplayName("when trying to sucessfully retrieve an User as the body of the request")
		void testPostNewUser() {
			doNothing().when(storeService).save(any());

			UserDTO user = new UserDTO(null, "tomas", "tomas@gmail.com", "123345", "address", "23467312",
					UserTypeDTO.ADMIN);

			ResponseEntity<UserDTO> response = restWithAdminUser().postForEntity("/users", user, UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(response.getBody()).isEqualToComparingFieldByFieldRecursively(user);
		}

		@Test
		@DisplayName("when trying to sucessfully retrieve an User with ID as the body of the request")
		void testPostUserWithId() {
			doNothing().when(storeService).save(any());

			UserDTO user = new UserDTO("1", "tomas", "tomas@gmail.com", "123345", "address", "23467312",
					UserTypeDTO.ADMIN);

			ResponseEntity<UserDTO> response = restWithAdminUser().postForEntity("/users", user, UserDTO.class);

			assertEquals("should've returned BAD_REQUEST status,", BAD_REQUEST, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to create an unexisting user")
		void testPostNullUser() {
			doNothing().when(storeService).save(any());

			ResponseEntity<UserDTO> response = restWithAdminUser().postForEntity("/users", null, UserDTO.class);

			assertEquals("should've returned UNSUPPORTED_MEDIA_TYPE status,", UNSUPPORTED_MEDIA_TYPE,
					response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with USER role authentication")
		void testUserAuth() {
			doNothing().when(storeService).save(any());

			ResponseEntity<UserDTO> response = restWithNormalUser().postForEntity("/users", new UserDTO(), UserDTO.class);

			assertEquals("should've returned FORBIDDEN status,", FORBIDDEN, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with ADMIN role authentication")
		void testAdminAuth() {
			doNothing().when(storeService).save(any());

			ResponseEntity<UserDTO> response = restWithAdminUser().postForEntity("/users", new UserDTO(), UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
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

			ResponseEntity<UserDTO> response = restWithAdminUser().exchange("/users/1", PUT, entity, UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(response.getBody()).isEqualToComparingFieldByFieldRecursively(user);
		}

		@Test
		@DisplayName("when trying to update a null user")
		void testPutNullUser() {
			doNothing().when(storeService).save(any());

			UserDTO user = null;

			ResponseEntity<UserDTO> response = restWithAdminUser().exchange("/users/1", PUT, new HttpEntity<UserDTO>(user),
					UserDTO.class);

			assertEquals("should've returned BAD_REQUEST status,", BAD_REQUEST, response.getStatusCode());
		}
		
		@Test
		@DisplayName("when trying to access with USER role authentication")
		void testUserAuth() {
			doNothing().when(storeService).save(any());

			HttpEntity<UserDTO> emptyEntity = new HttpEntity<UserDTO>(new UserDTO());
			ResponseEntity<UserDTO> response = restWithNormalUser().exchange("/users/1", PUT, emptyEntity, UserDTO.class);

			assertEquals("should've returned FORBIDDEN status,", FORBIDDEN, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with ADMIN role authentication")
		void testAdminAuth() {
			doNothing().when(storeService).save(any());

			HttpEntity<UserDTO> emptyEntity = new HttpEntity<UserDTO>(new UserDTO());
			ResponseEntity<UserDTO> response = restWithAdminUser().exchange("/users/1", PUT, emptyEntity, UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}
	}
	
	

	@Nested
	@DisplayName("the method delete")
	class testDeleteResource {

		@Test
		@DisplayName("when trying to sucessfully delete an User as the body of the request")
		void testPutExistingUser() {
			doNothing().when(storeService).delete(any());
			when(storeService.get(anyString())).thenAnswer(a -> new UserDTO());

			ResponseEntity<UserDTO> response = restWithAdminUser().exchange("/users/1", DELETE, null, UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(response.getBody()).isEqualToComparingFieldByFieldRecursively(new UserDTO());
		}
		
		@Test
		@DisplayName("when trying to access with USER role authentication")
		void testUserAuth() {
			doNothing().when(storeService).delete(any());
			when(storeService.get(anyString())).thenAnswer(a -> null);

			ResponseEntity<UserDTO> response = restWithNormalUser().exchange("/users/1", DELETE, null, UserDTO.class);

			assertEquals("should've returned FORBIDDEN status,", FORBIDDEN, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with ADMIN role authentication")
		void testAdminAuth() {
			doNothing().when(storeService).delete(any());
			when(storeService.get(anyString())).thenAnswer(a -> null);

			ResponseEntity<UserDTO> response = restWithAdminUser().exchange("/users/1", DELETE, null, UserDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}
	}
	
	

	@Nested
	@DisplayName("the method getAll")
	class testeGetAll {

		@Test
		@DisplayName("when trying to check if all parameters have been successfully fulfilled")
		void testAppliedFilters() {
			int[] checkParams = { 0 };

			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> {
				GetAllUserFilter user = a.getArgument(0);
				if (user.address != null)
					checkParams[0] += 1;
				if (user.email != null)
					checkParams[0] += 2;
				if (user.name != null)
					checkParams[0] += 4;
				if (user.order != null)
					checkParams[0] += 8;
				if (user.pageOffset != null)
					checkParams[0] += 16;
				if (user.pageSize != null)
					checkParams[0] += 32;
				if (user.telephone != null)
					checkParams[0] += 64;
				if (user.type != null)
					checkParams[0] += 128;

				return null;
			});

			String uri = "/users";

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri).queryParam("name", "Tomas")
					.queryParam("email", "tomas@gmail.com").queryParam("address", "address")
					.queryParam("telephone", "telephone").queryParam("type", UserTypeDTO.USER.name())
					.queryParam("order", OrderType.ASC.name()).queryParam("pageSize", "10")
					.queryParam("pageOffset", "2");

			String finalUri = builder.toUriString();
			restWithAdminUser().exchange(finalUri, GET, null, UserPageDTO.class);

			int countAllParams = 1 + 2 + 4 + 8 + 16 + 32 + 64 + 128;
			assertEquals("all parameters should be fulfilled", countAllParams, checkParams[0]);
		}

		@Test
		@DisplayName("when trying to retrieve a list with some users")
		void testFoundUser() {
			UserPageDTO page = new UserPageDTO(Arrays.asList(new UserDTO("1", "2", "3", "4", "5", "6", UserTypeDTO.ADMIN),
															 new UserDTO("2", "3", "4", "5", "6", "7", UserTypeDTO.ADMIN)), 1, 2);

			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> page);

			ResponseEntity<UserPageDTO> response = restWithAdminUser().getForEntity("/users", UserPageDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
			assertNotNull("response shouldn't be null,", response.getBody());
			assertThat(page).isEqualToComparingFieldByFieldRecursively(response.getBody());
		}

		@Test
		@DisplayName("when trying to retrieve a list of users with no results")
		void testNotFoundAnyUser() {
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> null);

			ResponseEntity<UserPageDTO> response = restWithAdminUser().getForEntity("/users", UserPageDTO.class);

			assertEquals("should've returned NOT_FOUND status,", NOT_FOUND, response.getStatusCode());
			assertNull("should've returned a null body,", response.getBody());
		}
		
		@Test
		@DisplayName("when trying to access with USER role authentication")
		void testUserAuth() {
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> new UserPageDTO(Arrays.asList(new UserDTO()), 0, 0));

			ResponseEntity<UserPageDTO> response = restWithNormalUser().getForEntity("/users", UserPageDTO.class);

			assertEquals("should've returned FORBIDDEN status,", OK, response.getStatusCode());
		}

		@Test
		@DisplayName("when trying to access with ADMIN role authentication")
		void testAdminAuth() {
			when(storeService.getAll(any(GetAllUserFilter.class))).thenAnswer(a -> new UserPageDTO(Arrays.asList(new UserDTO()), 0, 0));

			ResponseEntity<UserPageDTO> response = restWithAdminUser().getForEntity("/users", UserPageDTO.class);

			assertEquals("should've returned OK status,", OK, response.getStatusCode());
		}
	}
}
