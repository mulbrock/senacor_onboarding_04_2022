package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Person extends PanacheEntity {

    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public int age;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
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
    private Set<Group>groups;

}
