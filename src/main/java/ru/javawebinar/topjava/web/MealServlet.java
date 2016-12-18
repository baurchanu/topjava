package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String ALL_MEALS = "/meals.jsp";
    private static final int CALORIES_PER_DAY = 2000;
    private MealDao dao;

    public MealServlet() {
        dao = new MealDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        try {
            String action = request.getParameter("action");
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.remove(id);
                forward = ALL_MEALS;
                request.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(dao.getAll(),
                        LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                HttpSession session = request.getSession(false);
                session.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(dao.getAll(),
                        LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                response.sendRedirect("meals");
                return;
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.getById(id);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("add")) {
                forward = INSERT_OR_EDIT;
            }
        } catch (NullPointerException e) {
            forward = ALL_MEALS;
            request.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(dao.getAll(),
                    LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeUtil.getPattern());
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
            meal = new Meal(dateTime,
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(ALL_MEALS);
        request.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(dao.getAll(),
                LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        view.forward(request, response);
    }
}