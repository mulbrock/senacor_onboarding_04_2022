package org.acme.controllers.transfer;

import java.time.LocalDateTime;
import java.util.Set;

public class PersonTransferObject {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Set<GroupInPersonDTO> groups;

    public static class GroupInPersonDTO{

        private Long id;
        private LocalDateTime creationTime;
        private LocalDateTime meetingTime;
    }
}
