package org.acme.controllers.transfer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

public class PersonTransferObject {

    @Data
    public static class CreateUpdatePersonDTO {
        @NotBlank(message = "A first name has to be given")
        private String firstName;
        @NotEmpty(message = "A last name has to be given. If you're Seal, Cher or McLovin, enter at a blank at least.")
        private String lastName;
        @NotEmpty(message = "An age hast to be given")
        @PositiveOrZero(message = "The value must be positive")
        private int age;
    }

    @Data
    @Builder
    public static class ReadPersonDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private int age;
        private List<GroupInPersonDTO> groups;
    }

    @Data
    @Builder
    public static class GroupInPersonDTO {

        private Long id;
        private LocalDateTime creationTime;
        private LocalDateTime meetingTime;
    }
}
