package com.ravi.manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends AppCompatActivity {
ImageView i1;

    String DBPATH="/data/data/myapps.gtbapps.myfirstapp/databases/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       // i1=findViewById(R.id.spalshimageBox);



      //  mydbhelper obj=new mydbhelper(this);
        try
        {
        //    obj.copyDatabase();
        }
        catch(Exception e)
        {
            Toast.makeText(SplashScreen.this, "Error in Database Copying", Toast.LENGTH_SHORT).show();
        }


        SharedPreferences mysettings= PreferenceManager.getDefaultSharedPreferences(this);
        boolean splash=mysettings.getBoolean("splashChoice",true);
        if(splash==false)
        {
            startActivity(new Intent(getBaseContext(),FrontOptionsScreen.class));
        }
        else
        {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   // AnimationDrawable myanimation= (AnimationDrawable) i1.getDrawable();
                    //myanimation.start();
                    startActivity(new Intent(getBaseContext(),FrontOptionsScreen.class));
                    //myanimation.stop();
                    SplashScreen.this.finish();
                }
            },2000);
        }
    }
    void copyDatabase() throws IOException
    {
       // InputStream myinput=mycontext.getAssets().open(DBNAME);
        //String outfile=DBPATH + DBNAME;

       // OutputStream myoutput=new FileOutputStream(outfile);
        int a;
        byte[] buffer=new byte[1024];
        //while((a=myinput.read(buffer))>0)
        {
           // myoutput.write(buffer,0,a);
        }
       // myoutput.close();
       // myinput.close();


    }

}
