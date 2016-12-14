package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MockDB;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Gus' on 13.12.2016.
 */
public class MealDao {
    private static final Logger LOG = getLogger(MealServlet.class);

    public Meal getById(int id) {
        try {
            return MockDB.getMealById(id);
        } catch (NullPointerException e) {
            LOG.debug("There is no id=" + id + " in the DB");
        }
        return null;
    }

    public void update(Meal meal) {
        MockDB.updateMeal(meal);
        LOG.debug("The meal has been updated. Id=" + meal.getId());
    }

    public void remove(int id) {
        MockDB.removeMeal(id);
        LOG.debug("The meal has been removed. Id=" + id);
    }

    public void add(Meal meal) {
        MockDB.addMeal(meal);
        LOG.debug("The new meal has been added.");
    }

    public List<Meal> getAll() {
        return MockDB.getAllMeals();
    }
}
