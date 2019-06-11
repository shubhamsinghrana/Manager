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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowSavedCardsList extends AppCompatActivity {
    ListView mylist;
    ArrayList<String> content, srno,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_cards_list);
        mylist=findViewById(R.id.showMyCardsList);
        content=new ArrayList<>();
        srno=new ArrayList<>();
        type=new ArrayList<>();
        setTitle("Saved Cards");
    }
    @Override
    protected void onResume() {
        fetchvalues();
        mylist.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                content));
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(type.get(i).equals("atm")) {
                    startActivity(new Intent(ShowSavedCardsList.this, showDebitCard.class)
                            .putExtra("serialno", srno.get(i)));

                }
                //  else if(type.get(i).equals("fb")) {
                //    startActivity(new Intent(MainActivity.this, Main7Activity.class)
             /*               .putExtra("serialno", srno.get(i)));

                }
                else if(type.get(i).equals("web")) {
                    startActivity(new Intent(MainActivity.this, Main5Activity.class)
                            .putExtra("serialno", srno.get(i)));

                }*/
            }
        });
        super.onResume();
    }

    void fetchvalues()  // we are creating a function to fetch values (this can be done inside on create also)
    { content.clear();
        srno.clear();
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                Cursor myresult = mydb.rawQuery("select * from bankPasswordsInfo", null);// rawQuery is used to fetch query from table

                if(myresult.moveToNext())  // this tell us weather myresult is moved ahed or not -- since it tell us after moving we use do while loop
                {
                    do {
                        type.add("atm");//to fetch differnt title we use loop
                        content.add(myresult.getString(myresult.getColumnIndex("title")));
                        srno.add(myresult.getString(myresult.getColumnIndex("srno")));
                    }
                    while(myresult.moveToNext());

                }

                mydb.close();

            }
            catch(Exception e)
            {
               // Toast.makeText(ShowSavedCardsList.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(ShowSavedCardsList.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Add new card");
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1)
        {
            startActivity(new Intent(ShowSavedCardsList.this, SaveCardSection.class));

        }return  true;
    }
}
