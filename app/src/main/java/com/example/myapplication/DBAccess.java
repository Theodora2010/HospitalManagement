package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DBAccess instance;
    Cursor c = null;

    private DBAccess(Context context){
        this.openHelper = new DBOpenHelper(context);
    }

    public static DBAccess getInstance(Context context){
        if(instance==null){
            instance = new DBAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();

    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public Boolean insertData(String username, String password,String email,String firstname,String lastname, String dateofbirth){
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("dateofbirth", dateofbirth);
        long result = db.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        c=db.rawQuery("Select * from users where username = ?", new String[]{username});

        if (c.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){

        c = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }
}
