package com.example.mareu.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.mareu.R;

public class MeetingByRoomDialog extends AppCompatDialogFragment {

    private TextView mRoomSelected;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.meeting_by_room_dialog, null);
        mRoomSelected = view.findViewById(R.id.room_selected);
        builder.setView(view).setTitle("Filtrer par Salle");
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String room = mRoomSelected.getText().toString();
                        listener.applyTexts(room);
                    }
                });
        builder.setNeutralButton("Reinitialiser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String zeroRoom = "";
                listener.applyTexts(zeroRoom);
            }
        });
        mRoomSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                final String[] room = {"Salle 01", "Salle 02", "Salle 03", "Salle 04", "Salle 05", "Salle 06", "Salle 07", "Salle 08", "Salle 09", "Salle 10", "Toutes"};
                builder2.setItems(room, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRoomSelected.setText(room[which]);
                    }});
                AlertDialog dialog = builder2.create();
                dialog.show();}});
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String room);
    }
}
