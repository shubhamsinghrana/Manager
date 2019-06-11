package com.ravi.manager;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class WritingSection extends AppCompatActivity {
    Button saveBtn, updateBtn, deleteBtn;
    EditText titleBox, discriptionBox;
    String srno;// this will be send from the ListView and will be used to update ,delete and show the data of diary
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_section);
        setTitle("My Notes");
        saveBtn=findViewById(R.id.saveBtn);
        updateBtn=findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        titleBox=findViewById(R.id.titileBox);
        discriptionBox=findViewById(R.id.discriptionBox);
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            saveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            updateBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            updateBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }


        //#############################On Save Code###############################
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleBox.getText().toString().equals(""))        // validation in Android , title and description should not be empty
                {
                    titleBox.setError("Title cannot be blank");
                }
                else if(discriptionBox.getText().toString().equals(""))
                {
                    discriptionBox.setError("Description cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists notesInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "title varchar(100), description text, entrydate date)");

                            java.util.Date cdate = new java.util.Date();
                            SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                mydb.execSQL("insert into notesInfo(title, description, entrydate) values(?,?,?)",
                                        new Object[]{titleBox.getText().toString(), discriptionBox.getText().toString(),
                                                myformat.format(cdate)});
                            } catch (Exception e) {
                                Toast.makeText(WritingSection.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(WritingSection.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            WritingSection.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(WritingSection.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(WritingSection.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //#############################On Update Code###############################
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {
                        java.util.Date cdate=new java.util.Date();
                        SimpleDateFormat myformat=new SimpleDateFormat("yyyy-MM-dd");// we have put the date before because we also want to update the last wriiten date

                        mydb.execSQL("update notesInfo set title=?, description=?, entrydate=? " +
                                        "where srno=?",
                                new String[]{titleBox.getText().toString(),
                                        discriptionBox.getText().toString(),
                                        myformat.format(cdate), srno});


                        mydb.close();
                        Toast.makeText(WritingSection.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(WritingSection.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(WritingSection.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //#############################On Delete Code###############################
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mybuilder=new AlertDialog.Builder(WritingSection.this);
                mybuilder.setMessage("Are you Sure you want to delete?");
                mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                            try {

                                mydb.execSQL("delete from notesInfo where srno=?", new String[]{srno});
                                mydb.close();
                                Toast.makeText(WritingSection.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                WritingSection.this.finish();
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(WritingSection.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(WritingSection.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        });
    }

    void fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from notesInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    titleBox.setText(myresult.getString(myresult.getColumnIndex("title")));
                    discriptionBox.setText(myresult.getString(myresult.getColumnIndex("description")));
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

    //Sharing Code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Share");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1)
        {
            if(titleBox.getText().toString().equals(""))        // validation in Android , title and description should not be empty
            {
                titleBox.setError("Title cannot be blank");
            }
            else if(discriptionBox.getText().toString().equals(""))
            {
                discriptionBox.setError("Description cannot be blank");
            }
            else
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Title: "+titleBox.getText().toString()+"\n About the day: "+discriptionBox.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        }
        return  true;
    }
    }

