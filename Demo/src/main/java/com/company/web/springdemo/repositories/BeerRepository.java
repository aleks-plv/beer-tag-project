package com.company.web.springdemo.repositories;

import com.company.web.springdemo.models.Beer;

import java.util.List;

public interface BeerRepository {

    List<Beer> get(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String sortOrder);

    Beer getById(int id);

    Beer getByName(String name);

    void create(Beer beer);

    Beer update(Beer beer);

    void delete(int id);

}
