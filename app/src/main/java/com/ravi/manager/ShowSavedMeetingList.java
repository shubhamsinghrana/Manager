package com.ravi.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowSavedMeetingList extends AppCompatActivity {
ListView myMeetingList45;
    ArrayList<String> content, srno,date,timer;
    myhelperclass obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_meeting_list);
        setTitle("Meetings");
        content=new ArrayList<String>();
        srno=new ArrayList<String>();
        date=new ArrayList<String>();
        timer=new ArrayList<String>();

        myMeetingList45=findViewById(R.id.myMeetingList1);
    }
    protected void onResume() {
        fetchvalues();
        obj=new myhelperclass(this,android.R.layout.simple_expandable_list_item_1,content);
        myMeetingList45.setAdapter(obj);
        myMeetingList45.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getBaseContext(), SaveMeeting.class)
                        .putExtra("serialno", srno.get(i)));

            }
        });
        super.onResume();
    }
    class myhelperclass extends ArrayAdapter<String>
    {

        public myhelperclass(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow=getLayoutInflater().inflate(R.layout.showemailcustomlistview,parent,false);
            TextView t1,t2;
            Typeface txtcustom=Typeface.createFromAsset(getAssets(),"myfont.ttf");

            t1=myrow.findViewById(R.id.showFirstLetter);
            t1.setTypeface(txtcustom);
            t2=myrow.findViewById(R.id.showEmailID);
            char example=content.get(position).toString().toUpperCase().charAt(0);
            String show=""+example;
            t1.setText(show);
            // Toast.makeText(ShowSavedEmailList.this, ""+example, Toast.LENGTH_SHORT).show();
            t2.setText("With: "+content.get(position)+" At: "+date.get(position)+" "+timer.get(position));
            // t2.setText(date.get(position));
            return myrow;

        }
    }
    void fetchvalues()// used to fetch value from database and enter into ListView
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                Cursor myresult = mydb.rawQuery("select * from meetingInfo", null);
                content.clear();
                srno.clear();
                //  date.clear();
                if(myresult.moveToNext())
                {
                    do {
                        content.add(myresult.getString(myresult.getColumnIndex("mname")));
                        date.add(myresult.getString(myresult.getColumnIndex("mdate")));

                        timer.add(myresult.getString(myresult.getColumnIndex("mtime")));
                        srno.add(myresult.getString(myresult.getColumnIndex("srno")));
                    }
                    while(myresult.moveToNext());

                }

                mydb.close();

            }
            catch(Exception e)
            {
               // Toast.makeText(ShowSavedContactList.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
           // Toast.makeText(ShowSavedContactList.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myaddmenu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add1)
        {
            startActivity (new Intent(getBaseContext(),SaveMeeting.class));

        }
        return true;
    }
}
