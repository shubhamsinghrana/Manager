package com.ravi.manager;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FrontOptionsScreen extends AppCompatActivity {
    ImageView timeTableBtn, notesBtn, attendanceBtn,passwordBtn;
    //TextView masterBox;
    EditText masterPwdBox;
    ImageView loginBtn;
SQLiteDatabase mydb;
String dbpassword;
Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_options_screen);
        timeTableBtn=findViewById(R.id.timeTableBtn);
        notesBtn=findViewById(R.id.notesBtn);
        passwordBtn=findViewById(R.id.passwordBtn);
        attendanceBtn=findViewById(R.id.attendanceBtn);
        AnimationDrawable myanimation1= (AnimationDrawable) timeTableBtn.getDrawable();
        AnimationDrawable myanimation2= (AnimationDrawable) attendanceBtn.getDrawable();
        final AnimationDrawable myanimation3= (AnimationDrawable) passwordBtn.getDrawable();
       AnimationDrawable myanimation4= (AnimationDrawable) notesBtn.getDrawable();
        myanimation1.start();
        myanimation2.start();
        myanimation3.start();
        myanimation4.start();
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontOptionsScreen.this,DiaryList.class));
            }
        });
        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // setTheme(R.style.Theme_D1NoTitleDim);

                try{
                    mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("create table if not exists masterPwdInfo(srno Integer PRIMARY KEY AUTOINCREMENT , " +
                                "password varchar(100))");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(), "Can't Create table", Toast.LENGTH_SHORT).show();
                    }
                    Cursor myresult=mydb.rawQuery("select password from masterPwdInfo",null);
                    if(myresult.moveToNext())
                    {
                       // Toast.makeText(getBaseContext(), "Alread", Toast.LENGTH_SHORT).show();
                        //Intent myintent=new Intent(getBaseContext(),AlreadyUser.class);
                        // myintent.putExtra("Password",myresult.getString(myresult.getColumnIndex("password")));
                       // startActivity(myintent);
                         mydialog=new Dialog(FrontOptionsScreen.this);
                        mydialog.setContentView(R.layout.alreadyuserdialog);
                        mydialog.show();
                        mydialog.setTitle("Enter Master Password");
                       // masterBox=mydialog.findViewById(R.id.masterBox);
                        masterPwdBox=mydialog.findViewById(R.id.masterPwdBox);
                        loginBtn=mydialog.findViewById(R.id.loginBtn);
                        loginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                                    try
                                    {
                                        Cursor myresult=mydb.rawQuery("select * from masterPwdInfo",null);
                                        if(myresult.moveToNext())
                                        {
                                            // titleBox.setText(myresult.getString(myresult.getColumnIndex("title")));
                                            //discriptionBox.setText(myresult.getString(myresult.getColumnIndex("description")));
                                            dbpassword=myresult.getString(myresult.getColumnIndex("password"));
                                           // Toast.makeText(getBaseContext(), dbpassword, Toast.LENGTH_SHORT).show();
                                        }
                                        mydb.close();

                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(getBaseContext(), "Error in Fetching from Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e)
                                {
                                    Toast.makeText(getBaseContext(), "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                if(dbpassword.equals(masterPwdBox.getText().toString()))
                                {
                                    Toast.makeText(getBaseContext(), "logged In succesfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(),loginentry.class));
                                    mydialog.dismiss();

                                }
                                else
                                {
                                    //Toast.makeText(getBaseContext(), "Invalid User!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(),AskingUserDetailsOnceScreen.class));
                                    mydialog.dismiss();
                                }
                            }
                        });
                    }
                    else
                    {
                        startActivity(new Intent(getBaseContext(),EnterPasswordFirstTime.class));
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), "Cant create database!!", Toast.LENGTH_SHORT).show();
                }
            }

        });
timeTableBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getBaseContext(),ShowTimeTable.class));
    }
});
attendanceBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getBaseContext(),MyAttendanceFirst.class));
    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.optionsmenulayout,menu);
        menu.add(1,1,1,"Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1)
        {
            startActivity(new Intent(getBaseContext(),myPrefernceJavaFile.class));
        }
        return true;
    }
}
