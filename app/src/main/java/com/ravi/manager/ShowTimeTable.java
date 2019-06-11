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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowTimeTable extends AppCompatActivity {
TextView p1,p2,p3,p4,p5,z1,z2,z3,z4,z5;
ImageView seeBtn;
Spinner daySpinner;
ArrayList<String> myDay;
String choosenDay="Monday";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_table);
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

        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        p3=findViewById(R.id.p3);
        p4=findViewById(R.id.p4);
        p5=findViewById(R.id.p5);
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
                z3.setVisibility(View.VISIBLE);z4.setVisibility(View.VISIBLE);
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
               // Toast.makeText(ShowTimeTable.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        catch(Exception e)
        {
            //Toast.makeText(ShowTimeTable.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myaddmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add1)
        {
            startActivity(new Intent(getBaseContext(),AllDays.class));
        }

        return true;
    }
}
