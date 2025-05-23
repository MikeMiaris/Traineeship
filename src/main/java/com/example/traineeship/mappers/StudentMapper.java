package com.example.traineeship.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.traineeship.domainmodel.Student;


public interface StudentMapper extends JpaRepository<Student, String> {
	@Query("SELECT p FROM Student p WHERE p.lookingForTraineeship = true")
	List<Student> LookingForTrain();

}
