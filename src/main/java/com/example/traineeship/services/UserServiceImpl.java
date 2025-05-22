package com.example.traineeship.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.traineeship.domainmodel.User;
import com.example.traineeship.mappers.UserMapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired 
	UserMapper userDAO;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userDAO = userMapper;
	}
	
	@Override
	public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);
	}
	
	@Override
	public boolean isUserPresent(User user) {
        return userDAO.findByUsername(user.getUsername()).isPresent();
	}
	
	//Copied this from his github, he also had an exception catcher, i skipped that. it's the same as findById so idk
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userDAO.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
	
	@Override
	public User findById(String username) {
        return userDAO.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException(String.format("USER_NOT_FOUND", username)));
	}
	
	
}
