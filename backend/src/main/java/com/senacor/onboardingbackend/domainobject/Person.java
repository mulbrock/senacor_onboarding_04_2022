package com.senacor.onboardingbackend.domainobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(name = "person")
@Entity
@Setter
@Getter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "deleted")
    private boolean deleted = false;

    @JoinTable(
        name = "group_person",
        joinColumns = {@JoinColumn(name = "person_id")},
        inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    /**
     * Don't change this
     */
    @Override
    public int hashCode() {
        return 19;
    }

    /**
     * Don't change this
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Person other = (Person) obj;
        return id != null && id.equals(other.getId());
    }
}
