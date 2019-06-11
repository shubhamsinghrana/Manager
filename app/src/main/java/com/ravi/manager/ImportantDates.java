package com.ravi.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImportantDates extends AppCompatActivity {
ImageView bdayImage, MeetingImage,myNoteImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_dates);
        bdayImage=findViewById(R.id.bdayImage);
        MeetingImage=findViewById(R.id.meetingImage);
        setTitle("Important Dates/Notes");

        myNoteImage=findViewById(R.id.myNoteImage);
        bdayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowBirthdayList.class));
            }
        });
        MeetingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedMeetingList.class));
            }
        });
        myNoteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ShowSavedSecretNotesList.class));
            }
        });
    }
}
