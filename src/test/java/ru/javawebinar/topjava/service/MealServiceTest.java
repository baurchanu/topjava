package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(USER_MEAL3, service.get(USER_MEAL3.getId(), USER.getId()));
    }

    @Test (expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER.getId());
    }

    @Test (expected = NotFoundException.class)
    public void getNotFound2() throws Exception {
        service.get(ADMIN_MEAL1.getId(), USER.getId());
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL3.getId(), USER.getId());
        MATCHER.assertCollectionEquals(Stream.of(USER_MEAL1, USER_MEAL2, USER_MEAL4, USER_MEAL5, USER_MEAL6)
                .sorted(MEAL_COMPARATOR).collect(Collectors.toList()), service.getAll(USER.getId()));
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER.getId());
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFound2() throws Exception {
        service.delete(ADMIN_MEAL1.getId(), USER.getId());
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> pop = Stream.of(USER_MEAL1, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6)
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), START_DATE_TIME, END_DATE_TIME))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
        Collection<Meal> db = service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER.getId());
        MATCHER.assertCollectionEquals(pop, db);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> pop = Stream.of(USER_MEAL1, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6)
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
        Collection<Meal> db = service.getAll(USER.getId());
        MATCHER.assertCollectionEquals(pop, db);
    }

    @Test
    public void update() throws Exception {
        Meal updMeal = new Meal(USER_MEAL3.getId(), LocalDateTime.of(2017, Month.JANUARY, 3, 19, 15, 0), "Ланч", 710);
        service.update(updMeal, USER.getId());
        MATCHER.assertCollectionEquals(Stream.of(USER_MEAL1, USER_MEAL2, updMeal, USER_MEAL4, USER_MEAL5, USER_MEAL6)
                .sorted(MEAL_COMPARATOR).collect(Collectors.toList()), service.getAll(USER.getId()));
    }

    @Test (expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updMeal = new Meal(ADMIN_MEAL1.getId(), LocalDateTime.of(2017, Month.JANUARY, 3, 19, 15, 0), "Ланч", 710);
        service.update(updMeal, USER.getId());
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.JANUARY, 3, 19, 15, 0), "Ланч", 710);
        Meal created = service.save(newMeal, USER.getId());
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Stream.of(newMeal, USER_MEAL1, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6)
                .sorted(MEAL_COMPARATOR).collect(Collectors.toList()), service.getAll(USER.getId()));
    }
}