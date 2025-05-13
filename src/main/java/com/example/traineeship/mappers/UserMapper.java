package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.User;

public interface UserMapper extends JpaRepository<User, String>{

}
