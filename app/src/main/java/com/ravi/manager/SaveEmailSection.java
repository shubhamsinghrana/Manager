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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveEmailSection extends AppCompatActivity {
EditText emailIDBox, emailPasswordBox;
ImageView emailSaveBtn,emailUpdateBtn;
String srno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_email_section);
        emailIDBox=findViewById(R.id.emailIDBox);
        emailPasswordBox=findViewById(R.id.mybox);
        emailSaveBtn=findViewById(R.id.emailSaveBtn);
        emailUpdateBtn=findViewById(R.id.emailUpdateBtn);
        setTitle("Save EmailID's");
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            emailSaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            emailUpdateBtn.setVisibility(View.VISIBLE);
           // deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            emailUpdateBtn.setVisibility(View.GONE);
            //deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }
        emailSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(emailIDBox.getText().toString().equals(""))
                {
                    emailIDBox.setError("Cannot be blank");
                }
                else if(emailPasswordBox.getText().toString().equals(""))
                {
                    emailPasswordBox.setError("Cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists emailPasswordInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "email varchar(100), password text)");

                            try {
                                mydb.execSQL("insert into emailPasswordInfo(email, password) values(?,?)",
                                        new Object[]{emailIDBox.getText().toString(), emailPasswordBox.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SaveEmailSection.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SaveEmailSection.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SaveEmailSection.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SaveEmailSection.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SaveEmailSection.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        emailUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("update emailPasswordInfo set email=?, password=? " +
                                        "where srno=?",
                                new String[]{emailIDBox.getText().toString(),
                                        emailPasswordBox.getText().toString(), srno});


                        mydb.close();
                        Toast.makeText(SaveEmailSection.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveEmailSection.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SaveEmailSection.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    void fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from emailPasswordInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    emailIDBox.setText(myresult.getString(myresult.getColumnIndex("email")));
                    emailPasswordBox.setText(myresult.getString(myresult.getColumnIndex("password")));
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
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(SaveEmailSection.this);
            mybuilder.setMessage("Are you Sure you want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from emailPasswordInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(SaveEmailSection.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            SaveEmailSection.this.finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SaveEmailSection.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveEmailSection.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
