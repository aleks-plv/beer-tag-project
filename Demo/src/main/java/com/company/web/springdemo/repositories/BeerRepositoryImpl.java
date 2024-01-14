package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.Beer;
import com.company.web.springdemo.models.Style;
import com.company.web.springdemo.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BeerRepositoryImpl implements BeerRepository{
    private final SessionFactory sessionfactory;
    @Autowired
    public BeerRepositoryImpl(SessionFactory sessionfactory) {
       this.sessionfactory = sessionfactory;
    }

    @Override
    public List<Beer> get(String name, Double minAbv, Double maxAbv,
                          Integer styleId, String sortBy, String sortOrder) {
        try(Session session = sessionfactory.openSession()){
            Query<Beer> query = session.createQuery("from Beer", Beer.class);
            return filterBeers(query.list(), name, minAbv, maxAbv, styleId, sortBy, sortOrder);
        }
    }

    @Override
    public Beer getById(int id) {
        try(Session session = sessionfactory.openSession()){
           Beer beer = session.get(Beer.class, id);
           if(beer == null){
               throw new EntityNotFoundException("Beer", id);
           }
           return beer;
        }
    }

    @Override
    public Beer getByName(String name) {
        try(Session session = sessionfactory.openSession()){
            Query<Beer> query = session.createQuery("from Beer where name = :name", Beer.class);
            query.setParameter("name", name);
            List<Beer> result = query.list();
            if(result.isEmpty()){
                throw new EntityNotFoundException("Beer", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public void create(Beer beer) {
        try (Session session = sessionfactory.openSession()){
            session.beginTransaction();
            session.persist(beer);
            session.getTransaction().commit();

        }
    }

    @Override
    public Beer update(Beer beer) {

        try(Session session = sessionfactory.openSession()){
            session.beginTransaction();
            session.merge(beer);
            session.getTransaction().commit();
            return beer;
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionfactory.openSession()){
            session.beginTransaction();
            session.remove(getById(id));
            session.getTransaction().commit();

        }
    }


    private static List<Beer> filterBeers(List<Beer> beers, String name, Double minAbv,
                                          Double maxAbv, Integer styleId, String sortBy,
                                          String sortOrder) {
        beers = filterByName(beers, name);
        beers = filterByAbv(beers, minAbv, maxAbv);
        beers = filterByStyle(beers, styleId);
        beers = sortBy(beers, sortBy);
        beers = order(beers, sortOrder);
        return beers;
    }

    private static List<Beer> filterByName(List<Beer> beers, String name) {
        if (name != null && !name.isEmpty()) {
            beers = beers.stream()
                    .filter(beer -> containsIgnoreCase(beer.getName(), name))
                    .collect(Collectors.toList());
        }
        return beers;
    }

    private static List<Beer> filterByAbv(List<Beer> beers, Double minAbv, Double maxAbv) {
        if (minAbv != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getAbv() >= minAbv)
                    .collect(Collectors.toList());
        }

        if (maxAbv != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getAbv() <= maxAbv)
                    .collect(Collectors.toList());
        }

        return beers;
    }

    private static List<Beer> filterByStyle(List<Beer> beers, Integer styleId) {
        if (styleId != null) {
            beers = beers.stream()
                    .filter(beer -> beer.getStyle().getId() == styleId)
                    .collect(Collectors.toList());
        }
        return beers;
    }

    private static List<Beer> sortBy(List<Beer> beers, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    beers.sort(Comparator.comparing(Beer::getName));
                    break;
                case "abv":
                    beers.sort(Comparator.comparing(Beer::getAbv));
                case "style":
                    beers.sort(Comparator.comparing(beer -> beer.getStyle().getName()));
                    break;
            }
        }
        return beers;
    }

    private static List<Beer> order(List<Beer> beers, String order) {
        if (order != null && !order.isEmpty()) {
            if (order.equals("desc")) {
                Collections.reverse(beers);
            }
        }
        return beers;
    }

    private static boolean containsIgnoreCase(String value, String sequence) {
        return value.toLowerCase().contains(sequence.toLowerCase());
    }
}
