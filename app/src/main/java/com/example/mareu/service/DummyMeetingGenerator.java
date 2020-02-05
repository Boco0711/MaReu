package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {
    private static List<String> meetingParticipants = Arrays.asList("Papa", "tom", "toto");

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, "http://i.pravatar.cc/150?u=a042581f4e29026704e", "Salle 01", "2020/05/01","14h00", "Mario", meetingParticipants),
            new Meeting(2, "http://i.pravatar.cc/150?u=a042581f4e29026704f", "Salle 02", "2020/04/02", "11h00", "Mario", meetingParticipants),
            new Meeting(3, "http://i.pravatar.cc/150?u=a042581f4e29026704b", "Salle 05", "2020/08/15", "15h00", "Mario", meetingParticipants),
            new Meeting(4, "http://i.pravatar.cc/150?u=a042581f4e29026704b", "Salle 03", "2021/01/01", "16h00", "Mario", meetingParticipants),
            new Meeting(5, "http://i.pravatar.cc/150?u=a042581f4e29026704b", "Salle 06", "2019/12/24", "16h00", "Luigi", meetingParticipants)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}