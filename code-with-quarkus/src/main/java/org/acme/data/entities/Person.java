package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
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

    public Set<Group> getGroups(){
        return this.groups;
    }
}
