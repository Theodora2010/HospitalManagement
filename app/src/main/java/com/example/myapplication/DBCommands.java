package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBCommands extends SQLiteOpenHelper {
    public static final String DBNAME = "Database6.db";
    private LocalDate date;
    private String name;
    private String time;

    public DBCommands(Context context) {

        super(context, "Database6.db", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        onCreate(MyDB);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("PRAGMA foreign_keys = ON;");

        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("create Table users(" +
                            "username TEXT primary key, " +
                            "password TEXT ,  " +
                            "email TEXT, " +
                            "firstname TEXT, " +
                            "lastname TEXT, " +
                            "dateofbirth text," +
                            "checkDoctor INTEGER)");
        MyDB.execSQL("create Table appointment(" +
                            "username TEXT , " +
                            "password TEXT, " +
                            "eventName TEXT, " +
                            "date TEXT, " +
                            "eventName1 TEXT," +
                            "doctor TEXT," +
                            "review TEXT," +
                            "image BLOB )");

    }



    public Boolean insertData(String username, String password,String email,String firstname,String lastname, String dateofbirth, String checkDoctor){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("dateofbirth", dateofbirth);
        contentValues.put("checkdoctor", checkDoctor);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public String setfirstname(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select firstname from users where username = ? and password = ?", new String[] {username,password});
        cursor.moveToNext();
        @SuppressLint("Range") String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
        return firstname;
    }
    public String setlastname(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select lastname from users where username = ? and password = ?", new String[] {username,password});
        cursor.moveToNext();
        @SuppressLint("Range") String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
        return lastname;
    }
    public String setemail(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select email from users where username = ? and password = ?", new String[] {username,password});
        cursor.moveToNext();
        @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
        return email;
    }
    public String setdateofbirth(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select dateofbirth from users where username = ? and password = ?", new String[] {username,password});
        cursor.moveToNext();
        @SuppressLint("Range") String dateofbirth = cursor.getString(cursor.getColumnIndex("dateofbirth"));
        return dateofbirth;
    }

    public boolean insertAppointment(String user, String pass, String eventName, String date, String eventName1, String doctor) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", pass);
        contentValues.put("eventName", eventName);
        contentValues.put("date", date);
        contentValues.put("eventName1", eventName1);
        contentValues.put("doctor", doctor);
        long result = MyDB.insert("appointment", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public boolean checkAppointment(String username, String password, String date){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where username = ? and password = ? and date = ?", new String[] {username,password, date});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkdoctor(String user, String pass, String nr) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ? and checkDoctor = ?", new String[] {user,pass,nr});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public List<String> getAllDoc(String nr) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from users where  checkDoctor = ?", new String[] {nr});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
                list.add(lastname);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean checkdocapp(String date, String eventName1, String eventName2) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and eventName1 = ? and doctor = ?", new String[] {date, eventName1, eventName2});
        if(cursor.getCount()>1)
            return true;
        else
            return false;
    }

    


    public List<String> geteventNameuser(String thisday, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and username = ?", new String[]{thisday, username});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex("eventName"));
                list.add(eventName);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List geteventName1user(String thisday, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and username = ?", new String[]{thisday, username});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName1 = cursor.getString(cursor.getColumnIndex("eventName1"));
                list.add(eventName1);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<String> geteventNameDoc(String thisday, String doctor) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();

        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and doctor = ?", new String[]{thisday, doctor});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex("eventName"));
                list.add(eventName);
            } while (cursor.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public String getlastnamefromusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where  username = ?", new String[]{username});
        cursor.moveToNext();
        String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
        return lastname;
    }

    public List geteventName1Doc(String thisday, String doctor) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();

        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and doctor = ?", new String[]{thisday, doctor});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName1 = cursor.getString(cursor.getColumnIndex("eventName1"));
                list.add(eventName1);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List appointmenttime(String selectedDate, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  date = ? and doctor = ?", new String[]{selectedDate, username});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventName1 = cursor.getString(cursor.getColumnIndex("eventName1"));
                list.add(eventName1);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public String setpacientlastname(String date, String eventName1, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where date = ? and eventName1 = ? ", new String[] {date, eventName1});
        cursor.moveToNext();
        @SuppressLint("Range") String username1 = cursor.getString(cursor.getColumnIndex("username"));
        Cursor cursor1 = MyDB.rawQuery("Select * from users where username =?", new String[] {username1});
        cursor1.moveToNext();
        @SuppressLint("Range") String lastname = cursor1.getString(cursor1.getColumnIndex("lastname"));
        return lastname;
    }

    public String setpacientfirstname(String date, String eventName1, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where date = ? and eventName1 = ? and doctor = ?", new String[] {date, eventName1, username});
        cursor.moveToNext();
        @SuppressLint("Range") String username1 = cursor.getString(cursor.getColumnIndex("username"));
        Cursor cursor1 = MyDB.rawQuery("Select * from users where username =?", new String[] {username1});
        cursor1.moveToNext();
        @SuppressLint("Range") String firstname = cursor1.getString(cursor1.getColumnIndex("firstname"));
        return firstname;
    }

    public String settypeofapp(String date, String eventName1, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where date = ? and eventName1 = ? and doctor = ?", new String[] {date, eventName1, username});
        cursor.moveToNext();
        @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex("eventName"));
        return eventName;
    }
    public String setpacientusername(String date, String eventName1, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where date = ? and eventName1 = ? ", new String[] {date, eventName1});
        cursor.moveToNext();
        @SuppressLint("Range") String username1 = cursor.getString(cursor.getColumnIndex("username"));
        return username1;
    }

    public void insertReview(String pacient1, String typeoffapp, String date, String eventName1,  String username , String review, byte[] image) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Cursor cursor = MyDB.rawQuery("UPDATE appointment SET review=? WHERE username=? and eventName=? and date=? and eventName1=? and doctor=? ", new String[]{review, pacient1,  typeoffapp, date, eventName1, username});
        //cursor.moveToNext();
        ContentValues review1 = new ContentValues();
        ContentValues img = new ContentValues();
        img.put("image", image);
        review1.put("review", review);
        MyDB.update("appointment", review1, "username =? and date=? and eventName1=? and doctor=?", new String[] {pacient1, date, eventName1, username});
        MyDB.update("appointment", img, "username =? and date=? and eventName1=? and doctor=?", new String[] {pacient1, date, eventName1, username});
        //long result = MyDB.update("appointment",  review1, "username=? and eventName=? and date=? and eventName1=? and doctor=? ", new String[]{pacient1,  typeoffapp, date, eventName1, username} );
        //long result = MyDB.insert("appointment", null, contentValues);

    }


    public List appointmentdate(String pacuser) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  username=?", new String[]{pacuser});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                list.add(date);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List appointmenttimeselected(String pacuser, String eventDateitem) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  username=? and date=?", new String[]{pacuser, eventDateitem});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("eventName1"));
                list.add(time);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List appointmentName(String pacuser, String eventDateitem, String eventTimeitem) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where  username=? and date=? and eventName1=? ", new String[]{pacuser, eventDateitem, eventTimeitem});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("eventName"));
                list.add(name);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public String getReview(String pacuser, String eventDateitem, String eventTimeitem, String eventNameitem) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where username=? and date=? and eventName1=? and eventName=? ", new String[]{pacuser, eventDateitem, eventTimeitem, eventNameitem});
        cursor.moveToNext();
        @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
        return review;
    }

    public byte[] getImage(String pacuser, String eventDateitem, String eventTimeitem, String eventNameitem) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from appointment where username=? and date=? and eventName1=? and eventName=? ", new String[]{pacuser, eventDateitem, eventTimeitem, eventNameitem});
        cursor.moveToNext();
        @SuppressLint("Range") byte[] img = cursor.getBlob(cursor.getColumnIndex("image"));
        return img;
    }
}
