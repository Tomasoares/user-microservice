package br.com.creativedrivebrasil.usermicroservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.model.filters.OrderType;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.UserType;
import br.com.creativedrivebrasil.usermicroservice.shared.UserTypeDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
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
	
	@Nested
	@DisplayName("the getAll method")
	class testGetAllMethod {
		
		private List<User> userInAlphabeticalOrder;
		private User tomas;
		private User pedro;
		private User silvia;
		private User vinicius;
		private User tiago;

		@BeforeEach
		void addTestingUsers() {
			this.tomas = new User(null, "Tomas soares", "tomas@gmail.com", "12345", "25 november", "3746853", UserType.USER.getId());
			this.pedro = new User(null, "Pedro", "pedro@gmail.com", "67890", "12 lagoa", "1236278", UserType.USER.getId());
			this.silvia = new User(null, "Silvia", "silvia@gmail.com", "13579", "25 november", "989898", UserType.USER.getId());
			this.vinicius = new User(null, "Vinicius soares", "vinicius@hotmail.com", "24680", "655 pastor willian", "23746853", UserType.ADMIN.getId());
			this.tiago = new User(null, "Tiago", "tiago@gmail.com", "54321", "12 pequeno principe", "37486853", UserType.ADMIN.getId());
			
			this.userInAlphabeticalOrder = new ArrayList<>(Arrays.asList(pedro, silvia, tiago, tomas, vinicius));
			
			userInAlphabeticalOrder.forEach(u -> {
				repository.save(u);
			});
		}
		
		@AfterEach
		void removeTestingUsers() {
			userInAlphabeticalOrder.forEach(u -> {
				repository.deleteById(u.getId());
			});
		}
		
		@Test
		@DisplayName("when trying to to get all users without any filtering")
		void testNoFilters() {
			this.testFilter(this.userInAlphabeticalOrder, null);
		}
		
		@Test
		@DisplayName("when trying to get all users with empty filtering")
		void testEmptyFilters() {
			this.testFilter(this.userInAlphabeticalOrder, new GetAllUserFilter());
		}
		
		@Test
		@DisplayName("when trying to to get all users with DESC order filter")
		void testDescFilter() {
			ArrayList<User> expected = new ArrayList<>(Arrays.asList(vinicius, tomas, tiago, silvia, pedro));
			GetAllUserFilter filters = GetAllUserFilter.builder().order(OrderType.DESC).build();
			
			this.testFilter(expected, filters);
		}
		
		@Test
		@DisplayName("when trying to to get all users with NAME filter")
		void testNameFilter() {
			ArrayList<User> expected = new ArrayList<>(Arrays.asList(tomas, vinicius));
			GetAllUserFilter filter = GetAllUserFilter.builder().name("soares").build();
			
			this.testFilter(expected, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with EMAIL filter")
		void testEmailFilter() {
			ArrayList<User> expected = new ArrayList<>(Arrays.asList(vinicius));
			GetAllUserFilter filter = GetAllUserFilter.builder().email("hotmail").build();
			
			this.testFilter(expected, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with ADDRESS filter")
		void testAddresFilter() {
			List<User> expected = new ArrayList<>(Arrays.asList(silvia, tomas));
			GetAllUserFilter filter = GetAllUserFilter.builder().address("25 november").build();
			
			this.testFilter(expected, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with TELEPHONE filter")
		void testTelephoneFilter() {
			List<User> expected = new ArrayList<>(Arrays.asList(pedro));
			GetAllUserFilter filter = GetAllUserFilter.builder().telephone("1236278").build();
			
			this.testFilter(expected, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with USER TYPE filter")
		void testUserTypeFilter() {
			List<User> expected = new ArrayList<>(Arrays.asList(tiago, vinicius));
			GetAllUserFilter filter = GetAllUserFilter.builder().type(UserTypeDTO.ADMIN).build();
			
			this.testFilter(expected, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with PAGE SIZE filter")
		void testPageSizeFilter() {
			List<User> expectedList = new ArrayList<>(Arrays.asList(pedro, silvia, tiago));
			GetAllUserFilter filter = GetAllUserFilter.builder().pageSize(3).build();
			
			this.testFilter(expectedList, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with PAGE OFFSET filter")
		void testPageOffsetFilter() {
			List<User> expectedList = new ArrayList<>(Arrays.asList(tomas, vinicius));
			GetAllUserFilter filter = GetAllUserFilter.builder().pageOffset(3).build();

			this.testFilter(expectedList, filter);
		}
		
		@Test
		@DisplayName("when trying to to get all users with EMAIL and NAME filters")
		void testNameAndTelephoneFilters() {
			List<User> expectedList = new ArrayList<>(Arrays.asList(tomas));
			GetAllUserFilter filter = GetAllUserFilter.builder().email("gmail").name("soares").build();

			this.testFilter(expectedList, filter);
		}
		
		private void testFilter(List<User> expectedList, GetAllUserFilter filter) {
			List<User> all = repository.getPage(filter);
			
			for (int i = 0; i < all.size(); i++) {
				User expected = expectedList.get(i);
				User actual = all.get(i);
				
				assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);
			}
		}
		
	}
}
