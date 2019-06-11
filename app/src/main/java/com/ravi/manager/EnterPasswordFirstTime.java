package com.ravi.manager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterPasswordFirstTime extends AppCompatActivity {
    TextView titleBox;
    EditText pwdBox,confirmBox;
    Button createBtn;
    String passwordEntered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password_first_time);
        titleBox=findViewById(R.id.titleBox);
        pwdBox=findViewById(R.id.mybox);
        confirmBox=findViewById(R.id.confirmBox);
        createBtn=findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwdBox.getText().toString().length()<6)
                {
                    pwdBox.setError("Master Password's Length Must Be Greater Than six charcters");
                    if(!(pwdBox.getText().toString().equals(confirmBox.getText().toString())))
                    {
                        confirmBox.setError("Password Doesn't Match!!!");

                    }
                }
                else
                {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists masterPwdInfo(srno Integer PRIMARY KEY AUTOINCREMENT , " +
                                    "password varchar(100))");

                            try {
                                mydb.execSQL("insert into masterPwdInfo(password) values(?)",
                                        new Object[]{pwdBox.getText().toString()});
                            } catch (Exception e) {
                                Toast.makeText(EnterPasswordFirstTime.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(EnterPasswordFirstTime.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(EnterPasswordFirstTime.this, "new activity started!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(),FrontOptionsScreen.class));

                            EnterPasswordFirstTime.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(EnterPasswordFirstTime.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(EnterPasswordFirstTime.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
