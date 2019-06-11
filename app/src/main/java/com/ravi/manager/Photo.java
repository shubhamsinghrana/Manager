package com.ravi.manager;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Photo extends AppCompatActivity {
ImageView myphoto;
String photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        myphoto=findViewById(R.id.myphoto);
        if(getIntent().getExtras()!=null)
        {photo=getIntent().getExtras().getString("photo");
        myphoto.setImageURI(Uri.parse(photo));

        }
    }
}
