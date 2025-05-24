package com.example.traineeship.mappers;

import com.example.traineeship.domainmodel.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyMapperTest {

    @Autowired
    private CompanyMapper companyMapper;

    @Test
    @DisplayName("Saving a Company should assign it an ID and allow it to be retrieved")
    void testSaveAndFindById() {
        Company comp = new Company("compA", "Acme Inc", "London");
        Company saved = companyMapper.save(comp);
        assertThat(saved).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("compA");
        Optional<Company> fetched = companyMapper.findById("compA");
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getCompanyName()).isEqualTo("Acme Inc");
        assertThat(fetched.get().getCompanyLocation()).isEqualTo("London");
    }

    @Test
    @DisplayName("findAll should include any saved companies")
    void testFindAll() {
        List<Company> before = companyMapper.findAll();
        int initialSize = before.size();

        Company c1 = new Company("c1", "First Co", "Paris");
        Company c2 = new Company("c2", "Second Co", "Berlin");
        companyMapper.save(c1);
        companyMapper.save(c2);
        List<Company> all = companyMapper.findAll();
        assertThat(all).hasSize(initialSize + 2).extracting(Company::getUsername).contains("c1", "c2");
    }
}
