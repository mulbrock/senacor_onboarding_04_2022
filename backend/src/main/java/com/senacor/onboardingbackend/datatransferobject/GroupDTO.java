package com.senacor.onboardingbackend.datatransferobject;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class GroupDTO {

    @Data
    public static class CreateGroupDTO {

        @Future(message = "The meeting date of the group has to be in the future")
        private OffsetDateTime dateMeeting;

        @NotEmpty(message = "The group may not be empty (i.e. without persons)")
        private Set<Long> personIds;
    }

    @Data
    @Builder
    public static class ReadGroupDTO {
        private Long id;
        private OffsetDateTime dateCreated;
        private OffsetDateTime dateMeeting;
        private List<PersonInReadGroupDTO> persons;
    }

    @Data
    @Builder
    public static class PersonInReadGroupDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private Integer age;
    }
}
