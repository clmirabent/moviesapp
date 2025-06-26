package com.moviesapp.backend;

import com.moviesapp.backend.models.User;
import com.moviesapp.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            var user = repository.save(new User("test", "test@test.com", "test"));
            log.info("Preloading " + user + " with id " + user.getId());
        };
    }
}
