package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.Company;

public interface CompanyMapper extends JpaRepository<Company,Long>{

}
