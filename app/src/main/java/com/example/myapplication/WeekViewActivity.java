package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.CalendarUtils.daysInMonthArray;
import static com.example.myapplication.CalendarUtils.daysInWeekArray;
import static com.example.myapplication.CalendarUtils.monthYearFromDate;
import static com.example.myapplication.CalendarUtils.selectedDate;
import static java.security.AccessController.getContext;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    DBCommands DB = new DBCommands(this);



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Intent i = new Intent();
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Toast.makeText(WeekViewActivity.this, "The username is " +username, Toast.LENGTH_SHORT).show();
        i.putExtra("username", username);
        i.putExtra("password", password);

        initWidgets();
        setWeekView();





    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);


        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Toast.makeText(WeekViewActivity.this, "The username is " +username, Toast.LENGTH_SHORT).show();
        CalendarUtils.selectedDate = date;
        Event.eventsList.clear();
        String thisday = CalendarUtils.selectedDate.toString();
        List eventName1 = DB.geteventNameuser(thisday, username);
        List eventName2 = DB.geteventName1user(thisday, username);
        for (int c = 0; c < eventName1.size(); c++) {
            String event1 = eventName1.get(c).toString();
            String event2 = eventName2.get(c).toString();

            Event newEvent = new Event(event1, CalendarUtils.selectedDate, event2);
            Event.eventsList.add(newEvent);

        }
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }



    private void setEventAdpater()
    {
        //facem 3 metode in db commands pt a extrage eventname si eventname1 si bagam in eventListview ca in eventeditactiviity

        //Event.eventsList = list;
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);




    }

    public void newEventAction(View view)
    {

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(WeekViewActivity.this, EventEditActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }

    public void backAction(View view)
    {

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(WeekViewActivity.this, AppointmentActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }
}