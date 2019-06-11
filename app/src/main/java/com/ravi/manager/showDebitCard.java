package com.ravi.manager;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class showDebitCard extends AppCompatActivity {
    String srno;
    TextView bname,cd1,cd2,cd3,cd4,time,name;
    ImageView imageView,imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_debit_card);
       // imageView=findViewById(R.id.imageView);
     //   imageView2=findViewById(R.id.imageView2);
       bname=findViewById(R.id.bname);
        cd1=findViewById(R.id.cd1);
        cd2=findViewById(R.id.cd2);
        cd3=findViewById(R.id.cd3);
        cd4=findViewById(R.id.cd4);
        setTitle("Your Card");
        time=findViewById(R.id.time12);
       name=findViewById(R.id.name);
        if(getIntent().getExtras()!=null)// getting value from back (list file) if it is giving something  then hide save and visible update and delete button
        {
            srno=getIntent().getExtras().getString("serialno"); //storing information in srno String variable
            fetchvalues(srno);
        }
       // Toast.makeText(this, srno, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "DELETE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(showDebitCard.this);// conformation form  to give warning one more time
            mybuilder.setMessage("Do you really want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from bankPasswordsInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(showDebitCard.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            showDebitCard.this.finish();// since we are moving here from intent of back class to go back we do not need differnt intent simple finish function call will clase this activity as it is called by intent
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(showDebitCard.this, "Error in deleting Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(showDebitCard.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog mydialog=mybuilder.create();
            mydialog.show();


        }

        return true;
    }

    void fetchvalues(String id)// fetching information
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                Cursor myresult = mydb.rawQuery("select * from bankPasswordsInfo where srno=?", new String[]{id});// after query arguments are sent in form  of string array
                if(myresult.moveToNext())
                {

                    //Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
                    bname.setText(myresult.getString(myresult.getColumnIndex("bankname1")));
                    cd1.setText(myresult.getString(myresult.getColumnIndex("cd1")));
                    cd2.setText(myresult.getString(myresult.getColumnIndex("cd2")));
                    cd3.setText(myresult.getString(myresult.getColumnIndex("cd3")));
                    cd4.setText(myresult.getString(myresult.getColumnIndex("cd4")));
                    time.setText(myresult.getString(myresult.getColumnIndex("time1")));
                    name.setText(myresult.getString(myresult.getColumnIndex("name1")));

                }

                mydb.close();

            }
            catch(Exception e)
            {
               // Toast.makeText(getBaseContext(), "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(), "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
