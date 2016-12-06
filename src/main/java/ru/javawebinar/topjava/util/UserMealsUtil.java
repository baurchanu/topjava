package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed meal : list) {
            System.out.println(meal);
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMealWithExceed> list = new ArrayList<>();

        for (int i = 0; i < mealList.size(); i++) {
            UserMeal meal = mealList.get(i);
            if (map.get(meal.getDateTime().toLocalDate()) == null) {
                map.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            } else {
                map.put(meal.getDateTime().toLocalDate(), map.get(meal.getDateTime().toLocalDate())
                        + meal.getCalories());
            }
        }

        for (int i = 0; i < mealList.size(); i++) {
            UserMeal meal = mealList.get(i);
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                if (map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    list.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                } else {
                    list.add (new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
                }
            }
        }
        return list;
    }
}
