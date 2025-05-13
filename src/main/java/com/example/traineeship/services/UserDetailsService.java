package com.example.traineeship.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
	
	UserDetails loadUserByUsername(String Username);
}
