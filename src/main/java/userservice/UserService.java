package userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String name, String email, Integer age) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional
    public User updateUser (long id, String newName, String newEmail, Integer newAge) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (newName != null && !newName.isBlank()) user.setName(newName);
            if (newEmail != null && !newEmail.isBlank()) user.setEmail(newEmail);
            if (newAge != null) user.setAge(newAge);
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser (long id) {
        userRepository.deleteById(id);
    }
}
