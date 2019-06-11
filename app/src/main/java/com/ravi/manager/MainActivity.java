package com.ravi.manager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> name;
    String name1[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name= new ArrayList<>();
       /* fetchvalues();



       GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,name1));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //  Toast.makeText(MainActivity.this, "" + position,
                //        Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"add");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==1)
        {

            startActivity(new Intent(MainActivity.this, Main2Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        fetchvalues();



        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,name1));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //  Toast.makeText(MainActivity.this, "" + position,
                //        Toast.LENGTH_SHORT).show();
                if(name==null)
                {
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
                Intent intent=new Intent(getBaseContext(),Main2Activity.class);
                intent.putExtra("key",name1[position]);
                startActivity(intent);
               // Toast.makeText(MainActivity.this, name1[position], Toast.LENGTH_SHORT).show();
            }
        });




        super.onResume();
    }

    void fetchvalues()
    {
        name.clear();
        SQLiteDatabase mydb = openOrCreateDatabase("vault12", MODE_PRIVATE, null);
        try {
            Cursor myresult = mydb.rawQuery("select * from v12345", null);// rawQuery is used to fetch query from table
            if (myresult.moveToNext()==true)
            {
                int i=0;

                do {

                    name.add((myresult.getString(myresult.getColumnIndex("title"))));

                }
                while (myresult.moveToNext());



                name1  = new String[name.size()];
                name1 =   name.toArray(name1);
                //Toast.makeText(this, Uri.parse(name1[0]).toString(), Toast.LENGTH_SHORT).show();
            }


            mydb.close();

        } catch (Exception e) {
            //Toast.makeText(MainActivity.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(),Main2Activity.class));
        }
    }


}

