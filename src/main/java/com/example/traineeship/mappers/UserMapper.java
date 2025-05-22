package com.example.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.User;

public interface UserMapper extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}
