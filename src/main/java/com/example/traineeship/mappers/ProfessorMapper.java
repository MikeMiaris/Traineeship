package com.example.traineeship.mappers;

import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.traineeship.domainmodel.Professor;

public interface ProfessorMapper extends JpaRepository<Professor,String>{
	@Query("SELECT p FROM Professor p WHERE p.interests LIKE %:interest% ")
	Professor findByInterest(@Param("interest") String interest);
	
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