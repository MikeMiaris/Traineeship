package com.example.traineeship.services;

import com.example.traineeship.domainmodel.User;

public interface UserService {

	void saveUser(User user);
	boolean isUserPresent(User user);
	User findById(String username);
	
}
