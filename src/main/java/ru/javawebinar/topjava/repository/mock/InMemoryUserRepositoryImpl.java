package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        try {
            repository.remove(id);
            LOG.info("The user has been successfully removed. Id=" + id);
            return true;
        } catch (NullPointerException e) {
            LOG.info("The user hasn't been removed! Id=" + id);
            return false;
        }
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        LOG.info("The user has been successfully saved. " + user);
        return user;
    }

    @Override
    public User get(int id) {
        User user = null;
        try {
            user = repository.get(id);
            LOG.info("The user with getId {} has been successfully found.", id);
        } catch (NullPointerException e) {
            LOG.info("There is no user with such id: " + id);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        if (repository.values().size() > 0) {
            LOG.info("All users have been gotten from the DB.");
            return repository.values()
                    .stream()
                    .sorted((u1, u2) -> (u1.getName().compareTo(u2.getName())))
                    .collect(Collectors.toList());
        } else {
            LOG.info("There are no users in the DB.");
            return Collections.emptyList();
        }
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user;
        try {
            user = repository.values().stream().filter(u -> u.getEmail().equals(email)).findAny();
        } catch (NullPointerException e) {
            user = null;
        }
        if (user.isPresent()) {
            LOG.info("The user with email " + email + " has been successfully found. " + user.get());
            return user.get();
        } else {
            LOG.info("There is no user with such email: " + email + "!");
            return null;
        }
    }
}
