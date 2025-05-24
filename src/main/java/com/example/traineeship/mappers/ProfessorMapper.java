package com.example.traineeship.mappers;

import java.util.List;

import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.traineeship.domainmodel.Professor;

public interface ProfessorMapper extends JpaRepository<Professor,String>{
	@Query("SELECT p FROM Professor p WHERE CONCAT(',', p.interests, ',') LIKE CONCAT('%', :interest, '%') ")
	List<Professor> findByInterest(@Param("interest") String interest);
	
	
	@Query("""
		    SELECT p FROM Professor p 
		    WHERE SIZE(p.supervisedPositions) = (
		        SELECT MIN(SIZE(p2.supervisedPositions)) 
		        FROM Professor p2
		    )
		""")
	    Professor findByLoad();
	
	
}