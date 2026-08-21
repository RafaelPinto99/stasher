package io.github.rafaelpinto99.backend.auth;

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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmailIgnoreCase_matchesRegardlessOfCase() {
        User user = new User();
        ReflectionTestUtils.setField(user, "email", "Rafael@Example.com");
        ReflectionTestUtils.setField(user, "passwordHash", "hash");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmailIgnoreCase("rafael@example.com");

        assertThat(foundUser).isPresent();
    }

    @Test
    void existsByEmailIgnoreCase_matchesRegardlessOfCase(){
        User user = new User();

        ReflectionTestUtils.setField(user, "email", "Rafael@Example.com");
        ReflectionTestUtils.setField(user, "passwordHash", "hash");

        userRepository.save(user);

        boolean userExists = userRepository.existsByEmailIgnoreCase("rafael@example.com");

        assertThat(userExists).isTrue();
    }
}
