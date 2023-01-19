package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.BreakIterator;
import java.util.Calendar;


public class SignoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signout_activity);


        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView email =(TextView) findViewById(R.id.mail);
        TextView firstname =(TextView) findViewById(R.id.firstName);
        TextView lastname =(TextView) findViewById(R.id.lastName);
        Button btnDatePicker= (Button) findViewById(R.id.dateOfBirth);
        TextView secdoc =(TextView) findViewById(R.id.secdoc);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        SignoutActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        DBCommands DB = new DBCommands(this);

        MaterialButton signupbtn = (MaterialButton) findViewById(R.id.signupbutton);

        //admin and admin

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();
                String firstName = firstname.getText().toString();
                String lastName = lastname.getText().toString();
                String dateofbirth = btnDatePicker.getText().toString();
                String checkdoc = secdoc.getText().toString();


                if(user.equals("")||pass.equals("")||mail.equals("")||firstName.equals("")||lastName.equals("")||dateofbirth.equals(""))
                    Toast.makeText(SignoutActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuser = DB.checkusername(user);
                    if(checkuser==false){
                        boolean insert;

                        if(checkdoc.equals("1234")){
                            insert = DB.insertData(user, pass, mail, firstName, lastName, dateofbirth, "1");

                        }else {
                            insert = DB.insertData(user, pass, mail, firstName, lastName, dateofbirth, "0");
                        }
                        String nr = "1";
                        boolean checkdoctor = DB.checkdoctor(user,pass,nr);
                        if(insert){
                            if(checkdoctor){
                                Toast.makeText(SignoutActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),DoctorHomeActivity.class);
                                intent.putExtra("username", user);
                                intent.putExtra("password", pass);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignoutActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                intent.putExtra("username", user);
                                intent.putExtra("password", pass);
                                startActivity(intent);
                            }

                        }else{
                            Toast.makeText(SignoutActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignoutActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}


