package com.company.web.springdemo.services;

import com.company.web.springdemo.models.Beer;
import com.company.web.springdemo.models.User;

import java.util.List;

public interface UserService {
    User create (User user);
    List<User> getAll();
    User getById(int id);
    User getByUsername(String username);
    List<Beer> addBeerToWishlist(int userId, int beerId);
    List<Beer> removeBeerFromWishlist(int userId, int beerId);

}
