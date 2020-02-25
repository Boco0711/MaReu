package com.example.mareu.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.mareu.R;
import com.example.mareu.events.AddMeetingEvent;
import com.example.mareu.model.Meeting;
import com.google.android.material.textfield.TextInputLayout;
import org.greenrobot.eventbus.EventBus;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddMeetingDialog extends AppCompatDialogFragment {
    private List<Meeting> mMeetings;
    private TextView mEditMeetingRoom;
    private TextView mEditMeetingDate;
    private TextView mEditMeetingStartHour;
    private TextView mEditMeetingEndHour;
    private TextInputLayout mEditMeetingSubject;
    private TextInputLayout mEditMeetingParticipants;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerBeginningMeeting;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerEndingMeeting;

    public AddMeetingDialog(List<Meeting> mMeetings) {
        this.mMeetings = mMeetings;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.add_meeting_dialog, null);

        mEditMeetingSubject = view.findViewById(R.id.edit_meeting_subject);
        mEditMeetingDate = view.findViewById(R.id.edit_meeting_date);
        mEditMeetingStartHour = view.findViewById(R.id.edit_meeting_start_hour);
        mEditMeetingEndHour = view.findViewById(R.id.edit_meeting_end_hour);
        mEditMeetingRoom = view.findViewById(R.id.edit_meeting_room);
        mEditMeetingParticipants = view.findViewById(R.id.edit_meeting_participant);

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();
        clickOnPositiveButton(dialog);
        getMeetingRoom();
        getMeetingDate();
        getMeetingStartHour();
        getMeetingEndHour();
        return dialog;
    }

    /**
     * Check if all field are filled
     * Then call the checkIfRoomIsAvailable with all the param of the new meeting
     * And close dialog with dialog.dismiss()
     *
     * @param dialog is An AlertDialog type
     */
    public void clickOnPositiveButton(final AlertDialog dialog) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean createANewMeeting;
                String subject = mEditMeetingSubject.getEditText().getText().toString();
                String date = mEditMeetingDate.getText().toString();
                String startHour = mEditMeetingStartHour.getText().toString();
                String endHours = mEditMeetingEndHour.getText().toString();
                String room = mEditMeetingRoom.getText().toString();
                String participants = mEditMeetingParticipants.getEditText().getText().toString();
                int startingHourNewMeeting = convertMyStringToAnInt(startHour.toLowerCase());
                int endingHourNewMeeting = convertMyStringToAnInt(endHours.toLowerCase());
                List<String> listOfParticipants = Arrays.asList(participants.split(","));

                String[] allStringArray = {subject, date, startHour, endHours, room};
                boolean allStringAreFilled = checkString(allStringArray);
                boolean userMeeting = validateEmail();

                if (!allStringAreFilled)
                    Toast.makeText(getContext(), R.string.NOT_ALL_FIELD_FILLED_CORRECTLY, Toast.LENGTH_SHORT).show();
                if (allStringAreFilled && userMeeting) {
                    createANewMeeting = checkIfRoomIsAvailable(room, date, startHour, endHours, subject, listOfParticipants, startingHourNewMeeting, endingHourNewMeeting);
                    if (!createANewMeeting) {
                        checkIfRoomIsAvailable(room, date, startHour, endHours, subject, listOfParticipants, startingHourNewMeeting, endingHourNewMeeting);
                    } else
                        dialog.dismiss();
                }

            }
        });
    }

    private boolean checkString(String[] toCheck) {
        for (String stringToCheck :toCheck) {
            if (stringToCheck.isEmpty())
                return false;
            }
        return true;
    }

    private boolean validateEmail() {
        String emailInput = mEditMeetingParticipants.getEditText().getText().toString().trim();
        String[] array = emailInput.split(",");
        if (emailInput.isEmpty()) {
            mEditMeetingParticipants.setError("Le champ ne peut être vide");
            return false;
        } else {
            for (String email : array) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                    mEditMeetingParticipants.setError("Vous devez saisir des adresses email valide");
                    return false;
                }
            }
            mEditMeetingParticipants.setError(null);
            return true;
        }
    }

    /**
     * Check if the room is taken at the time requested.
     * If one meeting existing already use the room at the date and hour requested it say it to the user
     * If not create the new meeting and return a boolean
     */
    private boolean checkIfRoomIsAvailable(String room, String date, String startHour, String endHours, String subject, List<String> listOfParticipants, int startingHourNewMeeting, int endingHourNewMeeting) {
        boolean isMeetingOkay = true;
        for (Meeting meeting : mMeetings) {
            if (meeting.getMeetingDate().toLowerCase().equals(date.toLowerCase()) && meeting.getMeetingRoom().toLowerCase().equals(room.toLowerCase())) {
                String meetingStartingHourString = meeting.getMeetingStartingHour().toLowerCase();
                String meetingEndingHourString = meeting.getMeetingEndingHour().toLowerCase();
                int meetingStartingHour = convertMyStringToAnInt(meeting.getMeetingStartingHour().toLowerCase());
                int meetingEndingHour = convertMyStringToAnInt(meeting.getMeetingEndingHour().toLowerCase());
                if (meetingStartingHour >= startingHourNewMeeting && meetingStartingHour >= endingHourNewMeeting || meetingEndingHour <= startingHourNewMeeting && meetingEndingHour <= endingHourNewMeeting) {
                } else {
                    Toast.makeText(getContext(), "Une réunion à déja lieu en " + room + " de " + meetingStartingHourString + " à " + meetingEndingHourString + " veuillez changez de salle ou de crénaux horaire", Toast.LENGTH_SHORT).show();
                    isMeetingOkay = false;
                }
            }
        }
        if (isMeetingOkay) {
            Meeting newMeeting = new Meeting(mMeetings.size() + 1, room, date, startHour, endHours, subject, listOfParticipants);
            EventBus.getDefault().post(new AddMeetingEvent(newMeeting));
        }
        return isMeetingOkay;
    }

    /**
     * Open a dialog to make the user select a room then set the textfield as the room selected
     */
    private void getMeetingRoom() {
        mEditMeetingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                final String[] room = {"Salle 01", "Salle 02", "Salle 03", "Salle 04", "Salle 05", "Salle 06", "Salle 07", "Salle 08", "Salle 09", "Salle 10"};
                builder2.setItems(room, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEditMeetingRoom.setText(room[which]);
                    }
                });
                AlertDialog dialog = builder2.create();
                dialog.show();
            }
        });
    }

    /**
     * Open a dialog to make the user select a date for the meeting he want to create then display it in the textfield
     */
    private void getMeetingDate() {
        mEditMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dayIs = convertMyIntToAString(day);
                String monthIs = convertMyIntToAString(month);
                String date = year + "/" + monthIs + "/" + dayIs;
                mEditMeetingDate.setText(date);
            }
        };
    }

    /**
     * Open a dialog to make the user select a starting hour for the meeting he want to create then display it in the textfield
     */
    private void getMeetingStartHour() {
        mEditMeetingStartHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, mTimeSetListenerBeginningMeeting, hour, minute, true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        mTimeSetListenerBeginningMeeting = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String hourIs = convertMyIntToAString(hour);
                String minuteIs = convertMyIntToAString(minute);
                String hourTime = hourIs + "h" + minuteIs;
                mEditMeetingStartHour.setText(hourTime);
            }
        };
    }

    /**
     * Open a dialog to make the user select an ending hour for the meeting he want to create then display it in the textfield
     */
    private void getMeetingEndHour() {
        mEditMeetingEndHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, mTimeSetListenerEndingMeeting, hour, minute, true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        mTimeSetListenerEndingMeeting = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String hourIs = convertMyIntToAString(hour);
                String minuteIs = convertMyIntToAString(minute);
                String hourTime = hourIs + "h" + minuteIs;
                mEditMeetingEndHour.setText(hourTime);
            }
        };
    }

    /**
     * Convert an int to a string. If the int is smaller than 10 add a 0 before hiim in the string
     *
     * @param myInt the int converted
     * @return the string created
     */
    private String convertMyIntToAString(int myInt) {
        String myString;
        if (myInt < 10) {
            myString = "0" + myInt;
        } else {
            myString = "" + myInt;
        }
        return myString;
    }

    /**
     * Convert a String to an int, remove the h of the string.
     *
     * @param myString the string to be converted
     * @return the int created
     */
    private int convertMyStringToAnInt(String myString) {
        int myInt = 0;
        if (myString != null && myString.length() > 0) {
            String newString = myString.replace("h", "");
            myInt = Integer.parseInt(newString);
        }
        return myInt;
    }
}
