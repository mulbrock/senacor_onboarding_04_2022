package org.acme.controllers.transfer;

import java.time.LocalDateTime;
import java.util.List;

public class GroupTransferObject {

    private Long id;
    private LocalDateTime creationTime;
    private LocalDateTime meetingTime;
    private List<Long> memberIDs;

    public static class MemberInGroupDTO{

        private Long id;
        private String firstName;
        private String lastName;
        private int age;
    }

}
