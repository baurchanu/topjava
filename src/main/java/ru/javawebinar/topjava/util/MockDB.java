package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gus' on 13.12.2016.
 */
public class MockDB {
    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static int counter = 0;

    static {
        List<Meal> list = new ArrayList<>();
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 550));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 499));
        list.forEach(meal -> {
           meal.setId(counter++);
           meals.put(meal.getId(), meal);
        });
    }

    private MockDB() {}

    public static Meal getMealById(int id){
        return meals.get(id);
    }

    public static synchronized void addMeal(Meal meal) {
        meal.setId(counter++);
        meals.put(meal.getId(), meal);
    }

    public static void updateMeal(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    public static void removeMeal(int id) {
        meals.remove(id);
    }

    public static List<Meal> getAllMeals() {
        List<Meal> list = new ArrayList<Meal>();
        list.addAll(meals.values());
        return list;
    }
}
