package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Person extends PanacheEntity {

    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public int age;
    @ManyToMany
    public List<Group> groups;

}
