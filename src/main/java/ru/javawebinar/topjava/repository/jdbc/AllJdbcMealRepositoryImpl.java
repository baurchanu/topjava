package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;

@Repository
@Profile(Profiles.POSTGRES)
public class AllJdbcMealRepositoryImpl extends AbstractJdbcMealRepository {
    public AllJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
