package com.example.traineeship.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

public class CompanyServiceTest {

	@Mock
	private CompanyMapper companyMapper;
	
	@Mock
	private TraineeshipPositionMapper traineeshipPositionMapper;
	
	@InjectMocks
	CompanyServiceImpl companyService;
	
	
	@BeforeEach
	void setUp() {
		companyMapper = mock(CompanyMapper.class);
		traineeshipPositionMapper =mock(TraineeshipPositionMapper.class);
		companyService = mock(CompanyServiceImpl.class);
	}
	
	
	@Test
	@DisplayName("saveProfile Test")
	void saveProfileTest() {
        Company s = new Company();
        companyService.saveProfile(s);
        verify(companyMapper).save(s);
	}
	
	
	
}