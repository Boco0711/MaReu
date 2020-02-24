package com.example.mareu.controller;

import android.content.Context;
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
    private final List<Meeting> meetingsFiltered;
    private final List<Meeting> meetingsFull;
    private Context mContext;

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        ImageButton mImageButton;

        MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextView1 = itemView.findViewById(R.id.image_name);
            mTextView2 = itemView.findViewById(R.id.meeting_participants);
            mImageButton = itemView.findViewById(R.id.delete_meeting);
        }
    }

    RecyclerViewAdapter(List<Meeting> meetingsList, Context context) {
        this.meetings = meetingsList;
        meetingsFull = new ArrayList<>(meetingsList);
        meetingsFiltered = new ArrayList<>();
        this.mContext = context;
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
                meetingsFull.remove(((MainActivity)mContext).mApiService.getMeetingById(meetings.get(position).getId()));
                EventBus.getDefault().post(new DeleteMeetingEvent(meetings.get(position).getId()));
            }
        });
    }

    /**
     * Bind an image to a room number
     * @param meetingRoom room number
     * @return a drawable
     */
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
            meetingsFiltered.clear();
            if (constraint == null || constraint.length() == 0 || constraint == "Toutes") {
                meetingsFiltered.addAll(meetingsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Meeting meeting : meetingsFull) {
                    if (meeting.getMeetingRoom().toLowerCase().contains(filterPattern) || meeting.getMeetingDate().toLowerCase().contains(filterPattern)) {
                        meetingsFiltered.add(meeting);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = meetingsFiltered;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meetings.clear();
            meetings.addAll((List<Meeting>) results.values);
            notifyDataSetChanged();
        }
    };
}