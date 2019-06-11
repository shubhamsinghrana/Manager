package com.ravi.manager;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SaveCardSection extends AppCompatActivity {
    ImageView savebtn;
    EditText bankname1,c1,c2,c3,c4,title,time1,name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card_section);
        savebtn=findViewById(R.id.save1);
        bankname1= findViewById(R.id.bank1);
        setTitle("Add Card");
        c1= findViewById(R.id.cd1);
        c2= findViewById(R.id.cd2);
        c3= findViewById(R.id.cd3);
        c4= findViewById(R.id.cd4);
        time1= findViewById(R.id.time1);
        name1= findViewById(R.id.name1);
        title= findViewById(R.id.title);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {              // validation in android using set error function
                if (bankname1.getText().toString().equals("")) {
                    bankname1.setError("BANK NAME cannot be blank");
                } else if (name1.getText().toString().equals("")) {
                    name1.setError("NAME cannot be blank");
                }
                else if (time1.getText().toString().equals("")) {
                    time1.setError("VALID THRU cannot be blank");
                }
                else if (title.getText().toString().equals("")) {
                    title.setError("TITLE cannot be blank");
                }


                else {
                    try {
                        SQLiteDatabase mydatabase = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);//mode is fore visibility of database
                        try {
                            mydatabase.execSQL("create table if not exists bankPasswordsInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "bankname1 varchar(100), cd1 varchar,cd2 varchar,cd3 varchar,cd4 varchar, time1 varchar(100), name1 varchar, title varchar )");//we are telling content of tables (date is datatype )

                            mydatabase.execSQL("insert into bankPasswordsInfo(bankname1,cd1,cd2,cd3,cd4,time1,name1,title) values(?,?,?,?,?,?,?,?) ", new Object[]{bankname1.getText().toString(), c1.getText().toString(),c2.getText().toString(),c3.getText().toString(),c4.getText().toString(),time1.getText().toString(), name1.getText().toString(),title.getText().toString()});//inserting into database and describing whuch entries (because we use auto increment) after comma telling what we are sending
                            mydatabase.close();
                            Toast.makeText(SaveCardSection.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(SaveCardSection.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    } catch (Exception e) {
                        Toast.makeText(SaveCardSection.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


}

