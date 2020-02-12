package com.example.mareu.controller;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeetingByRoomDialog.ExampleDialogListener {
    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    ImageButton mAddMeetingButton;

    String filterRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService  = DI.getMeetingApiService();
        mMeetings = mApiService.getMeetings();
        mAddMeetingButton = (ImageButton) findViewById(R.id.add_meeting_button);
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        setUpRecyclerView();
    }

    private void openDialog() {
        AddMeetingDialog myDialog = new AddMeetingDialog();
        myDialog.show(getSupportFragmentManager(), "");
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(mMeetings);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchMeetingByRoom = menu.findItem(R.id.trier_par_salle);
        searchMeetingByRoom.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openMeetingByRoom();
                return true;
            }
        });

        MenuItem searchMeetingByDate = menu.findItem(R.id.trier_par_date);
        searchMeetingByDate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openMeetingByDate();
                return false;
            }
        });
        return true;
    }

    private void openMeetingByDate() {
        MeetingByDateDialog myMeetingByDateDialog = new MeetingByDateDialog();
        myMeetingByDateDialog.show(getSupportFragmentManager(), null);
    }

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
        mMeetings= mApiService.getMeetings();
        initList();
    }

    @Subscribe
    public void onAddMeeting(AddMeetingEvent event) {
        mApiService.addMeeting(event.meeting);
        initList();
    }

    private void initList() {
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new RecyclerViewAdapter(mMeetings));
    }

    @Override
    public void applyTexts(String room) {
        this.filterRoom = room;
        recyclerViewAdapter.getFilter().filter(filterRoom);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
            mApiService.generateMeetings();
            mMeetings = mApiService.getMeetings();
    }
}