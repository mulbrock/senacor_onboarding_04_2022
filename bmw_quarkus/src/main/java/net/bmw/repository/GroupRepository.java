package net.bmw.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import net.bmw.model.Group;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GroupRepository implements PanacheRepository<Group> {

}
