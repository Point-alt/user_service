package userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class UserServiseTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        userService.createUser("Anna", "1234@gmail.com", 25);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assert savedUser.getName().equals("Anna");
        assert savedUser.getEmail().equals("1234@gmail.com");
        assert savedUser.getAge() == 25;
    }

    @Test
    void testListUsers() {
        userService.listUsers();
        verify(userDao).getAll();
    }

    @Test
    void testGetUserById() {
        userService.getUserById(1L);
        verify(userDao).getById(1L);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Old");
        user.setEmail("old@gmail.com");
        user.setAge(20);

        userService.updateUser(user, "New", "new@gmail.com", 30);

        assert user.getName().equals("New");
        assert user.getEmail().equals("new@gmail.com");
        assert user.getAge() == 30;

        verify(userDao).update(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        userService.deleteUser(user);
        verify(userDao).delete(user);
    }
}
