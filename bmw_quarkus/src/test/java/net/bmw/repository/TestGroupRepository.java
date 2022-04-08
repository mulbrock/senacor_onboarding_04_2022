package net.bmw.repository;

import io.quarkus.arc.Priority;
import net.bmw.model.Group;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import static org.junit.jupiter.api.Assertions.*;

@Priority(1)
@Alternative
@ApplicationScoped
class TestGroupRepository extends GroupRepository {
    @PostConstruct
    public void init() {
        persist(new Group("12.12.2012", "12.12.2013"), new Group("15.12.2012", "11.4.2013"), new Group("12.11.2014", "14.11.2014"));
    }

}