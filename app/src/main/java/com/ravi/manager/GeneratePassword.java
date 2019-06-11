package com.ravi.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;

public class GeneratePassword extends AppCompatActivity {
TextView textBox, passwordBox;
EditText showPassword;
ImageView generateBtn;
Spinner myspinner;
ArrayList mylist=null;
int length;
String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
String ALPHA = "abcdefghijklmnopqrstuvwxyz";
String NUMERIC = "0123456789";
String SPECIAL_CHARS = "!@#$%^&*_=,+-/";
int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        setTitle("Generate Password");
        mylist=new ArrayList<String>();
        //textBox=findViewById(R.id.textBox);
        passwordBox=findViewById(R.id.mybox);
        showPassword=findViewById(R.id.showPassword);
        passwordBox.setVisibility(View.GONE);
        showPassword.setVisibility(View.GONE);
        generateBtn=findViewById(R.id.generateBtn);
        myspinner=findViewById(R.id.mySpinner);
        mylist.add("Length of Password....");
        mylist.add("6"); mylist.add("8"); mylist.add(10);
        mylist.add("12");
        myspinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mylist));
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    length=6;
                    a=1;
                }
                else if(i==2)
                {
                    length=8;
                    a=1;
                }
                else if(i==3)
                {
                    length=10;
                    a=1;
                }else if(i==4)
                {
                    length=12;
                    a=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(GeneratePassword.this, "Please Choose the length first...", Toast.LENGTH_SHORT).show();
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(a==0)
                 {
                     Toast.makeText(GeneratePassword.this, "Please Choose a length of passsword!!!", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     String result = generatePassword(length, ALPHA_CAPS + ALPHA + SPECIAL_CHARS + NUMERIC);
                     passwordBox.setVisibility(View.VISIBLE);
                     showPassword.setVisibility(View.VISIBLE);
                     showPassword.setText(result);
                     Toast.makeText(GeneratePassword.this, "Password Generated!!!!", Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }
    public  String generatePassword(int len, String dic) {
        String result = "";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }
}
