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
	        ORDER BY 
	            SIZE(
	                FUNCTION('STRING_TO_ARRAY', 
	                    FUNCTION('COALESCE', p.supervisedPositions, ''), 
	                    ','
	                )
	            ) ASC
	        LIMIT 1
	    """)
	    Professor findByLoad(Limit limit);
	
	
}