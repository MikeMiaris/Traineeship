package com.example.traineeship.services;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;

public interface StudentService {
    void saveProfile(Student student);
    Student retrieveProfile(String studentUsername);
    void saveLogbook(TraineeshipPosition position);
}
