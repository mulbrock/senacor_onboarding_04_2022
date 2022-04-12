package net.bmw.dto;

public class GroupDto {
    private Long id;
    private String creationDate;
    private String meetingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "creationDate='" + creationDate + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                '}';
    }
}
