package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.List;

public class EventEditActivity extends AppCompatActivity
{
    //private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    String eventName;
    String eventName1;
    String eventName2;
    DBCommands DB = new DBCommands(this);





    private LocalTime time;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityevent);
        Intent i = new Intent();
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Toast.makeText(EventEditActivity.this, "The username is " +username, Toast.LENGTH_SHORT).show();
        i.putExtra("username", username);
        i.putExtra("password", password);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(EventEditActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.appointment_motive));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myadapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                eventName = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        //eventTimeTV = findViewById(R.id.eventTimeTV);
        ArrayAdapter<String> myadapter1=new ArrayAdapter<String>(EventEditActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.appointment_time));
        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myadapter1);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item1 = parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                eventName1 = item1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        String nr ="1";
        List<String> labels = DB.getAllDoc(nr);
        ArrayAdapter<String> myadapter2 = new ArrayAdapter<String>(EventEditActivity.this,android.R.layout.simple_spinner_item, labels);
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(myadapter2);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                eventName2 = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void initWidgets()
    {
        eventDateTV = findViewById(R.id.eventDateTV);
    }



    public void saveEventAction(View view)
    {
        String date = CalendarUtils.selectedDate.toString();
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        if(username.equals("")||password.equals("")||eventName.equals("")||date.equals("")||eventName1.equals("")||eventName2.equals(""))
            Toast.makeText(EventEditActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else
            if(DB.checkdocapp(date,eventName1,eventName2)){
                Toast.makeText(EventEditActivity.this, "The current date selected or time is busy", Toast.LENGTH_SHORT).show();
            }else {
                Event newEvent = new Event(eventName, CalendarUtils.selectedDate, eventName1);
                Event.eventsList.add(newEvent);
                finish();
                DB.insertAppointment(username, password, eventName, date, eventName1, eventName2);
            }

    }


    public void backAction(View view)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(EventEditActivity.this, WeekViewActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }
}