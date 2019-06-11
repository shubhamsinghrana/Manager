package com.ravi.manager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfSocialSites extends AppCompatActivity {
ListView mySocialList;
    String socialBoxName[]={"Facebook","Instagram","Twitter","Hike","Snapchat","Tinder","Pinterest","Linkdin"};
    //int iconBox[]={R.drawable.nexticon};
    Integer socialBoxImages[]={R.drawable.fb,R.drawable.instagram,R.drawable.twitter,R.drawable.hike,R.drawable.snapchat,R.drawable.tinder,R.drawable.pinterest,R.drawable.linkdin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_social_sites);
        mySocialList=findViewById(R.id.mySocialList);
        setTitle("Social Sites");
        myhelper obj=new myhelper(this,android.R.layout.simple_list_item_1,socialBoxImages);
        mySocialList.setAdapter(obj);
        mySocialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                   // Toast.makeText(ListOfSocialSites.this, "FaceBook", Toast.LENGTH_SHORT).show();
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Facebook");
                    startActivity(myintent);
                }
                else if(i==1)
                {
                   // Toast.makeText(ListOfSocialSites.this, "Instagram", Toast.LENGTH_SHORT).show();
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Instagram");
                    startActivity(myintent);

                }
                else if(i==2)
                {
                    //Toast.makeText(ListOfSocialSites.this, "Twitter", Toast.LENGTH_SHORT).show();
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Twitter");
                    startActivity(myintent);

                }
                else if(i==3)
                {
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Hike");
                    startActivity(myintent);
                    //Toast.makeText(ListOfSocialSites.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
                else if(i==4)
                {
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Snapchat");
                    startActivity(myintent);
                   // Toast.makeText(ListOfSocialSites.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }else if(i==5)
                {
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Tinder");
                    startActivity(myintent);
                   // Toast.makeText(ListOfSocialSites.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
                else if(i==6)
                {
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Pinterest");
                    startActivity(myintent);
                    //Toast.makeText(ListOfSocialSites.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }

                else if(i==7)
                {
                    Intent myintent=new Intent(getBaseContext(),SocialSitesPasswordEntry.class);
                    myintent.putExtra("socialsiteName","Linkdin");
                    startActivity(myintent);
                    //Toast.makeText(ListOfSocialSites.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class myhelper extends ArrayAdapter<Integer>
    {


        public myhelper(@NonNull Context context, int resource, @NonNull Integer [] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow=getLayoutInflater().inflate(R.layout.socialsitenameslistlayout,parent,false);
            ImageView socialIconBox,nextSocialIcon;
            TextView socialNameBox;

            socialNameBox=myrow.findViewById(R.id.socialNameBox);
            socialIconBox=myrow.findViewById(R.id.socialIconBox);
            nextSocialIcon=myrow.findViewById(R.id.nextSocialIcon);
            int a=position;
            socialIconBox.setImageResource(socialBoxImages[position]);
            socialNameBox.setText(socialBoxName[position]);
            nextSocialIcon.setImageResource(R.drawable.nexticon);

            return myrow;
        }
    }
}
