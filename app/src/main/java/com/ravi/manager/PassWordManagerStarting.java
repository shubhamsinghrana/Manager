package com.ravi.manager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PassWordManagerStarting extends AppCompatActivity {
    ListView myList;
    String actionBoxName[]={"Social Sites","Emails","Credit/Debit card","Personal Info","Private Images","Websites Password"};
    //int iconBox[]={R.drawable.nexticon};
    Integer actionBoxImages[]={R.drawable.socialsites,R.drawable.emailicon,R.drawable.bankcard,R.drawable.personalinfo,R.drawable.privateimages,R.drawable.bankcard};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word_manager_starting);
        myList=findViewById(R.id.myList);
        myhelper obj=new myhelper(this,android.R.layout.simple_list_item_1,actionBoxImages);
        myList.setAdapter(obj);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),ListOfSocialSites.class));
                }
                else if(i==1)
                {
                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();

                }
                else if(i==2)
                {

                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),ShowSavedCardsList.class));
                }
                else if(i==3)
                {

                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
                else if(i==4)
                {

                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }else if(i==5)
                {

                    Toast.makeText(PassWordManagerStarting.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }class myhelper extends ArrayAdapter<Integer>
    {


        public myhelper(@NonNull Context context, int resource, @NonNull Integer [] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View myrow=getLayoutInflater().inflate(R.layout.passwordmanagerstartinglayout,parent,false);
            ImageView actionBox,nextIcon;
            TextView actionName;

            actionBox=myrow.findViewById(R.id.actionBox);
            actionName=myrow.findViewById(R.id.actionName);
            nextIcon=myrow.findViewById(R.id.nextIcon);
            int a=position;
            actionBox.setImageResource(actionBoxImages[0]);
            actionName.setText(actionBoxName[position]);
            nextIcon.setImageResource(R.drawable.nexticon);

            return myrow;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Generate Password");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1)
        {
           // Toast.makeText(this, "Generate Password", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(),GeneratePassword.class));
        }

        return true;
    }
}
