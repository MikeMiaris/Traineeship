package com.example.traineeship.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;

@Repository
public interface TraineeshipPositionMapper extends JpaRepository<TraineeshipPosition, Integer>{

	Optional<TraineeshipPosition> findById(Integer positionId);
	
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.company.companyLocation = :location")
	List<TraineeshipPosition> findByLocation(@Param("location") String location);
	
	
	@Query(value = """
		    SELECT p.* FROM positions p
		    WHERE (
		        SELECT COUNT(*) FROM (
		            SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(
		                CASE 
		                    WHEN p.topics LIKE '%,%' THEN p.topics
		                    ELSE CONCAT(p.topics, ',') 
		                END, 
		                ',', 
		                n.digit+1
		            ), ',', -1)) AS interest
		            FROM (SELECT 0 digit UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) n
		            WHERE n.digit < LENGTH(p.topics) - LENGTH(REPLACE(p.topics, ',', '')) + 1
		        ) split_interests
		        WHERE split_interests.interest IN (:interests)
		    ) > 0
		    """, nativeQuery = true)
		List<TraineeshipPosition> findBytopic(@Param("interests") List<String> interests);
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.isAssigned = true AND CURRENT_TIMESTAMP < p.toDate")
	List<TraineeshipPosition> getallAssigned();
	
}
