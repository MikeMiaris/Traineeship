package com.example.traineeship.factories;
import java.util.List;

import com.example.traineeship.domainmodel.TraineeshipPosition;


public interface PositionSearchStrategy{
	List<TraineeshipPosition> search(String applicantUsername);
}
