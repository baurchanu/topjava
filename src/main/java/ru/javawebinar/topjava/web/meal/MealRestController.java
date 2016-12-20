package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealRestController {
    private MealService service = new MealServiceImpl();

    public Meal create(Meal meal) {
        User user = new User();
        user.setId(AuthorizedUser.getId());
        meal.setUser(user);
        return service.save(meal, AuthorizedUser.getId());
    }

    public void update(Meal meal, int id) {
        meal.setId(id);
        User user = new User();
        user.setId(AuthorizedUser.getId());
        meal.setUser(user);
        service.save(meal, AuthorizedUser.getId());
    }

    public void delete(int id){
        service.delete(id, AuthorizedUser.getId());
    }

    public Meal get(int id){
        return service.get(id, AuthorizedUser.getId());
    }

    public List<MealWithExceed> getAll(){
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getAllFiltered(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return MealsUtil.getWithExceeded(service.getAllFiltered(userId, fromDate, toDate, fromTime, toTime), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
