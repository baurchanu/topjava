package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(MealsUtil.getRandomMealsList(), LocalTime.MIN, LocalTime.MAX, 2000);
        LOG.debug("forward to meals.jsp");
        request.setAttribute("mealsList", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
