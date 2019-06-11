package com.ravi.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SocialSitesPasswordEntry extends AppCompatActivity {
TextView siteNameBox;
EditText usernameBox, passwordBox;
ImageView saveBtn, updateBtn;
String typeofsite;
ImageView socialSiteIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_sites_password_entry);
        siteNameBox=findViewById(R.id.siteNameBox);
        usernameBox=findViewById(R.id.usernameBox);
        passwordBox=findViewById(R.id.mybox);
        saveBtn=findViewById(R.id.saveBtn);
        updateBtn=findViewById(R.id.updateBtn);
        socialSiteIcon=findViewById(R.id.socialSiteIcon);
        if(getIntent().getExtras()!=null)
        {
            typeofsite=getIntent().getExtras().getString("socialsiteName");
               // because if something is coming from ListView then only we will show the update and delete btn
            if(fetchvalues(typeofsite))
            {
                updateBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
            }//calling featchvalues to get the previous title and dis
            else
            {
                saveBtn.setVisibility(View.VISIBLE);   // because if something is coming from ListView then only we will show the update and delete btn
                updateBtn.setVisibility(View.GONE);
            }
          //  updateBtn.setVisibility(View.VISIBLE);
            siteNameBox.setText(typeofsite);


            if(typeofsite.equalsIgnoreCase("Facebook"))
            {
                socialSiteIcon.setImageResource(R.drawable.fb);

            }
            else if(typeofsite.equalsIgnoreCase("Instagram"))
            {
                socialSiteIcon.setImageResource(R.drawable.instagram);

            }
            else if(typeofsite.equalsIgnoreCase("Twitter"))
            {
                socialSiteIcon.setImageResource(R.drawable.twitter);

            }
            else if(typeofsite.equalsIgnoreCase("Hike"))
            {
                socialSiteIcon.setImageResource(R.drawable.hike);

            }
            else if(typeofsite.equalsIgnoreCase("Snapchat"))
            {
                socialSiteIcon.setImageResource(R.drawable.snapchat);

            }
            else if(typeofsite.equalsIgnoreCase("Tinder"))
            {
                socialSiteIcon.setImageResource(R.drawable.tinder);

            }
            else if(typeofsite.equalsIgnoreCase("Pinterest"))
            {
                socialSiteIcon.setImageResource(R.drawable.pinterest);

            }
            else if(typeofsite.equalsIgnoreCase("Linkdin"))
            {
                socialSiteIcon.setImageResource(R.drawable.linkdin);

            }


        }

        //#############################On Save Code###############################
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameBox.getText().toString().equals(""))        // validation in Android , title and description should not be empty
                {
                    usernameBox.setError("UserName cannot be blank");
                }
                else if(passwordBox.getText().toString().equals(""))
                {
                    passwordBox.setError("Password cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists socialPasswordsInfo(typeofsocialsite varchar(100) PRIMARY KEY , " +
                                    "username varchar(100), password text)");

                            try {
                                mydb.execSQL("insert into socialPasswordsInfo(typeofsocialsite, username, password) values(?,?,?)",
                                        new Object[]{typeofsite, usernameBox.getText().toString(),
                                                passwordBox.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SocialSitesPasswordEntry.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SocialSitesPasswordEntry.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SocialSitesPasswordEntry.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SocialSitesPasswordEntry.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SocialSitesPasswordEntry.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
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

                        mydb.execSQL("update socialPasswordsInfo set  username=?, password=? " +
                                        "where typeofsocialsite=?",
                                new String[]{
                                        usernameBox.getText().toString(),
                                        passwordBox.getText().toString(), typeofsite});


                        mydb.close();
                        Toast.makeText(SocialSitesPasswordEntry.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SocialSitesPasswordEntry.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SocialSitesPasswordEntry.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean fetchvalues(String id)//This function is used to show the selected item from the ListView and will display the tiltle in tiltlebox and discription in dbox
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try
            {
                Cursor myresult=mydb.rawQuery("select * from socialPasswordsInfo where typeofsocialsite=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    usernameBox.setText(myresult.getString(myresult.getColumnIndex("username")));
                    passwordBox.setText(myresult.getString(myresult.getColumnIndex("password")));
                    return true;
                }mydb.close();



            }
            catch (Exception e)
            {
               // Toast.makeText(getBaseContext(), "Error in Fetching from Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(getBaseContext(), "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;

    }
}
