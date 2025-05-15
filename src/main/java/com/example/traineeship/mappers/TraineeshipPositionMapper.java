package com.example.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.TraineeshipPosition;

public interface TraineeshipPositionMapper extends JpaRepository<TraineeshipPosition, Integer>{

	Optional<TraineeshipPosition> findById(Integer positionId);
	
}
