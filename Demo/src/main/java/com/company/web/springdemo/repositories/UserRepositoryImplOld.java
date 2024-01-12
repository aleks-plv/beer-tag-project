package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImplOld implements UserRepository{
    private final List<User> users;

    public UserRepositoryImplOld() {
        users = new ArrayList<>();
        users.add(new User(1, "peter", "pass1", true));
        users.add(new User(2, "nadya", "pass2", false));
        users.add(new User(3, "vladi", "pass3", false));
    }

    @Override
    public User create(User user) {
      users.add(user);
      return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getById(int id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getByUsername(String username) {
        return getAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }
}

