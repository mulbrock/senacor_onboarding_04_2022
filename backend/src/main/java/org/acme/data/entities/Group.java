package org.acme.data.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class Group extends PanacheEntity {

    @Column
    private LocalDateTime creationTime;
    @Column
    private LocalDateTime meetingTime;
    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private Set<Person> members;

    public Group(){
        this.creationTime = LocalDateTime.now();
        this.members = new HashSet<>();
    }
}
