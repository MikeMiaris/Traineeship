package com.example.traineeship.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final TraineeshipPositionMapper positionMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, TraineeshipPositionMapper positionMapper) {
        this.studentMapper = studentMapper;
        this.positionMapper = positionMapper;
    }

    @Override
    public void saveProfile(Student student) {
        studentMapper.save(student);
    }

    @Override
    public Student retrieveProfile(String studentUsername) {
        return studentMapper.findById(studentUsername).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentUsername));
    }

    @Override
    public void saveLogbook(TraineeshipPosition position) {
        positionMapper.save(position);
    }
    
}