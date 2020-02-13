package com.example.mareu;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class MeetingApiServiceUnitTest {
    public MeetingApiService mApiService = DI.getNewInstanceApiService();

    @Test
    public void getMeetingWithSuccess() {
        String expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING.toString();
        String actualMeetings = mApiService.getMeetings().toString();
        Assert.assertEquals(expectedMeetings, actualMeetings);
    }

    @Test
    public void addMeetingWithSuccess() {
        List<Meeting> meetings = mApiService.getMeetings();
        int numberOfMeeting = meetings.size();
        Meeting meeting = new Meeting(7, "Salle 05", "2020/02/15", "15h00", "15h45", "Nom du jeu", Arrays.asList("tom", "paul", "henry", "jake"));
        mApiService.addMeeting(meeting);
        Assert.assertEquals(numberOfMeeting+1, meetings.size());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        List<Meeting> meetings = mApiService.getMeetings();
        int numberOfMeeting = meetings.size();
        Meeting meeting = meetings.get(1);
        mApiService.deleteMeeting(meeting);
        Assert.assertEquals(numberOfMeeting-1, meetings.size());
    }
}
