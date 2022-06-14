package org.acme.controllers.transfer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class PersonTransferObject {

    @Data
    public static class CreateUpdatePersonDTO {
        private String firstName;
        private String lastName;
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
