package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealServiceImpl implements MealService {

    private MealRepository repository = new InMemoryMealRepositoryImpl();

    @Override
    public Meal save(Meal meal, int userId) throws NotFoundException {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!repository.delete(id, userId)) {
            throw new NotFoundException("SERVICE: The meal hasn't been removed!");
        }
    }

    @Override
    public Meal get(int id,int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal != null) {
            return meal;
        } else {
            throw new NotFoundException("SERVICE: The meal hasn't been found!");
        }
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return repository.getAllFiltered(userId, fromDate, toDate, fromTime, toTime);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}
