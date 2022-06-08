package org.acme.data.entities;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Group extends PanacheEntity {

    private long id;
    private Date creatingTime;
    private Date meetingTime;

    public Group() {
        this.creatingTime = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatingTime() {
        return creatingTime;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }
}
