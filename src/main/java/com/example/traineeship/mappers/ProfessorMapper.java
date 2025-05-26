package com.example.traineeship.mappers;

import java.util.List;

import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.traineeship.domainmodel.Professor;

public interface ProfessorMapper extends JpaRepository<Professor,String>{
	
	@Query(value = """
		    SELECT p.* FROM professors p
		    WHERE (
		        SELECT COUNT(*) FROM (
		            SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(
		                CASE 
		                    WHEN p.interests LIKE '%,%' THEN p.interests 
		                    ELSE CONCAT(p.interests, ',') 
		                END, 
		                ',', 
		                n.digit+1
		            ), ',', -1)) AS interest
		            FROM (SELECT 0 digit UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) n
		            WHERE n.digit < LENGTH(p.interests) - LENGTH(REPLACE(p.interests, ',', '')) + 1
		        ) split_interests
		        WHERE split_interests.interest IN (:topics)
		    ) > 0
		    """, nativeQuery = true)
		List<Professor> findByInterest(@Param("topics") List<String> topics);
	
	
	@Query("""
		    SELECT p FROM Professor p 
		    WHERE SIZE(p.supervisedPositions) = (
		        SELECT MIN(SIZE(p2.supervisedPositions)) 
		        FROM Professor p2 
		    ) 
		""")
	    List<Professor> findByLoad();
	
	
}