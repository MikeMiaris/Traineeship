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
	static
	List<TraineeshipPosition> findByLocation(@Param("location") String location) {
		return null;
	}
	
	
	@Query("Select p FROM TraineeshipPosition p WHERE p.topics = :topic")
	static
	List<TraineeshipPosition> findBytopic(@Param("topic") String topic) {
		return null;
	}
	
}
