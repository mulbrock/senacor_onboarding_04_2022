package net.bmw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persons")
// TODO: Remove Getter and Setter and use PanacheEntity or Lombok
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "firstName may not be blank.")
    private String firstName;
    @NotBlank(message = "lastName may not be blank.")
    private String lastName;
    private Integer age;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name= "persons_group", joinColumns = @JoinColumn(name="persons_id"), inverseJoinColumns = @JoinColumn(name = "groups_id"))
    Set<Group> groups = new HashSet<>();

    public Person() {
    }

    public Person(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.getPersons().add(this);
    }

    public Set<Group> removeGroup(Long groupId) {
       Group foundGroup = this.groups.stream().filter(group -> group.getId().equals(groupId)).findFirst().orElse(null);
        if(foundGroup != null) {
            this.groups.remove(foundGroup);
            foundGroup.getPersons().remove(this);
        }
        return groups;
    }

    public Set<Group> getGroups() {
        return groups;
    }
}
