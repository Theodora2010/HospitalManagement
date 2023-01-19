package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ImageView img = (ImageView) findViewById(R.id.menu);
        ImageView info = (ImageView) findViewById(R.id.info);
        ImageView prescription = (ImageView) findViewById(R.id.prescription);
        ImageView records = (ImageView) findViewById(R.id.records);
        ImageView appointment = (ImageView) findViewById(R.id.appointment);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, img);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(HomeActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        switch (menuItem.getItemId()){
                            case R.id.item1:
                                Intent intent1 = new Intent(HomeActivity.this, HomeActivity.class);
                                intent1.putExtra("username", username);
                                intent1.putExtra("password", password);
                                startActivity(intent1);
                                break;
                            case R.id.item2:
                                Intent intent2 = new Intent(HomeActivity.this, InfoActivity.class);
                                intent2.putExtra("username", username);
                                intent2.putExtra("password", password);
                                startActivity(intent2);
                                break;
                            case R.id.item3:
                                Intent intent3 = new Intent(HomeActivity.this, PrescriptionActivity.class);
                                intent3.putExtra("username", username);
                                intent3.putExtra("password", password);
                                startActivity(intent3);
                                break;
                            case R.id.item4:
                                Intent intent4 = new Intent(HomeActivity.this, AppointmentActivity.class);
                                intent4.putExtra("username", username);
                                intent4.putExtra("password", password);
                                startActivity(intent4);
                                break;
                            case R.id.item5:
                                Intent intent5 = new Intent(HomeActivity.this, RecordsActivity.class);
                                intent5.putExtra("username", username);
                                intent5.putExtra("password", password);
                                startActivity(intent5);
                                break;
                            case R.id.item6:
                                Intent intent6 = new Intent(HomeActivity.this, MainActivity.class);
                                intent6.putExtra("username", username);
                                intent6.putExtra("password", password);
                                startActivity(intent6);
                                break;
                            default:
                                Toast.makeText(HomeActivity.this, "It has entered the loop" , Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PrescriptionActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AppointmentActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RecordsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }
}