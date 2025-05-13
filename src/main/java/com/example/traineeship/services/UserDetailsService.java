package com.example.traineeship.services;

import com.example.traineeship.domainmodel.UserDetails;

public interface UserDetailsService {
	
	UserDetails loadUserByUsername(String Username);
}
