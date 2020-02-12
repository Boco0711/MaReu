package com.example.mareu.model;

import java.util.List;

public class Meeting {

    private Integer id;
    private String meetingRoom;
    private String meetingDate;
    private String meetingStartingHour;
    private String meetingEndingHour;
    private String meetingSubject;
    private List<String> meetingParticipants;

    public Meeting(Integer id, String meetingRoom, String date, String meetingStartingHour, String meetingEndingHour, String meetingSubject, List<String> meetingParticipants) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.meetingDate = date;
        this.meetingStartingHour = meetingStartingHour;
        this.meetingEndingHour = meetingEndingHour;
        this.meetingSubject = meetingSubject;
        this.meetingParticipants = meetingParticipants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingStartingHour() {
        return meetingStartingHour;
    }

    public void setMeetingStartingHour(String meetingHour) {
        this.meetingStartingHour = meetingHour;
    }

    public String getMeetingEndingHour() {
        return meetingEndingHour;
    }

    public void setMeetingEndingHour(String meetingEndingHour) {
        this.meetingEndingHour = meetingEndingHour;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public List<String> getMeetingParticipants() {
        return meetingParticipants;
    }

    public void setMeetingParticipants(List<String> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }
}