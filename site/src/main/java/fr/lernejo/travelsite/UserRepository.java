package fr.lernejo.travelsite;

import fr.lernejo.travelsite.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final UserRepository INSTANCE=new UserRepository();
    private final List<User> userList = new ArrayList<>();

    private UserRepository() {}

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public boolean addUser(User newUser) {
        userList.add(newUser);
        return true;
    }

    public User findUser(String userName) {
        for (User user : userList)
            if (user.userName().equals(userName))
                return user;

        throw new UserNotFoundException();
    }
}
