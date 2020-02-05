package com.example.mareu.model;

import java.util.List;

public class Meeting {

    private Integer id;
    private String meetingAvatar;
    private String meetingRoom;
    private String meetingDate;
    private String meetingHour;
    private String meetingSubject;
    private List<String> meetingParticipants;

    public Meeting(Integer id, String meetingAvatar, String meetingRoom, String date, String meetingHour, String meetingSubject, List<String> meetingParticipants) {
        this.id = id;
        this.meetingAvatar = meetingAvatar;
        this.meetingRoom = meetingRoom;
        this.meetingDate = date;
        this.meetingHour = meetingHour;
        this.meetingSubject = meetingSubject;
        this.meetingParticipants = meetingParticipants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeetingAvatar() {
        return meetingAvatar;
    }

    public void setMeetingAvatar(String meetingAvater) {
        this.meetingAvatar = meetingAvatar;
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

    public String getMeetingHour() {
        return meetingHour;
    }

    public void setMeetingHour(String meetingHour) {
        this.meetingHour = meetingHour;
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