package userservice;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String name, String email, Integer age) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        userDao.save(user);
    }

    public List<User> listUsers() {
        return userDao.getAll();
    }

    public User getUserById(long id) {
        return userDao.getById(id);
    }

    public void updateUser(User user, String newName, String newEmail, Integer newAge) {
        if (newName != null && !newName.isBlank()) user.setName(newName);
        if (newEmail != null && !newEmail.isBlank()) user.setEmail(newEmail);
        if (newAge != null) user.setAge(newAge);
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
