package com.ravi.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllDays extends AppCompatActivity {
Button monBtn,tueBtn,wedBtn,thrBtn,friBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_days);
        monBtn=findViewById(R.id.monBtn);
        tueBtn=findViewById(R.id.tueBtn);
        wedBtn=findViewById(R.id.wedBtn);
        thrBtn=findViewById(R.id.thrBtn);
        friBtn=findViewById(R.id.friBtn);

        monBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowingTimeTable.class));
            }
        });

        tueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),TuesdayTimeTable.class));
            }
        });

        wedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),WednesdayTimeTable.class));
            }
        });

        thrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),ThursdayTimeTable.class));
            }
        });

        friBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),FridayTimeTable.class));
                }
        });
    }
}
