package jm;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    void createUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserById(int id);

    User getUserByLogin(String login);

}