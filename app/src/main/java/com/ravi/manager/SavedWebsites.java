package com.ravi.manager;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SavedWebsites extends AppCompatActivity {
EditText websiteBox,websiteUserBox,websitePasswordBox;
ImageView websiteSaveBtn,websiteUpdateBtn;
String srno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_websites);
        websiteBox=findViewById(R.id.websiteBox);
        websiteUserBox=findViewById(R.id.websiteuserBox);
        websitePasswordBox=findViewById(R.id.websitePasswordBox);
        websiteSaveBtn=findViewById(R.id.websiteSaveBtn);
        websiteUpdateBtn=findViewById(R.id.websiteUpdateBtn);
        setTitle("Save Websites");
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            websiteSaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            websiteUpdateBtn.setVisibility(View.VISIBLE);
            // deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            websiteUpdateBtn.setVisibility(View.GONE);
            //deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }
        websiteSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(websiteBox.getText().toString().equals(""))
                {
                    websiteBox.setError("Cannot be blank");
                }
                else if(websiteUserBox.getText().toString().equals(""))
                {
                    websiteUserBox.setError("Cannot be blank");
                }
                else if(websitePasswordBox.getText().toString().equals(""))
                {
                    websitePasswordBox.setError("Cannot be blank");
                }

                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists websiteInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "website varchar(100), user text,password varchar(100))");

                            try {
                                mydb.execSQL("insert into websiteInfo(website, user, password) values(?,?,?)",
                                        new Object[]{websiteBox.getText().toString(), websiteUserBox.getText().toString(),websitePasswordBox.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SavedWebsites.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SavedWebsites.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SavedWebsites.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SavedWebsites.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SavedWebsites.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        websiteUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("update websiteInfo set website=?, user=?, password=? " +
                                        "where srno=?",
                                new String[]{websiteBox.getText().toString(),
                                        websiteUserBox.getText().toString(),websitePasswordBox.getText().toString(), srno});


                        mydb.close();
                        Toast.makeText(SavedWebsites.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SavedWebsites.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SavedWebsites.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    void fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from websiteInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    websiteBox.setText(myresult.getString(myresult.getColumnIndex("website")));
                    websiteUserBox.setText(myresult.getString(myresult.getColumnIndex("user")));
                    websitePasswordBox.setText(myresult.getString(myresult.getColumnIndex("password")));
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
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(SavedWebsites.this);
            mybuilder.setMessage("Are you Sure you want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from websiteInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(SavedWebsites.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            SavedWebsites.this.finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SavedWebsites.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SavedWebsites.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        return true;
    }
}
