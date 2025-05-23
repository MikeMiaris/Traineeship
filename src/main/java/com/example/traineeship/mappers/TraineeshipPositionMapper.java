package com.example.traineeship.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.traineeship.domainmodel.TraineeshipPosition;

@Repository
public interface TraineeshipPositionMapper extends JpaRepository<TraineeshipPosition, Integer>{

	Optional<TraineeshipPosition> findById(Integer positionId);
	
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.company.companyLocation = :location")
	
	List<TraineeshipPosition> findByLocation(@Param("location") String location);
	
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.topics = :topic")
	List<TraineeshipPosition> findBytopic(@Param("topic") String topic);
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.isAssigned = true AND CURRENT_TIMESTAMP < p.toDate")
	List<TraineeshipPosition> getallAssigned();
	
}
