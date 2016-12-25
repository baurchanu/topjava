package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> {
            m.setUserId(1);
            m.setId(counter.incrementAndGet());
            repository.put(m.getId(), m);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            //The meal has been added
            return meal;
        } else if (userId == meal.getUserId()){
            repository.put(meal.getId(), meal);
            //The meal has been successfully updated
            return meal;
        } else {
            //Wrong user ID! The meal hasn't been updated
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        try {
            if (userId == meal.getUserId()) {
                repository.remove(id);
                //The meal has been successfully removed from the DB
                return true;
            } else {
                //Wrong user ID! The meal hasn't been removed
                return false;
            }
        } catch (NullPointerException e) {
            //There is no such meal in the DB!
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
        if (meals.size() > 0) {
            //All user's meals have been gotten form the DB
            return meals;
        } else {
            //There are no meals in the DB
            return Collections.emptyList();
        }
    }

    public List<Meal> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        List<Meal> allMeals = getAll(userId);
        List<Meal> meals = allMeals
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenTime(meal.getDateTime().toLocalTime(), fromTime, toTime))
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), fromDate, toDate))
                .collect(Collectors.toList());
        if (meals.size() > 0) {
            //All filtered user's meals have been gotten form the DB
            return meals;
        } else {
            //There are no meals in the list
            return Collections.emptyList();
        }
    }
}

