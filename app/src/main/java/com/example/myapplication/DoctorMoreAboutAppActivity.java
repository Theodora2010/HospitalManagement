package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

public class DoctorMoreAboutAppActivity extends AppCompatActivity {

    private TextView dateright, timeright, nameappright,  reviewright;
    private ImageView img;
    DBCommands DB = new DBCommands(this);


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_more_about_app_activity);

        dateright = (TextView) findViewById(R.id.dateright);
        timeright = (TextView) findViewById(R.id.timeright);
        nameappright = (TextView) findViewById(R.id.nameappright);
        reviewright = (TextView) findViewById(R.id.reviewright);
        img = (ImageView) findViewById(R.id.imageView);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String eventDateitem = getIntent().getStringExtra("eventDateitem");
        String eventTimeitem = getIntent().getStringExtra("eventTimeitem");
        String eventNameitem = getIntent().getStringExtra("eventNameitem");
        String pacuser = getIntent().getStringExtra("pacuser");

        dateright.setText(eventDateitem);
        timeright.setText(eventTimeitem);
        nameappright.setText(eventNameitem);

        String getreview = DB.getReview(pacuser, eventDateitem, eventTimeitem, eventNameitem);
        reviewright.setText(getreview);

        byte[] imgretrieved = DB.getImage(pacuser, eventDateitem, eventTimeitem, eventNameitem);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgretrieved , 0, imgretrieved .length);

        img.setImageBitmap(bitmap);




    }

    public void backAction(View view)
    {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent1 = new Intent(DoctorMoreAboutAppActivity.this, DoctorRecordsActivity.class);
        intent1.putExtra("username", username);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }


}
