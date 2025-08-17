package userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Чтобы не заменять Testcontainers DataSource
public class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testSaveAndGet() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@gmail.com");
        user.setAge(40);

        userRepository.save(user);

        assertThat(user.getId()).isNotNull();

        User fetched = userRepository.findById(user.getId()).orElse(null);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Bob");
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setName("Before");
        user.setEmail("before@gmail.com");
        user.setAge(20);
        userRepository.save(user);

        user.setName("After");
        userRepository.save(user);

        User updated = userRepository.findById(user.getId()).orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("After");
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setName("Temp");
        user.setEmail("temp@gmail.com");
        user.setAge(50);
        userRepository.save(user);

        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

}