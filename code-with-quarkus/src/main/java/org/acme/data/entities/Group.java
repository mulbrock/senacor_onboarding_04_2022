package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Group extends PanacheEntity {

    @Column
    private LocalDateTime creationTime;
    @Column
    public LocalDateTime meetingTime;
    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private Set<Person> members;

    public Group(){
        this.creationTime = LocalDateTime.now();
    }

    public Set<Person> getMembers(){
        return this.members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }

    public LocalDateTime getCreationTime(){
        return this.creationTime;
    }

}
