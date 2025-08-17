package userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

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
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("Anna", savedUser.getName());
        assertEquals("1234@gmail.com", savedUser.getEmail());
        assertEquals(25, savedUser.getAge());
    }

    @Test
    void testListUsers() {
        userService.listUsers();
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById() {
        userService.getUserById(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Old");
        user.setEmail("old@gmail.com");
        user.setAge(20);

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user)); // Mock findById

        userService.updateUser(1L, "New", "new@gmail.com", 30);

        assertEquals("New", user.getName());
        assertEquals("new@gmail.com", user.getEmail());
        assertEquals(30, user.getAge());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}