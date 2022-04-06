package com.senacor.onboardingbackend.domainobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "group")
@Entity
@Setter
@Getter
@ToString
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created")
    private OffsetDateTime dateCreated;

    @Column(name = "date_meeting")
    private OffsetDateTime dateMeeting;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Person> persons = new HashSet<>();

    /**
     * Don't change this
     */
    @Override
    public int hashCode() {
        return 19;
    }

    /**
     * Don't change this
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Group other = (Group) obj;
        return id != null && id.equals(other.getId());
    }
}
