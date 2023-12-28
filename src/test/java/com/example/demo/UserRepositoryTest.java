package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test 
	public void testCreateUser() {
		User user = new User();
		user.setEmail("admin@gmail.com");
		user.setPassword("admin");
		user.setFirstName("Rashmi");
		user.setLastName("Nimje");
		User savedUser = repo.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getId());	
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	}
	
	
	@Test
	public void testFindUserByEmail()
	{
		String email = "admin@gmail.com";
		User user = repo.findByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
}
