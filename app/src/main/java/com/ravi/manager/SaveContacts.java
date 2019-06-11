package com.ravi.manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

public class SaveContacts extends AppCompatActivity {
    EditText contactBox, contactnoBox;
    ImageView contactSaveBtn,contactUpdateBtn;
    String srno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contacts);
        contactBox=findViewById(R.id.contactBox);
        contactnoBox=findViewById(R.id.contactnoBox);
        contactSaveBtn=findViewById(R.id.contactSaveBtn);
        contactUpdateBtn=findViewById(R.id.contactUpdateBtn);
        setTitle("Save Contacts");
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            contactSaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            contactUpdateBtn.setVisibility(View.VISIBLE);
            // deleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            contactUpdateBtn.setVisibility(View.GONE);
            //deleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }

        contactSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactBox.getText().toString().equals(""))
                {
                    contactBox.setError("Cannot be blank");
                }
                else if(contactnoBox.getText().toString().equals(""))
                {
                    contactnoBox.setError("Cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists contactInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "name varchar(100), mobile text)");

                            try {
                                mydb.execSQL("insert into contactInfo(name, mobile) values(?,?)",
                                        new Object[]{contactBox.getText().toString(), contactnoBox.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(SaveContacts.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SaveContacts.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SaveContacts.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SaveContacts.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SaveContacts.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        contactUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {

                        mydb.execSQL("update contactInfo set name=?, mobile=? " +
                                        "where srno=?",
                                new String[]{contactBox.getText().toString(),
                                        contactnoBox.getText().toString(), srno});


                        mydb.close();
                        Toast.makeText(SaveContacts.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveContacts.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SaveContacts.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Cursor myresult=mydb.rawQuery("select * from contactInfo where srno=?",new String[]{id});
                if(myresult.moveToNext())
                {
                    contactBox.setText(myresult.getString(myresult.getColumnIndex("name")));
                    contactnoBox.setText(myresult.getString(myresult.getColumnIndex("mobile")));
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
        getMenuInflater().inflate(R.menu.mycallmessagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String contactno=contactnoBox.getText().toString();
        if(contactno==null)
        {
            contactnoBox.setError("No number selected!!!!");
        }
        else
            {
            if (item.getItemId() == R.id.call1)
            {
                    Intent callintent=new Intent(Intent.ACTION_DIAL);
                    callintent.setData(Uri.parse("tel:"+contactno));
                    startActivity(callintent);

            } else if (item.getItemId() == R.id.mess1)
            {
                Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+contactno));
                intent.putExtra("Sms_body","Hello How are u?");
                startActivity(intent);

            }
            else if(item.getItemId()==R.id.delete1)
            {
                AlertDialog.Builder mybuilder=new AlertDialog.Builder(SaveContacts.this);
                mybuilder.setMessage("Are you Sure you want to delete?");
                mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                            try {

                                mydb.execSQL("delete from contactInfo where srno=?", new String[]{srno});
                                mydb.close();
                                Toast.makeText(SaveContacts.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                SaveContacts.this.finish();
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(SaveContacts.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SaveContacts.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        }
        return true;
    }
}

