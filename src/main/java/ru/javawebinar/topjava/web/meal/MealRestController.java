package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
public class MealRestController {

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        meal.setUserId(AuthorizedUser.getId());
        return service.save(meal, AuthorizedUser.getId());
    }

    public void update(Meal meal) {
        meal.setUserId(AuthorizedUser.getId());
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

    public List<MealWithExceed> getAllFiltered(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return MealsUtil.getWithExceeded(service.getAllFiltered(AuthorizedUser.getId(), fromDate, toDate, fromTime, toTime), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
