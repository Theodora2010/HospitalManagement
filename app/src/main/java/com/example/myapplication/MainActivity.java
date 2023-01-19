package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBCommands DB;



        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        DB = new DBCommands(this);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignoutActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        String nr = "1";
                        boolean checkdoctor = DB.checkdoctor(user,pass,nr);
                        if(checkdoctor){
                            Toast.makeText(MainActivity.this, "Sign in successfull doc", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(getApplicationContext(), DoctorHomeActivity.class);
                            intent.putExtra("username", user);
                            intent.putExtra("password", pass);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, "Sign in successfull user", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("username", user);
                            intent.putExtra("password", pass);
                            startActivity(intent);
                        }

                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}


