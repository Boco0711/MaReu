package com.example.mareu.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyRecyclerViewHolder> implements Filterable {
    private List<Meeting> meetings;
    private List<Meeting> meetingsFull;

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        ImageButton mImageButton;

        MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTextView1 = (TextView) itemView.findViewById(R.id.image_name);
            mTextView2 = (TextView) itemView.findViewById(R.id.meeting_participants);
            mImageButton = (ImageButton) itemView.findViewById(R.id.delete_meeting);
        }
    }

    RecyclerViewAdapter(List<Meeting> meetingsList) {
        this.meetings = meetingsList;
        meetingsFull = new ArrayList<>(meetingsList);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting, parent, false);
        return new MyRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyRecyclerViewHolder holder, final int position) {
        Meeting currentMeeting = meetings.get(position);
        String meetingDetails = currentMeeting.getMeetingRoom() + " - " + currentMeeting.getMeetingStartingHour() + " - " + currentMeeting.getMeetingSubject();
        holder.mImageView.setImageResource(getGoodCircle(currentMeeting.getMeetingRoom()));
        holder.mTextView1.setText(meetingDetails);
        holder.mTextView2.setText(currentMeeting.getMeetingParticipants().toString().replace("[", "").replace("]", ""));
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meetings.get(position)));
            }
        });
    }

    private int getGoodCircle(String meetingRoom) {
        switch (meetingRoom) {
            case "Salle 01":
            case "Salle 06" :
                return R.drawable.black_circle;
            case "Salle 02" :
            case "Salle 07" :
                return R.drawable.blue_circle;
            case "Salle 03" :
            case "Salle 08" :
                return R.drawable.red_circle;
            case "Salle 04" :
            case "Salle 09" :
                return R.drawable.yellow_circle;
            case "Salle 05" :
            case "Salle 10" :
                return R.drawable.purple_circle;
                default:
                    return R.drawable.grey_circle;
        }
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    @Override
    public Filter getFilter() {
        return meetingFilter;
    }

    private Filter meetingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Meeting> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 || constraint == "Toutes") {
                filteredList.addAll(meetingsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Meeting meeting : meetingsFull) {
                    if (meeting.getMeetingRoom().toLowerCase().contains(filterPattern) || meeting.getMeetingDate().toLowerCase().contains(filterPattern)) {
                        filteredList.add(meeting);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meetings.clear();
            meetings.addAll((List<Meeting>) results.values);
            notifyDataSetChanged();
        }
    };
}