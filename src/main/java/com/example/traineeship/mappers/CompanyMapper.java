package com.example.traineeship.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traineeship.domainmodel.Company;

// D: changed to reflect that Company's primary key is String
public interface CompanyMapper extends JpaRepository<Company,String>{

}
