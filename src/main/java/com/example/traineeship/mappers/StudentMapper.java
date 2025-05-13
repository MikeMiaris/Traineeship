package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.traineeship.domainmodel.Student;


public interface StudentMapper extends JpaRepository<Student, String> {
	
}
