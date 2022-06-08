package org.acme.data.entities;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class Group extends PanacheEntity {

    @Column
    private Date creatingTime;
    @Column
    public Date meetingTime;
    @Column
    @ElementCollection
    public List<Long> groupMembers;

    public Group() {
        this.creatingTime = new Date();
    }

    public Date getCreatingTime() {
        return creatingTime;
    }

}
