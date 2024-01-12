package com.company.web.springdemo.services;

import com.company.web.springdemo.models.User;

import java.util.List;

public interface UserService {
    User create (User user);
    List<User> getAll();
    User getById(int id);
    User getByUsername(String username);

}
