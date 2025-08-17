package userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public User createUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User updateUser(long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User updatedUser = userMapper.userDtoToUser(userDto);
        updatedUser.setId(id);
        updatedUser.setCreatedAt(existingUser.getCreatedAt());  // Сохраняем дату создания

        return userRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}