package net.bmw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    //TODO: Use Date Type
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String creatingTime;
    private String meetingTime;

    @ManyToMany(mappedBy = "groups", fetch =  FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Person> persons = new HashSet<>();

    public Group() {

    }

    public Group(String creatingTime, String meetingTime) {
        this.creatingTime = creatingTime;
        this.meetingTime = meetingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(String creatingTime) {
        this.creatingTime = creatingTime;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
        person.getGroups().add(this);
    }
}
