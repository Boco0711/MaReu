package com.example.mareu.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.mareu.R;
import java.util.Calendar;

public class MeetingByDateDialog extends AppCompatDialogFragment {

    private TextView mDateSelected;
    private MeetingByRoomDialog.ExampleDialogListener listener;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.meeting_by_date_dialog, null);
        mDateSelected = view.findViewById(R.id.date_selected);
        builder.setView(view).setTitle("Filtrer par Salle");
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date = mDateSelected.getText().toString();
                        listener.applyTexts(date);
                    }
                });
        builder.setNeutralButton("Reinitialiser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String zeroDate = "";
                listener.applyTexts(zeroDate);
            }
        });
        mDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();}});
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String dayIs = convertMyIntToAString(day);
                String monthIs = convertMyIntToAString(month);
                String date = year+"/"+monthIs+"/"+dayIs;
                mDateSelected.setText(date);}};
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MeetingByRoomDialog.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    /**
     * Convert and int to a string
     * @param myInt to be converted
     * @return the string created
     */
    public String convertMyIntToAString(int myInt) {
        String myString = null;
        if (myInt<10) {
            myString = "0"+myInt;
        } else {
            myString = ""+myInt;
        }
        return myString;
    }
}
