package com.senacor.onboardingbackend.datatransferobject;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.List;

public class PersonDTO {

    @Data
    public static class CreateUpdatePersonDTO {

        @NotBlank(message = "First name may not be blank")
        private String firstName;

        @NotBlank(message = "Last name may not be blank")
        private String lastName;

        @PositiveOrZero(message = "Age cannot be negative")
        @NotNull(message = "Age may not be null")
        private Integer age;
    }

    @Data
    @Builder
    public static class ReadPersonDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private Integer age;
        private List<GroupInReadPersonDTO> groups;
    }

    @Data
    @Builder
    public static class GroupInReadPersonDTO {
        private Long id;
        private OffsetDateTime dateCreated;
        private OffsetDateTime dateMeeting;
    }
}
