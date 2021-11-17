package ru.otus.spring.health;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DbHealthCheck implements HealthIndicator {
    private static final Logger log = LoggerFactory.getLogger(DbHealthCheck.class);

    private final MongoTemplate mongoTemplate;

    @Override
    public Health health() {
        boolean mongoStatusUp = new MongoHealthIndicator(mongoTemplate).health().getStatus().equals(Status.UP);
        if (mongoStatusUp) {
            log.info("mongo database is up");
            return Health.up().status(Status.UP).withDetail("Message", "mongo database is up").build();
        } else {
            log.error("mongo database is down");
            return Health.down().status(Status.DOWN).withDetail("Message", "mongo database is down").build();
        }
    }
}
