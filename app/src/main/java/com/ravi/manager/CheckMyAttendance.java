package com.ravi.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CheckMyAttendance extends AppCompatActivity {
String subjectName;
TextView subjectName1,myattshow,mytotalattshow;
int a=0,b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_attendance);
        if(getIntent().getExtras()!=null)
        {subjectName=getIntent().getExtras().getString("subname");

        }
        subjectName1=findViewById(R.id.subjectName);

        setTitle("Check Attendance");
        subjectName1.setText(subjectName);
        myattshow=findViewById(R.id.myattshow);
        mytotalattshow=findViewById(R.id.mytotalattshow);
        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);


        if(subjectName.equalsIgnoreCase("Maths"))
        {   try {
            Cursor myresult = mydb.rawQuery("select * from mathstable", null);// rawQuery is used to fetch query from table

            if(myresult.moveToNext())  // this tell us weather myresult is moved ahed or not -- since it tell us after moving we use do while loop
            {
                do {
                    a=a+(myresult.getInt(myresult.getColumnIndex("myattendance")));
                    b=b+(myresult.getInt(myresult.getColumnIndex("mytotalattendance")));
                }
                while(myresult.moveToNext());
                myattshow.setText(String.valueOf(a));
                mytotalattshow.setText(String.valueOf(b));
            }

            mydb.close();

        }
        catch(Exception e)
        {
            // Toast.makeText(CheckMyAttendance.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }

      else  if(subjectName.equalsIgnoreCase("English"))
        {   try {
            Cursor myresult = mydb.rawQuery("select * from englishtable", null);// rawQuery is used to fetch query from table

            if(myresult.moveToNext())  // this tell us weather myresult is moved ahed or not -- since it tell us after moving we use do while loop
            {
                do {
                    a=a+(myresult.getInt(myresult.getColumnIndex("myattendance")));
                    b=b+(myresult.getInt(myresult.getColumnIndex("mytotalattendance")));
                }
                while(myresult.moveToNext());
                myattshow.setText(String.valueOf(a));
                mytotalattshow.setText(String.valueOf(b));
            }

            mydb.close();

        }
        catch(Exception e)
        {
            //Toast.makeText(CheckMyAttendance.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }
        else  if(subjectName.equalsIgnoreCase("Science"))
        {   try {
            Cursor myresult = mydb.rawQuery("select * from sciencetable", null);// rawQuery is used to fetch query from table

            if(myresult.moveToNext())  // this tell us weather myresult is moved ahed or not -- since it tell us after moving we use do while loop
            {
                do {
                    a=a+(myresult.getInt(myresult.getColumnIndex("myattendance")));
                    b=b+(myresult.getInt(myresult.getColumnIndex("mytotalattendance")));
                }
                while(myresult.moveToNext());
                myattshow.setText(String.valueOf(a));
                mytotalattshow.setText(String.valueOf(b));
            }

            mydb.close();

        }
        catch(Exception e)
        {
           // Toast.makeText(CheckMyAttendance.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }

        else  if(subjectName.equalsIgnoreCase("Drawing"))
        {   try {
            Cursor myresult = mydb.rawQuery("select * from drawingtable", null);// rawQuery is used to fetch query from table

            if(myresult.moveToNext())  // this tell us weather myresult is moved ahed or not -- since it tell us after moving we use do while loop
            {
                do {
                    a=a+(myresult.getInt(myresult.getColumnIndex("myattendance")));
                    b=b+(myresult.getInt(myresult.getColumnIndex("mytotalattendance")));
                }
                while(myresult.moveToNext());
                myattshow.setText(String.valueOf(a));
                mytotalattshow.setText(String.valueOf(b));
            }

            mydb.close();

        }
        catch(Exception e)
        {
          //  Toast.makeText(CheckMyAttendance.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }



    }
}
