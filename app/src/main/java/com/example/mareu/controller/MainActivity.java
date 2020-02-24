package com.example.mareu.controller;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.events.AddMeetingEvent;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeetingByRoomDialog.ExampleDialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    protected MeetingApiService mApiService = DI.getNewInstanceApiService();
    private List<Meeting> mMeetings;
    private final List<Meeting> mMeetingsFinal = new ArrayList<>(mApiService.getMeetings());
    String filterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeetings = mApiService.getMeetings();
        ImageButton mAddMeetingButton = (ImageButton) findViewById(R.id.add_meeting_button);
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        setUpRecyclerView(mMeetings);
    }

    private void openDialog() {
        AddMeetingDialog myDialog = new AddMeetingDialog();
        myDialog.show(getSupportFragmentManager(), "Add a new Meeting in a dialog");
    }

    private void setUpRecyclerView(List<Meeting> meetings) {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(mMeetings, this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * Set and handle action on the menu
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchMeetingByRoom = menu.findItem(R.id.filtrer_par_salle);
        searchMeetingByRoom.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openMeetingByRoom();
                return true;
            }
        });

        MenuItem searchMeetingByDate = menu.findItem(R.id.filtrer_par_date);
        searchMeetingByDate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openMeetingByDate();
                return true;
            }
        });
        return true;
    }

    /**
     * Create a new MeetingByDateDialog and show it
     */
    private void openMeetingByDate() {
        MeetingByDateDialog myMeetingByDateDialog = new MeetingByDateDialog();
        myMeetingByDateDialog.show(getSupportFragmentManager(), null);
    }

    /**
     * Create a new MeetingByRoomDialog and show it
     */
    private void openMeetingByRoom() {
        MeetingByRoomDialog myMeetingByRoomDialog = new MeetingByRoomDialog();
        myMeetingByRoomDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onAddMeeting(AddMeetingEvent event) {
        mApiService.addMeeting(event.meeting);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * Init the list of meetings
     * give the list as param for the RecyclerViewAdapter
     */
    private void initList() {
        mMeetings.clear();
        mMeetings.addAll(mMeetingsFinal);
        setUpRecyclerView(mMeetings);
    }

    /**
     * Pass a filter to the recyclerViewAdapter
     * @param myFilter the filter
     */
    @Override
    public void applyTexts(String myFilter) {
        this.filterString = myFilter;
        recyclerViewAdapter.getFilter().filter(filterString);
    }

    /**
     * When the configuration of the device change, the list of meeting is reset
     * @param newConfig the newConfiguration
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initList();
    }
}