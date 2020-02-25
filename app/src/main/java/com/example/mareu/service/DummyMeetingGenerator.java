package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {
    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1,"Salle 01", "2020/02/12", "09h00", "10h59", "Sujet 01", Arrays.asList("jean@hotmail.fr", "suzanne@gmail.com")),
            new Meeting(2,"Salle 02", "2020/02/10", "14h50", "15h30", "Sujet 02", Arrays.asList("thomas@yahoo.fr", "michael@gmail.com")),
            new Meeting(3,"Salle 03", "2020/08/15", "15h00", "15h40", "Sujet 03", Arrays.asList("nicolas@hotmail.fr", "suzanne@gmail.com", "suzanne@gmail.com")),
            new Meeting(4,"Salle 04", "2021/01/01", "16h00", "16h50", "Sujet 04", Arrays.asList("jean@hotmail.fr", "michael@gmail.com", "thomas@yahoo.fr", "stéphane@gmail.fr")),
            new Meeting(5,"Salle 05", "2019/12/24", "16h00", "17h00", "Sujet 05", Arrays.asList("suzanne@gmail.com"))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}