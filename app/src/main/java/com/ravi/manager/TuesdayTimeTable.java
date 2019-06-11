package com.ravi.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TuesdayTimeTable extends AppCompatActivity {
    Spinner daySpinner,spinner1,spinner2,spinner3,spinner4,spinner5;
    ArrayList<String> subjects;
    ArrayList<String> days;
    ImageView mondaySaveBtn,updateSaveBtn;
    String period1,period2,period3,period4,period5;
    int a=0,b=0,c=0,d=0,e=0;
    int q1=0,q2=0,q3=0,q4=0,q5=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday_time_table);
        subjects=new ArrayList<String>();
//daySpinner=findViewById(R.id.daySpinner);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        spinner3=findViewById(R.id.spinner3);
        setTitle("Tuesday");
        spinner4=findViewById(R.id.spinner4);
        spinner5=findViewById(R.id.spinner5);
        mondaySaveBtn=findViewById(R.id.mondaySaveBtn);
        updateSaveBtn=findViewById(R.id.updateSaveBtn);

        updateSaveBtn.setVisibility(View.INVISIBLE);
        //  daySpinner=findViewById(R.id.daySpinner);
        if(fetchvalues("Tuesday"))
        {
            mondaySaveBtn.setVisibility(View.GONE);
            updateSaveBtn.setVisibility(View.VISIBLE);
        }

        days=new ArrayList<String>();
        subjects.add("Choose Subject");
        subjects.add("MATHS");
        subjects.add("SCIENCE");
        subjects.add("ENGLISH");
        subjects.add("DRAWING");
        subjects.add("Free");

       /* days.add("Choose Day");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursdsay");
        days.add("Friday");*/
        spinner1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjects));

        spinner2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjects));

        spinner3.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjects));

        spinner4.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjects));

        spinner5.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjects));
        spinner1.setSelection(q1);
//        Toast.makeText(this, String.valueOf(q1), Toast.LENGTH_SHORT).show();
        spinner2.setSelection(q2);
        spinner3.setSelection(q3);
        spinner4.setSelection(q4);
        //  Toast.makeText(this, String.valueOf(q4), Toast.LENGTH_SHORT).show();
        spinner5.setSelection(q5);

        // daySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(ShowingTimeTable.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                if(i==1)
                {
                    period1="Maths";
                    // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show();
                    a=1;
                }
                else if(i==2)
                {
                    period1="Science";
                    a=1;
                }
                else if(i==3)
                {
                    period1="English";
                    a=1;
                }
                else if(i==4)
                {
                    period1="Drawing";
                    a=1;
                }
                else if(i==5)
                {
                    period1="Free";
                    a=1;
                }
                else if(i==0)
                {

                    a=0;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    period2="Maths";
                    b=1;
                }
                else if(i==2)
                {
                    period2="Science";
                    b=1;
                }
                else if(i==3)
                {
                    period2="English";
                    b=1;
                }
                else if(i==4)
                {
                    period2="Drawing";
                    b=1;
                }
                else if(i==5)
                {
                    period2="Free";
                    b=1;
                }else if(i==0)
                {

                    b=0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    period3="Maths";
                    c=1;
                }
                else if(i==2)
                {
                    period3="Science";
                    c=1;
                }
                else if(i==3)
                {
                    period3="English";
                    c=1;
                }
                else if(i==4)
                {
                    period3="Drawing";
                    c=1;
                }
                else if(i==5)
                {
                    period3="Free";
                    c=1;
                }else if(i==0)
                {

                    c=0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    period4="Maths";
                    d=1;
                }
                else if(i==2)
                {
                    period4="Science";
                    d=1;
                }
                else if(i==3)
                {
                    period4="English";
                    d=1;
                }
                else if(i==4)
                {
                    period4="Drawing";
                    d=1;
                }
                else if(i==5)
                {
                    period4="Free";
                    d=1;
                }
                else if(i==0)
                {

                    d=0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    period5="Maths";
                    e=1;
                }
                else if(i==2)
                {
                    period5="Science";
                    e=1;
                }
                else if(i==3)
                {
                    period5="English";
                    e=1;
                }
                else if(i==4)
                {
                    period5="Drawing";
                    e=1;
                }
                else if(i==5)
                {
                    period5="Free";
                    e=1;
                }
                else if(i==0)
                {

                    e=0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mondaySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(name1.getText().toString().equals(""))
                {
                    name1.setError("Cannot be blank");
                }
                else if(birthdate.getText().toString().equals(""))
                {
                    birthdate.setError("Cannot be blank");
                }*/
                if(a==1&&b==1&&c==1&&d==1&&e==1) {
                    mondaySaveBtn.setVisibility(View.VISIBLE);


                    try {


                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists timetable1(day varchar(100),period1 varchar(100),period2 varchar(100),period3 varchar(100),period4 varchar(100),period5 varchar(100))");

                            try {
                                Toast.makeText(TuesdayTimeTable.this, period1, Toast.LENGTH_SHORT).show();
                                mydb.execSQL("insert into timetable1(day,period1,period2,period3,period4,period5) values(?,?,?,?,?,?)",
                                        new Object[]{"Tuesday", period1, period2, period3, period4, period5});
                            } catch (Exception e) {
                                Toast.makeText(TuesdayTimeTable.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(TuesdayTimeTable.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            TuesdayTimeTable.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(TuesdayTimeTable.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(TuesdayTimeTable.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(TuesdayTimeTable.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                }
            }

        });
        updateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == 1 && b == 1 && c == 1 && d == 1 && e == 1)
                {
                    // mondaySaveBtn.setVisibility(View.VISIBLE);


                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {
                        mydb.execSQL("update timetable1 set period1=?, period2=?, period3=?,period4=?, period5=?" +
                                "where day=?",new String[]{period1, period2, period3, period4, period5,"Tuesday"});


                        mydb.close();
                        Toast.makeText(TuesdayTimeTable.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(TuesdayTimeTable.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                    Toast.makeText(TuesdayTimeTable.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    boolean fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from timetable1 where day=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    // titleBox.setText(myresult.getString(myresult.getColumnIndex("title")));
                    //discriptionBox.setText(myresult.getString(myresult.getColumnIndex("description")));
                    q1=setter(myresult.getString(myresult.getColumnIndex("period1")));
                    q2=setter(myresult.getString(myresult.getColumnIndex("period2")));
                    // Toast.makeText(this, myresult.getString(myresult.getColumnIndex("period2")), Toast.LENGTH_SHORT).show();
                    q3=setter(myresult.getString(myresult.getColumnIndex("period3")));
                    q4=setter(myresult.getString(myresult.getColumnIndex("period4")));
                    q5=setter(myresult.getString(myresult.getColumnIndex("period5")));
                    return true;
                }
                mydb.close();

            }
            catch (Exception e)
            {
               // Toast.makeText(getBaseContext(), "Error in Fetching from Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(getBaseContext(), "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    public int setter(String p)
    {

        int q1=0;

        if(p.equalsIgnoreCase("maths"))
        {
            q1=1;
            return q1;
        }
        else if(p.equalsIgnoreCase("science"))
        {
            q1=2;
            return q1;
        }
        else if(p.equalsIgnoreCase("english"))
        {
            q1=3;
            return q1;
        }
        else if(p.equalsIgnoreCase("drawing"))
        {
            q1=4;
            return q1;
        }
        else if(p.equalsIgnoreCase("free"))
        {
            q1=5;
            return q1;
        }
        return 0;
    }
}
