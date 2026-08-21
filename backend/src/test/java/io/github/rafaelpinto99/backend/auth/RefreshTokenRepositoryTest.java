package io.github.rafaelpinto99.backend.auth;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.rafaelpinto99.backend.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class RefreshTokenRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void findByTokenHash_returnsMatchingToken(){
        User user = new User();
        ReflectionTestUtils.setField(user, "email", "rafael@example.com");
        ReflectionTestUtils.setField(user, "passwordHash", "hash");

        userRepository.save(user);

        RefreshToken refreshToken = new RefreshToken();

        ReflectionTestUtils.setField(refreshToken, "tokenHash", "some-hash");
        ReflectionTestUtils.setField(refreshToken, "user", user);

        refreshTokenRepository.save(refreshToken);

        Optional<RefreshToken> foundToken = refreshTokenRepository.findByTokenHash("some-hash");

        assertThat(foundToken).isPresent();
    }
}