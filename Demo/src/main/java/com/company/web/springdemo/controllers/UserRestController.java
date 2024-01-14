package com.company.web.springdemo.controllers;


import com.company.web.springdemo.exceptions.AuthorizationException;
import com.company.web.springdemo.helpers.AuthenticationHelper;
import com.company.web.springdemo.models.Beer;
import com.company.web.springdemo.models.User;
import com.company.web.springdemo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.annotation.HandlesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    public static final String UNAUTHORIZED_USER_ERROR_MESSAGE = "You are not authorized to browse user information";
    private UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserRestController(UserService userService, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;

    }

    @GetMapping
    public List<User> get(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            if (!user.isAdmin()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_USER_ERROR_MESSAGE);
            }
            return userService.getAll();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id, @RequestHeader HttpHeaders headers) {

        try {
            tryAuthorize(id, headers);
            return userService.getById(id);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/{wish-list}")
    public List<Beer> getWishList(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            User user = tryAuthorize(id, headers);
            return new ArrayList<>(user.getWishList());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{userId}/{wish-list}/{beerId}")
    public List<Beer> addBeerToWishlist(@PathVariable int userId,
                                        @PathVariable int beerId,
                                        @RequestHeader HttpHeaders headers) {
        try {
            User user = tryAuthorize(userId, headers);
            return userService.addBeerToWishlist(userId, beerId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/{wish-list}/{beerId}")
    public List<Beer> removeBeerFromoWishlist(@PathVariable int userId,
                                              @PathVariable int beerId,
                                              @RequestHeader HttpHeaders headers) {
        try {
            User user = tryAuthorize(userId, headers);
            return userService.removeBeerFromWishlist(userId, beerId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private User tryAuthorize(int id, HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        if (!user.isAdmin() && user.getId() != id) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_USER_ERROR_MESSAGE);
        }
        return user;
    }
}
