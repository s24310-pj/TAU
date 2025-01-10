package pl.pjatk.zjazd6.service;

import org.springframework.stereotype.Service;
import pl.pjatk.zjazd6.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User createUser(String name, String email) {
        User newUser = new User(idCounter.getAndIncrement(), name, email);
        users.add(newUser);
        return newUser;
    }

    public Optional<User> updateUser(Long id, String name, String email) {
        return getUserById(id).map(user -> {
            user.setName(name);
            user.setEmail(email);
            return user;
        });
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}
