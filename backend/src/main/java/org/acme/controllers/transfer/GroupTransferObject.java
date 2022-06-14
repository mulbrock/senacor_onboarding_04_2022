package org.acme.controllers.transfer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public class GroupTransferObject {

    @Data
    public static class CreateGroupDTO {
        @Future(message = "The meeting time has to be in the future")
        private LocalDateTime meetingTime;
        @NotEmpty(message = "Group must not be empty")
        private List<Long> memberIDs;
    }

    @Data
    @Builder
    public static class ReadGroupDTO {
        private Long id;
        private LocalDateTime creationTime;
        private LocalDateTime meetingTime;
        private List<MemberInGroupDTO> groupMembers;
    }

    @Data
    @Builder
    public static class MemberInGroupDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private int age;
    }

}
