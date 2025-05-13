package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.Evaluation;

public interface EvaluationMapper extends JpaRepository<Evaluation,Long>{

}
