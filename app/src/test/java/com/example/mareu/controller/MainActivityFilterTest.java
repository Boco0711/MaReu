package com.example.mareu.controller;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFilterTest {

    String filterString;
    protected MeetingApiService mApiService = DI.getNewInstanceApiService();
    private List<Meeting> mMeetings = mApiService.getMeetings();
    private List<Meeting> meetingsFiltrer;

    @Test
    public void Given_Salle01_When_FilterAsk_Then_Display1Meeting() {
        meetingsFiltrer = new ArrayList<>();
        this.filterString = "Salle 01";
        for (Meeting meeting : mMeetings) {
            if (meeting.getMeetingRoom().contains(filterString) || meeting.getMeetingDate().contains(filterString)) {
                meetingsFiltrer.add(meeting);
            }
        }
        Assert.assertEquals(1, meetingsFiltrer.size());
    }

    @Test
    public void Given_Empty_When_FilterAsk_Then_Display1Meeting() {
        meetingsFiltrer = new ArrayList<>();
        this.filterString = "";
        for (Meeting meeting : mMeetings) {
            if (meeting.getMeetingRoom().contains(filterString) || meeting.getMeetingDate().contains(filterString)) {
                meetingsFiltrer.add(meeting);
            }
        }
        Assert.assertEquals(5, meetingsFiltrer.size());
    }
}