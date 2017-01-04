package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2017, Month.JANUARY, 1, 7, 5, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2017, Month.JANUARY, 1, 21, 5, 0);

    private static final int USER_MEAL1_ID = START_SEQ + 2;
    private static final int USER_MEAL2_ID = START_SEQ + 3;
    private static final int USER_MEAL3_ID = START_SEQ + 4;
    private static final int USER_MEAL4_ID = START_SEQ + 5;
    private static final int USER_MEAL5_ID = START_SEQ + 6;
    private static final int USER_MEAL6_ID = START_SEQ + 7;
    private static final int ADMIN_MEAL1_ID = START_SEQ + 8;

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(2017, Month.JANUARY, 1, 7, 42, 0),
            "Завтрак", 510);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL2_ID, LocalDateTime.of(2017, Month.JANUARY, 1, 13, 15, 0),
            "Обед", 1010);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL3_ID, LocalDateTime.of(2017, Month.JANUARY, 1, 19, 0, 0),
            "Ужин", 300);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL4_ID, LocalDateTime.of(2017, Month.JANUARY, 2, 6, 0, 0),
            "Завтрак", 700);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL5_ID, LocalDateTime.of(2017, Month.JANUARY, 2, 14, 0, 0),
            "Обед", 923);
    public static final Meal USER_MEAL6 = new Meal(USER_MEAL6_ID, LocalDateTime.of(2017, Month.JANUARY, 2, 22, 0, 0),
            "Ужин", 513);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2017, Month.JANUARY, 1, 13, 32, 0),
            "Завтрак", 321);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
}
