package org.acme.data.entities;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Group extends PanacheEntity {

    @Column
    private Date creatingTime;
    @Column
    public Date meetingTime;
    @ManyToMany
    @JoinTable(
            name = "group_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    public List<Person> groupMembers;

    public Group() {
        this.creatingTime = new Date();
    }

    public Date getCreatingTime() {
        return creatingTime;
    }

}
