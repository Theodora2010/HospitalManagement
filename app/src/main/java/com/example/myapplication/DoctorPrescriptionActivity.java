package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorPrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_prescription_activity);

        ImageView img = (ImageView) findViewById(R.id.menu);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(DoctorPrescriptionActivity.this, img);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(DoctorPrescriptionActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        switch (menuItem.getItemId()) {
                            case R.id.item1:
                                Intent intent1 = new Intent(DoctorPrescriptionActivity.this, DoctorHomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.item2:
                                Intent intent2 = new Intent(DoctorPrescriptionActivity.this, DoctorInfoActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.item3:
                                Intent intent3 = new Intent(DoctorPrescriptionActivity.this, DoctorPrescriptionActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.item4:
                                Intent intent4 = new Intent(DoctorPrescriptionActivity.this, DoctorAppointmentActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.item5:
                                Intent intent5 = new Intent(DoctorPrescriptionActivity.this, DoctorPrescriptionActivity.class);
                                startActivity(intent5);
                                break;
                            default:
                                Toast.makeText(DoctorPrescriptionActivity.this, "It has entered the loop", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }
}