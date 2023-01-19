package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_info_activity);

        ImageView img = (ImageView) findViewById(R.id.menu);
        String userName = getIntent().getStringExtra("username");
        String pass = getIntent().getStringExtra("password");
        TextView text = (TextView) findViewById(R.id.help);
        TextView text1 = (TextView) findViewById(R.id.showemail);
        TextView text2 = (TextView) findViewById(R.id.showdateofBirth);

        DBCommands DB = new DBCommands(this);

        Boolean check = DB.checkusernamepassword(userName, pass);
        if (check==true) {
            String textinfo = "Welcome " + DB.setfirstname(userName, pass) + " " + DB.setlastname(userName, pass);
            Toast.makeText(DoctorInfoActivity.this,  "The text will be " + textinfo, Toast.LENGTH_SHORT).show();
            text.setText(textinfo);
            String textinfo1 =  DB.setemail(userName, pass) ;
            String textinfo2 =  DB.setdateofbirth(userName, pass) ;
            text1.setText(textinfo1);
            text2.setText(textinfo2);
        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(DoctorInfoActivity.this, img);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(DoctorInfoActivity.this,  "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        switch (menuItem.getItemId()) {
                            case R.id.item1:
                                Intent intent1 = new Intent(DoctorInfoActivity.this, DoctorHomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.item2:
                                Intent intent2 = new Intent(DoctorInfoActivity.this, DoctorInfoActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.item3:
                                Intent intent3 = new Intent(DoctorInfoActivity.this, DoctorPrescriptionActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.item4:
                                Intent intent4 = new Intent(DoctorInfoActivity.this, DoctorAppointmentActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.item5:
                                Intent intent5 = new Intent(DoctorInfoActivity.this, DoctorRecordsActivity.class);
                                startActivity(intent5);
                                break;
                            default:
                                Toast.makeText(DoctorInfoActivity.this, "It has entered the loop", Toast.LENGTH_SHORT).show();
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