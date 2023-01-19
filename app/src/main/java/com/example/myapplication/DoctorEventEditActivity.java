package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.List;

public class DoctorEventEditActivity extends AppCompatActivity
{
    //private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private TextView pacientname;
    private TextView typeofapp;
    private EditText review;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    String eventName;
    String eventName1;
    String eventName2;
    String typeoffapp1;
    String lastname;
    String review1;
    DBCommands DB = new DBCommands(this);






    private LocalTime time;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activityevent);
        TextView pacientname = (TextView) findViewById(R.id.pacientname);
        TextView typeoffapp = (TextView) findViewById(R.id.typeoffapp);
        review = (EditText) findViewById(R.id.review);
        Intent i = new Intent();
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Toast.makeText(DoctorEventEditActivity.this, "The username is " +username, Toast.LENGTH_SHORT).show();
        i.putExtra("username", username);
        i.putExtra("password", password);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        lastname = DB.getlastnamefromusername(username);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        //eventTimeTV = findViewById(R.id.eventTimeTV);
        String date = CalendarUtils.selectedDate.toString();
        List appointmenttime = DB.appointmenttime(CalendarUtils.selectedDate.toString(), lastname);
        ArrayAdapter<String> myadapter1=new ArrayAdapter<String>(DoctorEventEditActivity.this,android.R.layout.simple_list_item_1,appointmenttime);
        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myadapter1);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item1 = parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                eventName1 = item1;
                String pacient1 = DB.setpacientlastname(date, eventName1, lastname);
                String pacient2 = DB.setpacientfirstname(date, eventName1, lastname);
                pacientname.setText(pacient1 +" "+ pacient2);

                typeoffapp1 = DB.settypeofapp(date, eventName1, lastname);
                typeoffapp.setText(typeoffapp1);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

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
        Intent intent = getIntent();

        byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //if(getIntent().hasExtra("byteArray")) {
            //ImageView previewThumbnail = new ImageView(this);
            //Bitmap bmp = BitmapFactory.decodeByteArray(
        //     getIntent().getByteArrayExtra("byteArray"),0,getIntent()
            //               .getByteArrayExtra("byteArray").length);
            //previewThumbnail.setImageBitmap(bmp);


        //}
        lastname = DB.getlastnamefromusername(username);
        String review2 = review.getText().toString();
        review1 = review2;
        String user = DB.setpacientusername(date, eventName1, lastname);
        //String
        Toast.makeText(DoctorEventEditActivity.this, "lastname: " + lastname+" reviw: "+review1+"user: "+ user, Toast.LENGTH_LONG).show();
        if(username.equals("")||password.equals("")||eventName1.equals("")||date.equals(""))
            Toast.makeText(DoctorEventEditActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else
            {
                DB.insertReview(user, typeoffapp1, date, eventName1, lastname, review1, byteArray);
            }

    }


    public void backAction(View view)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(DoctorEventEditActivity.this, DoctorWeekViewActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }




    public void saveImagine(View view) {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent2 = new Intent(DoctorEventEditActivity.this, DoctorImageEditActivity.class);
        intent2.putExtra("username", username);
        intent2.putExtra("password", password);
        startActivity(intent2);
    }
}