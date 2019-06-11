package com.ravi.manager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAttendanceFirst extends AppCompatActivity {
    TextView p1,p2,p3,p4,p5,z1,z2,z3,z4,z5;
    ImageView seeBtn;
    Spinner daySpinner;
    ArrayList<String> myDay;
    String choosenDay="Monday";
    Button pp1,pp2 ,pp3,pp4,pp5,pa1,pa2,pa3,pa4,pa5;
    String subject1,subject2,subject3,subject4,subject5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance_first);
        setTitle("Check Timetable");
        p1=findViewById(R.id.p1);
        z1=findViewById(R.id.z12);
        z2=findViewById(R.id.z22);
        z3=findViewById(R.id.z32);
        z4=findViewById(R.id.z42);
        z5=findViewById(R.id.z52);

        z1.setVisibility(View.INVISIBLE);
        z2.setVisibility(View.INVISIBLE);
        z3.setVisibility(View.INVISIBLE);
        z4.setVisibility(View.INVISIBLE);
        z5.setVisibility(View.INVISIBLE);



        p2=findViewById(R.id.p2);

        p3=findViewById(R.id.p3);

        p4=findViewById(R.id.p4);

        p5=findViewById(R.id.p5);

        pp1=findViewById(R.id.pp1);
        pp2=findViewById(R.id.pp2);
        pp3=findViewById(R.id.pp3);
        pp4=findViewById(R.id.pp4);
        pp5=findViewById(R.id.pp5);
        pa1=findViewById(R.id.pa1);
        pa2=findViewById(R.id.pa2);
        pa3=findViewById(R.id.pa3);
        pa4=findViewById(R.id.pa4);
        pa5=findViewById(R.id.pa5);
        pp1.setVisibility(View.GONE);   pp2.setVisibility(View.GONE);   pp3.setVisibility(View.GONE); pp4.setVisibility(View.GONE); pp5.setVisibility(View.GONE);
        pa1.setVisibility(View.GONE); pa2.setVisibility(View.GONE); pa3.setVisibility(View.GONE); pa4.setVisibility(View.GONE); pa5.setVisibility(View.GONE);
        daySpinner=findViewById(R.id.daySpinner);
        seeBtn=findViewById(R.id.seeBtn);
        myDay=new ArrayList<String>();
        myDay.add("Monday");
        myDay.add("Tuesday");
        myDay.add("Wednesday");
        myDay.add("Thursday");
        myDay.add("Friday");
        //fetchvalues("Monday");

        daySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myDay));
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    choosenDay="Monday";
                    // Toast.makeText(ShowTimeTable.this, choosenDay, Toast.LENGTH_SHORT).show();
                }
                else if(i==1)
                {
                    choosenDay="Tuesday";
                    // Toast.makeText(ShowTimeTable.this, choosenDay, Toast.LENGTH_SHORT).show();

                }
                else if(i==2)
                {
                    choosenDay="Wednesday";
                    // Toast.makeText(ShowTimeTable.this, choosenDay, Toast.LENGTH_SHORT).show();

                }
                else if(i==3)
                {
                    choosenDay="Thursday";
                    //Toast.makeText(ShowTimeTable.this, choosenDay, Toast.LENGTH_SHORT).show();

                }
                else if(i==4)
                {
                    choosenDay="Friday";
                    // Toast.makeText(ShowTimeTable.this, choosenDay, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                fetchvalues("Monday");
            }
        });

        seeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z1.setVisibility(View.VISIBLE);
                z2.setVisibility(View.VISIBLE);
                z3.setVisibility(View.VISIBLE);
                z4.setVisibility(View.VISIBLE);
                z5.setVisibility(View.VISIBLE);


                fetchvalues(choosenDay);
            }
        });


    }
    void fetchvalues(String id)// used to fetch value from database and enter into ListView
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                Cursor myresult = mydb.rawQuery("select period1,period2,period3,period4,period5 from timetable1 where day=?", new String[]{id});
                //content.clear();
                //srno.clear();
                //  date.clear();
                if(myresult.moveToNext())
                {
                    //Toast.makeText(this, "inside if", Toast.LENGTH_SHORT).show();
                    //  do {
                        /*content.add(myresult.getString(myresult.getColumnIndex("name")));
                        // date.add(myresult.getString(myresult.getColumnIndex("entrydate")));
                        srno.add(myresult.getString(myresult.getColumnIndex("srno")));*/


                    p1.setText((myresult.getString(myresult.getColumnIndex("period1"))).toString());
                    subject1=(myresult.getString(myresult.getColumnIndex("period1"))).toString();
                    pp1.setVisibility(View.VISIBLE); pa1.setVisibility(View.VISIBLE);
                    if(subject1.equalsIgnoreCase("free"))
                    {
                        pp1.setVisibility(View.INVISIBLE);
                        pa1.setVisibility(View.INVISIBLE);
                    }
                    pp1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pp1.setVisibility(View.INVISIBLE);
                            pa1.setVisibility(View.INVISIBLE);
                            present(subject1);
                        }
                    });
                   pa1.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           absent( subject1);


                           pp1.setVisibility(View.INVISIBLE);
                           pa1.setVisibility(View.INVISIBLE);
                       }
                   });

                   //############################
                    p2.setText((myresult.getString(myresult.getColumnIndex("period2"))).toString());
                    subject2=(myresult.getString(myresult.getColumnIndex("period2"))).toString();
                    pp2.setVisibility(View.VISIBLE); pa2.setVisibility(View.VISIBLE);
                    if(subject2.equalsIgnoreCase("free"))
                    {
                        pp2.setVisibility(View.INVISIBLE);
                        pa2.setVisibility(View.INVISIBLE);
                    }
                    pp2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show(
                            present(subject2);

                            pp2.setVisibility(View.INVISIBLE);
                            pa2.setVisibility(View.INVISIBLE);
                        }
                    });
                    pa2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            absent( subject2);

                            pp2.setVisibility(View.INVISIBLE);
                            pa2.setVisibility(View.INVISIBLE);

                        }
                    });



                    //############################
                    p3.setText((myresult.getString(myresult.getColumnIndex("period3"))).toString());
                    subject3=(myresult.getString(myresult.getColumnIndex("period3"))).toString();

                    pp3.setVisibility(View.VISIBLE); pa3.setVisibility(View.VISIBLE);
                    if(subject3.equalsIgnoreCase("free"))
                    {
                        pp3.setVisibility(View.INVISIBLE);
                        pa3.setVisibility(View.INVISIBLE);
                    }
                    pp3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show(
                            present(subject3);

                            pp3.setVisibility(View.INVISIBLE);
                            pa3.setVisibility(View.INVISIBLE);
                        }
                    });
                    pa3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            absent( subject3);

                            pp3.setVisibility(View.INVISIBLE);
                            pa3.setVisibility(View.INVISIBLE);

                        }
                    });


                    //############################
                    p4.setText((myresult.getString(myresult.getColumnIndex("period4"))).toString());
                    subject4=(myresult.getString(myresult.getColumnIndex("period4"))).toString();

                    pp4.setVisibility(View.VISIBLE); pa4.setVisibility(View.VISIBLE);
                    if(subject4.equalsIgnoreCase("free"))
                    {
                        pp4.setVisibility(View.INVISIBLE);
                        pa4.setVisibility(View.INVISIBLE);
                    }
                    pp4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show(
                            present(subject4);

                            pp4.setVisibility(View.INVISIBLE);
                            pa4.setVisibility(View.INVISIBLE);
                        }
                    });
                    pa4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            absent( subject4);

                            pp4.setVisibility(View.INVISIBLE);
                            pa4.setVisibility(View.INVISIBLE);

                        }
                    });


                    //############################
                    p5.setText((myresult.getString(myresult.getColumnIndex("period5"))).toString());
                    subject5=(myresult.getString(myresult.getColumnIndex("period5"))).toString();

                    pp5.setVisibility(View.VISIBLE); pa5.setVisibility(View.VISIBLE);
                    if(subject5.equalsIgnoreCase("free"))
                    {
                        pp5.setVisibility(View.INVISIBLE);
                        pa5.setVisibility(View.INVISIBLE);
                    }
                    pp5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show(
                            present(subject5);

                            pp5.setVisibility(View.INVISIBLE);
                            pa5.setVisibility(View.INVISIBLE);
                        }
                    });
                    pa5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            absent( subject5);

                            pp5.setVisibility(View.INVISIBLE);
                            pa5.setVisibility(View.INVISIBLE);

                        }
                    });






                    p2.setText((myresult.getString(myresult.getColumnIndex("period2"))).toString());
                    p3.setText((myresult.getString(myresult.getColumnIndex("period3"))).toString());
                    p4.setText((myresult.getString(myresult.getColumnIndex("period4"))).toString());
                    p5.setText((myresult.getString(myresult.getColumnIndex("period5"))).toString());
                    // }
                    // while(myresult.moveToNext());

                }


                mydb.close();

            }
            catch(Exception e)
            {
               // Toast.makeText(MyAttendanceFirst.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            //Toast.makeText(MyAttendanceFirst.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.mysubjectsmenu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.maths1)
        {
            startActivity(new Intent(getBaseContext(),CheckMyAttendance.class).putExtra("subname","Maths"));
        }
       else if(item.getItemId()==R.id.sci1)
        {
            startActivity(new Intent(getBaseContext(),CheckMyAttendance.class).putExtra("subname","Science"));
        }
       else if(item.getItemId()==R.id.eng1)
        {
            startActivity(new Intent(getBaseContext(),CheckMyAttendance.class).putExtra("subname","English"));
        }
      else  if(item.getItemId()==R.id.draw1)
        {
            startActivity(new Intent(getBaseContext(),CheckMyAttendance.class).putExtra("subname","Drawing"));
        }

        return true;
    }
   public void present(String subject1)
    {
        if(subject1.equalsIgnoreCase("maths"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists mathstable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into mathstable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{1, 1});
                    Toast.makeText(this, "Present in Maths", Toast.LENGTH_SHORT).show();
                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }

        else if(subject1.equalsIgnoreCase("science"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists sciencetable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into sciencetable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{1, 1});
                    Toast.makeText(this, "Present in Science", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }


        else if(subject1.equalsIgnoreCase("english"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists englishtable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into englishtable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{1, 1});
                    Toast.makeText(this, "Present in English", Toast.LENGTH_SHORT).show();


                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }
        else if(subject1.equalsIgnoreCase("drawing"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists drawingtable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into drawingtable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{1, 1});
                    Toast.makeText(this, "Present in Drawing", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }
    }
    public void absent(String subject1)
    {
        if(subject1.equalsIgnoreCase("maths"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists mathstable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into mathstable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{0, 1});
                    Toast.makeText(this, "Absent in Maths", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }

        else if(subject1.equalsIgnoreCase("science"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists sciencetable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into sciencetable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{0, 1});
                    Toast.makeText(this, "Absent in Science", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }


        else if(subject1.equalsIgnoreCase("english"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists englishtable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into englishtable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{0, 1});
                    Toast.makeText(this, "Absent in English", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }
        else if(subject1.equalsIgnoreCase("drawing"))
        {
            try
            {
                SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                mydb.execSQL("create table if not exists drawingtable(myattendance Integer, mytotalattendance Integer)");



                try {
                    //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                    mydb.execSQL("insert into drawingtable(myattendance,mytotalattendance) values(?,?)",
                            new Object[]{0, 1});
                    Toast.makeText(this, "Absent in Drawing", Toast.LENGTH_SHORT).show();

                    //content.clear();
                } catch (Exception e) {
                }
            }
            catch(Exception e)
            {
                // Toast.makeText(ShowingTimeTable.this, period1, Toast.LENGTH_SHORT).show()

            }

        }
    }
}
