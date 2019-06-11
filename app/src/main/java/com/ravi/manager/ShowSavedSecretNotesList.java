package com.ravi.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ShowSavedSecretNotesList extends AppCompatActivity {
ListView mySecretList;
    ArrayList<String> content, srno,date;
    myhelperclass obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_secret_notes_list);
        mySecretList=findViewById(R.id.mySecretList);
        content=new ArrayList<>();
        srno=new ArrayList<>();
        date=new ArrayList<>();
        setTitle("All Notes");

    }

    protected void onResume() {
        fetchvalues();
        obj=new myhelperclass(this,android.R.layout.simple_expandable_list_item_1,content);
        mySecretList.setAdapter(obj);


        mySecretList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ShowSavedSecretNotesList.this, SaveSecretNotes.class)
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
            View myrow=getLayoutInflater().inflate(R.layout.customdiarylistview,parent,false);
            TextView t1,t2;
            t1=myrow.findViewById(R.id.diaryListLayoutTitleBox);
            t2=myrow.findViewById(R.id.diaryListLayoutDateBox);
            t1.setText(content.get(position));
            t2.setText(date.get(position));
            return myrow;

        }
    }
    void fetchvalues()// used to fetch value from database and enter into ListView
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                Cursor myresult = mydb.rawQuery("select * from secretnotesInfo", null);
                content.clear();
                srno.clear();
                date.clear();
                if(myresult.moveToNext())
                {
                    do {
                        content.add(myresult.getString(myresult.getColumnIndex("title")));
                        date.add(myresult.getString(myresult.getColumnIndex("entrydate")));
                        srno.add(myresult.getString(myresult.getColumnIndex("srno")));
                    }
                    while(myresult.moveToNext());

                }

                mydb.close();

            }
            catch(Exception e)
            {
                //Toast.makeText(DiaryList.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(ShowSavedSecretNotesList.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
           startActivity(new Intent(getBaseContext(),SaveSecretNotes.class));
       }

        return true;
    }
}
