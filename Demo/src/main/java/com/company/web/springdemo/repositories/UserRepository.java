package com.company.web.springdemo.repositories;

import com.company.web.springdemo.models.User;

import java.util.List;

public interface UserRepository {
    User create (User user);
    List<User> getAll();
    User getById(int id);
    User getByUsername(String username);
    void update(User user);

}

