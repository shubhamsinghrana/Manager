package com.ravi.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class loginmypassword extends AppCompatActivity {
    ImageView c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmypassword);
        setTitle("My Passwords");

        c1=findViewById(R.id.i11);
        c2=findViewById(R.id.i22);
        c3=findViewById(R.id.i33);
        c4=findViewById(R.id.i44);
        c5=findViewById(R.id.i55);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedCardsList.class));

            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ListOfSocialSites.class));

            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedWebsiteList.class));//ShowSavedWebsiteList

            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedEmailList.class));//ShowSavedEmailList

            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedContactList.class));//ContactList

            }
        });

    }
}
