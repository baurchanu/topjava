package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class MealServiceImpl implements MealService {
    private MealRepository repository;

    @Autowired
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id,int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
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
