package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

public class DoctorRecordsActivity extends AppCompatActivity {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private Spinner spinnereventDateTV, spinnerTime, spinnerTypeOfApp;
    private EditText pacientusername;
    private String eventDateitem, eventTimeitem, eventNameitem, pacuser;
    DBCommands DB = new DBCommands(this);


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_records_activity);

        ImageView img = (ImageView) findViewById(R.id.menu);
        pacientusername = (EditText) findViewById(R.id.pacientusername);
        //selectedDate = LocalDate.now();
        //Toast.makeText(DoctorRecordsActivity.this, "You Clicked " + selectedDate, Toast.LENGTH_SHORT).show();


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(DoctorRecordsActivity.this, img);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(DoctorRecordsActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        switch (menuItem.getItemId()) {
                            case R.id.item1:
                                Intent intent1 = new Intent(DoctorRecordsActivity.this, DoctorHomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.item2:
                                Intent intent2 = new Intent(DoctorRecordsActivity.this, DoctorInfoActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.item3:
                                Intent intent3 = new Intent(DoctorRecordsActivity.this, DoctorPrescriptionActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.item4:
                                Intent intent4 = new Intent(DoctorRecordsActivity.this, DoctorAppointmentActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.item5:
                                Intent intent5 = new Intent(DoctorRecordsActivity.this, DoctorRecordsActivity.class);
                                startActivity(intent5);
                                break;
                            default:
                                Toast.makeText(DoctorRecordsActivity.this, "It has entered the loop", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        spinnereventDateTV = (Spinner) findViewById(R.id.spinnereventDateTV);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        spinnerTypeOfApp = (Spinner) findViewById(R.id.spinnerTypeOfApp);









    }

    public void seeAppAction(View view) {
        pacuser = pacientusername.getText().toString();
        if (pacuser != null) {

            List appointmentdate1 = DB.appointmentdate(pacuser);
            ArrayAdapter<String> myadapter1=new ArrayAdapter<String>(DoctorRecordsActivity.this,android.R.layout.simple_list_item_1,appointmentdate1);
            myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnereventDateTV.setAdapter(myadapter1);

            spinnereventDateTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String item1 = parentView.getItemAtPosition(position).toString();
                    Toast.makeText(parentView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                    eventDateitem = item1;
                    if (pacuser != null && eventDateitem != null) {
                        List appointmenttime1 = DB.appointmenttimeselected(pacuser, eventDateitem);
                        ArrayAdapter<String> myadapter2=new ArrayAdapter<String>(DoctorRecordsActivity.this,android.R.layout.simple_list_item_1,appointmenttime1);
                        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTime.setAdapter(myadapter2);

                        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String item1 = parentView.getItemAtPosition(position).toString();
                                Toast.makeText(parentView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                                eventTimeitem = item1;
                                if (pacuser != null && eventDateitem != null && eventTimeitem != null){
                                    List appointmentname1 = DB.appointmentName(pacuser, eventDateitem, eventTimeitem);
                                    ArrayAdapter<String> myadapter3=new ArrayAdapter<String>(DoctorRecordsActivity.this,android.R.layout.simple_list_item_1,appointmentname1);
                                    myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerTypeOfApp.setAdapter(myadapter3);

                                    spinnerTypeOfApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                            String item1 = parentView.getItemAtPosition(position).toString();
                                            Toast.makeText(parentView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                                            eventNameitem = item1;

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parentView) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {

                            }
                        });
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });

        }





    }


    public void moreAboutAppointment(View view) {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent2 = new Intent(DoctorRecordsActivity.this, DoctorMoreAboutAppActivity.class);
        intent2.putExtra("username", username);
        intent2.putExtra("password", password);
        intent2.putExtra("pacuser", pacuser);
        intent2.putExtra("eventDateitem", eventDateitem);
        intent2.putExtra("eventTimeitem", eventTimeitem);
        intent2.putExtra("eventNameitem", eventNameitem);
        startActivity(intent2);
    }
}