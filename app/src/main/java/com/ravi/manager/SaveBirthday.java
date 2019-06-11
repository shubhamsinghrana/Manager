package com.ravi.manager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
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
import android.widget.Toast;

import java.util.Calendar;

public class SaveBirthday extends AppCompatActivity {
Calendar mycalendar;
EditText name1,birthdate;
ImageView selectDate;
ImageView bdaySaveBtn,bdayUpdateBtn;
String srno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_birthday);
         mycalendar=Calendar.getInstance();
        final int mYear=mycalendar.get(Calendar.YEAR);
        final int mMonth=mycalendar.get(Calendar.MONTH);
        final int mDay=mycalendar.get(Calendar.DAY_OF_MONTH);
        name1=findViewById(R.id.name1);
        birthdate=findViewById(R.id.birthdate);
        birthdate.setKeyListener(null);
        bdaySaveBtn=findViewById(R.id.bdaySaveBtn);
        bdayUpdateBtn=findViewById(R.id.bdayUpdateBtn);
        setTitle("Save Birthday");
        selectDate=findViewById(R.id.selectDate);
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            bdaySaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            bdayUpdateBtn.setVisibility(View.VISIBLE);
            // deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            bdayUpdateBtn.setVisibility(View.GONE);
            //deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }


        bdaySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name1.getText().toString().equals(""))
                {
                    name1.setError("Cannot be blank");
                }
                else if(birthdate.getText().toString().equals(""))
                {
                    birthdate.setError("Cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists bdayInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "name varchar(100), bday text)");

                            try {
                                mydb.execSQL("insert into bdayInfo(name, bday) values(?,?)",
                                        new Object[]{name1.getText().toString(), birthdate.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SaveBirthday.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SaveBirthday.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SaveBirthday.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SaveBirthday.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SaveBirthday.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        bdayUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("update bdayInfo set name=?, bday=? " +
                                        "where srno=?",
                                new String[]{name1.getText().toString(),
                                        birthdate.getText().toString(), srno});


                        mydb.close();
                        Toast.makeText(SaveBirthday.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveBirthday.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SaveBirthday.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SaveBirthday.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String month2=((month+1)<10)? "0" + (month+1):String.valueOf(month+1);
                        String day2=(day<10)? "0" + day:String.valueOf(day);
                        birthdate.setText(String.valueOf(year) + "-" + month2 + "-" + day2 );

                    }
                }, mYear, mMonth, mDay).show();
            }
        });


    }
    void fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from bdayInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    name1.setText(myresult.getString(myresult.getColumnIndex("name")));
                    birthdate.setText(myresult.getString(myresult.getColumnIndex("bday")));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mydeletemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.delete)
        {

            AlertDialog.Builder mybuilder=new AlertDialog.Builder(SaveBirthday.this);
            mybuilder.setMessage("Are you Sure you want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from bdayInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(SaveBirthday.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            SaveBirthday.this.finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SaveBirthday.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveBirthday.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        return  true;
    }
}
