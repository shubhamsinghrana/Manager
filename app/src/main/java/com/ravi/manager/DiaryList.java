package com.ravi.manager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiaryList extends AppCompatActivity {
    ListView mylist;
    EditText searchBox;
    ArrayList<String> content, srno,date;
    Toolbar toolbar;
    Menu mymenu;
    myhelperclass obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        mylist=findViewById(R.id.myList);
        searchBox=findViewById(R.id.searchBox);
        searchBox.setVisibility(View.GONE);
        content=new ArrayList<>();
        srno=new ArrayList<>();
        date=new ArrayList<>();
//        userName.setText("Welcome Guest");
        setTitle("All Records");
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("All Records");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DiaryList.this, WritingSection.class));
            }
        });
    }
    protected void onResume() {
        fetchvalues();
         obj=new myhelperclass(this,android.R.layout.simple_expandable_list_item_1,content);
        mylist.setAdapter(obj);


        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(DiaryList.this, WritingSection.class)
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
                Cursor myresult = mydb.rawQuery("select * from notesInfo", null);
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
            Toast.makeText(DiaryList.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listviewsearchoptionslayout,mymenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId()==R.id.searchBtn)
       {
           searchBox.setVisibility(View.VISIBLE);
            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    DiaryList.this.obj.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
       }
        return true;
    }
}
