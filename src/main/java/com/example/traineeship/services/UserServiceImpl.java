package com.example.traineeship.services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.traineeship.domainmodel.User;
import com.example.traineeship.mappers.UserMapper;

public class UserServiceImpl {
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired 
	UserMapper userDAO;
	

	public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);
	}
	
	
	public boolean isUserPresent(User user) {
        return userDAO.findById(user.getUsername()).isPresent();
	}
	
	/*public UserDetails loadUserByUsername(String username) {

	}*/
	
	public User findById(String username) {
        return userDAO.findById(username).orElse(null);
	}
	
	
}
