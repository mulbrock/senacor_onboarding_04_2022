package org.acme.controllers.transfer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class GroupTransferObject {

    @Data
    public static class CreateGroupDTO{
        private LocalDateTime meetingTime;
        private List<Long> memberIDs;
    }

    @Data
    @Builder
    public static class ReadGroupDTO{
        private Long id;
        private LocalDateTime creationTime;
        private LocalDateTime meetingTime;
        private List<MemberInGroupDTO> groupMembers;
    }

    @Data
    @Builder
    public static class MemberInGroupDTO{

        private Long id;
        private String firstName;
        private String lastName;
        private int age;
    }

}
