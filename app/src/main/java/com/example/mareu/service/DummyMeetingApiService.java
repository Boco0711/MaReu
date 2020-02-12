package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public void generateMeetings() {
        meetings = DummyMeetingGenerator.generateMeetings();
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}