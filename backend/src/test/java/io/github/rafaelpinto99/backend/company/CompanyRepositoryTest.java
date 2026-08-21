package io.github.rafaelpinto99.backend.company;

import io.github.rafaelpinto99.backend.TestcontainersConfiguration;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void findByNameIgnoreCase_matchesRegardlessOfCase(){
        Company company = new Company();
        ReflectionTestUtils.setField(company, "name", "Example");

        companyRepository.save(company);

        Optional<Company> foundCompany = companyRepository.findByNameIgnoreCase("example");

        assertThat(foundCompany).isPresent();
    }

    @Test
    void findByUrl_returnsMatchingCompany(){
        Company company = new Company();
        ReflectionTestUtils.setField(company, "name", "example");
        ReflectionTestUtils.setField(company, "url", "www.example.com");

        companyRepository.save(company);

        Optional<Company> foundUrl = companyRepository.findByUrl("www.example.com");

        assertThat(foundUrl).isPresent();
    }
}
