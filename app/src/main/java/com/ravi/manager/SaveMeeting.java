package com.ravi.manager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SaveMeeting extends AppCompatActivity {
EditText meetingWith, meetingDate,meetingTime,meetingVenue,meetingRequirements;
ImageView meetingSaveBtn,meetingUpdateBtn;
ImageView meetingDateIcon,meetingTimeIcon;
String srno;
Calendar mycalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_meeting);
        mycalendar=Calendar.getInstance();
        final int mYear=mycalendar.get(Calendar.YEAR);
        final int mMonth=mycalendar.get(Calendar.MONTH);
        final int mDay=mycalendar.get(Calendar.DAY_OF_MONTH);

        meetingWith=findViewById(R.id.meetingWith);
        meetingDate=findViewById(R.id.meetingDate);
        meetingTime=findViewById(R.id.meetingTime);
        meetingVenue=findViewById(R.id.meetingVenue);
        meetingRequirements=findViewById(R.id.meetingRequirements);
        meetingSaveBtn=findViewById(R.id.meetingSaveBtn);
        meetingUpdateBtn=findViewById(R.id.meetingUpdateBtn);
        meetingDateIcon=findViewById(R.id.meetingdateIcon);
        meetingTimeIcon=findViewById(R.id.meetingtimeIcon);
        meetingDate.setKeyListener(null);
        meetingTime.setKeyListener(null);
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            meetingSaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            meetingUpdateBtn.setVisibility(View.VISIBLE);
            // deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            meetingUpdateBtn.setVisibility(View.GONE);
            //deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }


        meetingTimeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(SaveMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int HOUR_OF_DAY, int minute) {
                        int hour=HOUR_OF_DAY%12;
                        if(hour==0)
                        {
                            hour=12;
                        }
                        String a=String.format("%02d:%02d%s",hour,minute,HOUR_OF_DAY<12?"am":"pm");
                        meetingTime.setText(a);
                    }
                }, mycalendar.get(Calendar.HOUR_OF_DAY), mycalendar.get(Calendar.MINUTE), false).show();
            }
        });
        meetingDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatePickerDialog mydialog= new DatePickerDialog(SaveMeeting.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String month2=((month+1)<10)? "0" + (month+1):String.valueOf(month+1);
                        String day2=(day<10)? "0" + day:String.valueOf(day);
                        meetingDate.setText(String.valueOf(year) + "-" + month2 + "-" + day2 );

                    }
                }, mYear, mMonth, mDay);
               mydialog.getDatePicker().setMaxDate(new java.util.Date().getTime());
               mydialog.show();
            }
        });






        meetingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meetingWith.getText().toString().equals(""))
                {
                    meetingWith.setError("Cannot be blank");
                }
                else if(meetingDate.getText().toString().equals(""))
                {
                    meetingDate.setError("Cannot be blank");
                }
                else if(meetingTime.getText().toString().equals(""))
                {
                    meetingTime.setError("Cannot be blank");
                }
                else if(meetingVenue.getText().toString().equals(""))
                {
                    meetingVenue.setError("Cannot be blank");
                }
                else if(meetingRequirements.getText().toString().equals(""))
                {
                    meetingRequirements.setText("No requiremnets");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists meetingInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "mname varchar(100), mdate text,mtime varchar(100),mvenue varchar(100), mrequirements varchar(200))");

                            try {
                                mydb.execSQL("insert into meetingInfo(mname, mdate,mtime,mvenue,mrequirements) values(?,?,?,?,?)",
                                        new Object[]{meetingWith.getText().toString(), meetingDate.getText().toString(), meetingTime.getText().toString(), meetingVenue.getText().toString(),meetingRequirements.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SaveMeeting.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SaveMeeting.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SaveMeeting.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SaveMeeting.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SaveMeeting.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        meetingUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("update meetingInfo set mname=?, mdate=?, mtime=?,mvenue=?,mrequirements=? " +
                                        "where srno=?",
                                new String[]{meetingWith.getText().toString(), meetingDate.getText().toString(), meetingTime.getText().toString(), meetingVenue.getText().toString(),meetingRequirements.getText().toString(), srno});


                        mydb.close();
                        Toast.makeText(SaveMeeting.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveMeeting.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SaveMeeting.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    void fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from meetingInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    meetingWith.setText(myresult.getString(myresult.getColumnIndex("mname")));
                    meetingDate.setText(myresult.getString(myresult.getColumnIndex("mdate")));

                    meetingTime.setText(myresult.getString(myresult.getColumnIndex("mtime")));
                    meetingVenue.setText(myresult.getString(myresult.getColumnIndex("mvenue")));
                    meetingRequirements.setText(myresult.getString(myresult.getColumnIndex("mrequirements")));
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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mydeletemenu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.delete)
        {
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(SaveMeeting.this);
            mybuilder.setMessage("Are you Sure you want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from meetingInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(SaveMeeting.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            SaveMeeting.this.finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SaveMeeting.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveMeeting.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog mydialog=mybuilder.create();
            mydialog.show();
        }

        return true;
    }

}
