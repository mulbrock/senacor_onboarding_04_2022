package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person extends PanacheEntity {

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private int age;
    @JoinTable(
            name = "person_group",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") },
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = { "person_id", "group_id"}
                    )
            }
    )

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.MERGE
    })
    private Set<Group>groups;

    public Person(){
        this.groups = new HashSet<>();
    }

    public Set<Group> getGroups(){
        return this.groups;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
