package net.bmw.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group extends PanacheEntity {
    //TODO: Use Date Type
    private Long id;
    private String creatingTime;
    private String meetingTime;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<Person> persons = new HashSet<>();

    public Group() {

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

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
