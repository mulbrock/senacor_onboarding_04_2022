package net.bmw.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import net.bmw.model.Person;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
}
