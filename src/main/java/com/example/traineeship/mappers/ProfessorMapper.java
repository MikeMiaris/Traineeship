package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.Professor;

public interface ProfessorMapper extends JpaRepository<Professor,Long>{
	 Professor findByUsername(String username);
}