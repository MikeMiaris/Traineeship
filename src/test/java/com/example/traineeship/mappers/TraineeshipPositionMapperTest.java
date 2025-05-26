package com.example.traineeship.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.TraineeshipPosition;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TraineeshipPositionMapperTest {

    @Autowired
    private TraineeshipPositionMapper traineeshipPositionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Test
    @DisplayName("findById returns saved position")
    void testFindById() {
        TraineeshipPosition position = new TraineeshipPosition("Backend Developer","Build REST APIs",LocalDate.now(), LocalDate.now().plusMonths(3), "Spring,Java","Java,REST");

        TraineeshipPosition saved = traineeshipPositionMapper.save(position);

        Optional<TraineeshipPosition> found = traineeshipPositionMapper.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Backend Developer", found.get().getTitle());
    }

    @Test
    @DisplayName("findByLocation returns positions in specified company location")
    void testFindByLocation() {
        Company company = new Company();
        company.setUsername("comp1");
        company.setCompanyLocation("Athens");

        Company savedCompany = companyMapper.save(company);

        TraineeshipPosition pos = new TraineeshipPosition(
            "AI Intern",
            "Work on models",
            LocalDate.now(),
            LocalDate.now().plusMonths(3),
            "AI,ML",
            "Python"
        );
        pos.setCompany(savedCompany);
        traineeshipPositionMapper.save(pos);

        List<TraineeshipPosition> results = traineeshipPositionMapper.findByLocation("Athens");

        assertEquals(1, results.size());
        assertEquals("AI Intern", results.get(0).getTitle());
    }

    @Test
    @DisplayName("findByTopic returns positions containing topic")
    void testFindByTopic() {
        TraineeshipPosition pos = new TraineeshipPosition("ML Research", "Deep Learning and AI",LocalDate.now(),LocalDate.now().plusMonths(3), "ML,AI,Vision","Python");
        traineeshipPositionMapper.save(pos);

        List<TraineeshipPosition> results = traineeshipPositionMapper.findBytopic(List.of("AI"));

        assertFalse(results.isEmpty());
        assertEquals("ML Research", results.get(0).getTitle());
    }

    @Test
    @DisplayName("getAllAssigned returns only assigned positions with valid toDate")
    void testGetAllAssigned() {
        TraineeshipPosition assigned = new TraineeshipPosition("Security Intern","Work on firewall setup", LocalDate.now(), LocalDate.now().plusDays(10), "Security,Networking","Linux"
        );
        assigned.setAssigned(true);

        TraineeshipPosition expired = new TraineeshipPosition("DevOps Intern","Setup CI/CD pipelines", LocalDate.now().minusMonths(2), LocalDate.now().minusDays(1),  "DevOps","Docker" );
        expired.setAssigned(true);

        traineeshipPositionMapper.save(assigned);
        traineeshipPositionMapper.save(expired);

        List<TraineeshipPosition> results = traineeshipPositionMapper.getallAssigned();

        assertEquals(1, results.size());
        assertEquals("Security Intern", results.get(0).getTitle());
    }
}
