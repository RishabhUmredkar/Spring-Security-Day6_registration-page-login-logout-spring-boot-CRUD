package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	   @Query(value = "SELECT u.* FROM users u WHERE u.email = ?1",
	            nativeQuery = true)
	   User findByEmail(String email);
}
