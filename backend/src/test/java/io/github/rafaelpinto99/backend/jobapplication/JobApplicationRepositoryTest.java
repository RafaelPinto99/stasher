package io.github.rafaelpinto99.backend.jobapplication;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.rafaelpinto99.backend.TestcontainersConfiguration;
import io.github.rafaelpinto99.backend.auth.User;
import io.github.rafaelpinto99.backend.auth.UserRepository;
import io.github.rafaelpinto99.backend.company.Company;
import io.github.rafaelpinto99.backend.company.CompanyRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class JobApplicationRepositoryTest {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void findByUserId_returnsJobApplicationsForThatUser() {
        User user = new User();
        ReflectionTestUtils.setField(user, "email", "rafael@example.com");
        ReflectionTestUtils.setField(user, "passwordHash", "hash");

        userRepository.save(user);

        UUID userId = (UUID) ReflectionTestUtils.getField(user, "id");

        Company company = new Company();
        ReflectionTestUtils.setField(company, "name", "Example");

        companyRepository.save(company);

        JobApplication jobApplication = new JobApplication();
        ReflectionTestUtils.setField(jobApplication, "user", user);
        ReflectionTestUtils.setField(jobApplication, "company", company);
        ReflectionTestUtils.setField(jobApplication, "role", "Example");
        ReflectionTestUtils.setField(jobApplication, "status", JobApplication.Status.SAVED);

        jobApplicationRepository.save(jobApplication);

        List<JobApplication> foundApplications = jobApplicationRepository.findByUserId(userId);

        assertThat(foundApplications).containsExactly(jobApplication);
    }
}
