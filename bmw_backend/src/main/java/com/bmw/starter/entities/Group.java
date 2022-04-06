package com.bmw.starter.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="groups")
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 40)
    private String name;

    @Column(name="creating_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime creatingTime;

    @Column(name="meeting_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime meetingTime;

    @ManyToMany(mappedBy = "groups")
    Set<Person> members;

    public Group() {
    }
    public Group(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(LocalDateTime creatingTime) {
        this.creatingTime = creatingTime;
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }
}
