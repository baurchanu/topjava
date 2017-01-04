package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-test.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: ");
            for (String str : appCtx.getBeanDefinitionNames()) {
                System.out.println(str);
            }
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.update(UserTestData.USER, UserTestData.USER.getId());
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2017, Month.JANUARY, 01), LocalTime.of(7, 0),
                            LocalDate.of(2017, Month.JANUARY, 01), LocalTime.of(14, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
