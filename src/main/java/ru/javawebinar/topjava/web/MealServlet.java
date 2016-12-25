package ru.javawebinar.topjava.web;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String autorizedUser = request.getParameter("autorizedUser");

        if (autorizedUser != null) {
            switch(autorizedUser) {
                case "user1":
                    AuthorizedUser.setId(1);
                    break;
                case "user2":
                    AuthorizedUser.setId(2);
                    break;
                default:
                    break;
            }
            response.sendRedirect("meals");
        } else {
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (meal.isNew()) {
                LOG.info("Create {}", meal);
                controller.create(meal);
            } else {
                LOG.info("Update {}", meal);
                controller.update(meal);
            }
            response.sendRedirect("meals");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("fromDate") != null) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");
            LocalTime startTime = LocalTime.MIN;
            LocalTime endTime = LocalTime.MAX;
            LocalDate startDate = LocalDate.MIN;
            LocalDate endDate = LocalDate.MAX;

            if (!fromTime.isEmpty()) {
                startTime = LocalTime.parse(fromTime);
            }
            if (!toTime.isEmpty()) {
                endTime = LocalTime.parse(toTime);
            }
            if (!fromDate.isEmpty()) {
                startDate = LocalDate.parse(fromDate);
            }
            if (!toDate.isEmpty()) {
                endDate = LocalDate.parse(toDate);
            }

            LOG.info("getAllFiltered");
            request.setAttribute("meals",
                    controller.getAllFiltered(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals",
                    controller.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}