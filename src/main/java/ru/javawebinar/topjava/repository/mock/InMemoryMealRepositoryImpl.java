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
            User user = new User();
            user.setId(1);
            m.setUser(user);
            m.setId(counter.incrementAndGet());
            repository.put(m.getId(), m);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            LOG.info("The meal has been added. " + meal);
            return meal;
        } else if (userId == meal.getUser().getId()){
            repository.put(meal.getId(), meal);
            LOG.info("The meal has been successfully updated. " + meal);
            return meal;
        } else {
            LOG.info("Wrong user ID! The meal hasn't been updated.");
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal;
        try {
            meal = repository.get(id);
        } catch (NullPointerException e) {
            LOG.info("There is no such meal in the DB!");
            return false;
        }
        if (userId == meal.getUser().getId()) {
            repository.remove(id);
            LOG.info("The meal has been successfully removed from the DB.");
            return true;
        } else {
            LOG.info("Wrong user ID! The meal hasn't been removed.");
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal;
        try {
            meal = repository.get(id);
            LOG.info("The meal with getId {} has been successfully found.", id);
            return meal;
        } catch (NullPointerException e) {
            LOG.info("There is no meal with such id: " + id);
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = repository.values()
                    .stream()
                    .filter(meal -> meal.getUser().getId() == userId)
                    .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                    .collect(Collectors.toList());
        if (meals.size() > 0) {
            LOG.info("All user's meals have been gotten form the DB. User id: " + userId);
            return meals;
        } else {
            LOG.info("There are no meals in the DB. User id: " + userId);
            return Collections.emptyList();
        }
    }

    public List<Meal> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        List<Meal> meals = repository.values()
                .stream()
                .filter(meal -> meal.getUser().getId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenTime(meal.getDateTime().toLocalTime(), fromTime, toTime))
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), fromDate, toDate))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
        if (meals.size() > 0) {
            LOG.info("All filtered user's meals have been gotten form the DB. User id: " + userId);
            return meals;
        } else {
            LOG.info("There are no meals in the list. User id: " + userId);
            return Collections.emptyList();
        }
    }
}

