package userservice;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoIntegrationTest {
    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDao userDao;
    @BeforeAll
    void setup() {
        postgres.start();

        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        HibernateUtil.rebuildSessionFactory();
        userDao = new UserDao();
    }

    @Test
    void testSaveAndGet() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@gmail.com");
        user.setAge(40);

        userDao.save(user);

        assertTrue(user.getId() > 0);

        User fetched = userDao.getById(user.getId());
        assertEquals("Bob", fetched.getName());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setName("Before");
        user.setEmail("before@gmail.com");
        user.setAge(20);
        userDao.save(user);

        user.setName("After");
        userDao.update(user);

        User updated = userDao.getById(user.getId());
        assertEquals("After", updated.getName());
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setName("Temp");
        user.setEmail("temp@gmail.com");
        user.setAge(50);
        userDao.save(user);

        userDao.delete(user);

        assertNull(userDao.getById(user.getId()));
    }

    @AfterAll
    void teardown() {
        postgres.stop();
    }
}
