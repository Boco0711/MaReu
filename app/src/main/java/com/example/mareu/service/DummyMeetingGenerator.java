package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {
    private static List<String> meetingParticipants = Arrays.asList("Papa", "tom", "totoooo");

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1,"Salle 01", "2020/02/12", "09h00", "10h59", "Mario", meetingParticipants),
            new Meeting(2,"Salle 01", "2020/02/10", "14h50", "15h30", "Mario", meetingParticipants),
            new Meeting(3,"Salle 05", "2020/08/15", "15h00", "15h40", "Mario", meetingParticipants),
            new Meeting(4,"Salle 03", "2021/01/01", "16h00", "16h50", "Mario", meetingParticipants),
            new Meeting(5,"Salle 06", "2019/12/24", "16h00", "17h00", "Luigi", meetingParticipants)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}