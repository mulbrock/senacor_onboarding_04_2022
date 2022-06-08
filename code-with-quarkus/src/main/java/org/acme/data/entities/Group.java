package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.sql.Date;
import java.util.Set;

@Entity
public class Group extends PanacheEntity {

    @Column
    private Date creationTime;
    @Column
    public Date meetingTime;
    @ManyToMany(mappedBy = "groups")
    private Set<Person> members;

    public Set<Person> getMembers(){
        return this.members;
    }

}
