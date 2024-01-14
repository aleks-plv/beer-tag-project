package com.company.web.springdemo.services;

import com.company.web.springdemo.models.Beer;
import com.company.web.springdemo.models.User;
import com.company.web.springdemo.repositories.BeerRepository;
import com.company.web.springdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BeerService beerService;

    @Autowired
    public UserServiceImpl(UserRepository repository, BeerService beerService) {
        this.userRepository = repository;
        this.beerService = beerService;
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public List<Beer> addBeerToWishlist(int userId, int beerId) {
        User user = userRepository.getById(userId);
        Beer beer = beerService.get(beerId);
        if(user.getWishList().add(beer)){
            userRepository.update(user);
        }
        return new ArrayList<>(user.getWishList());
    }

    @Override
    public List<Beer> removeBeerFromWishlist(int userId, int beerId) {
        User user = userRepository.getById(userId);
        Beer beer = beerService.get(beerId);
        if(user.getWishList().remove(beer)){
            userRepository.update(user);
        }
        return new ArrayList<>(user.getWishList());
    }
}