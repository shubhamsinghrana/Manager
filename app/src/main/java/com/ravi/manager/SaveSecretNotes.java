package com.ravi.manager;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SaveSecretNotes extends AppCompatActivity {
EditText topicBox,contentBox;
ImageView cameraBtn, imageBox;
ImageView mysaveBtn,myupdateBtn;
String srno;

    ImageView dialogCameraBtn, dialogGalleryBtn;
    Uri imageUri=null;
    File imageFolder=null;
    Uri myuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_secret_notes);
        topicBox=findViewById(R.id.topicBox);
        contentBox=findViewById(R.id.contentBox);
        cameraBtn=findViewById(R.id.cameraBtn);
        imageBox=findViewById(R.id.imageBox);
        mysaveBtn=findViewById(R.id.mysaveBtn);
        myupdateBtn=findViewById(R.id.myupdateBtn);
        //mydeleteBtn=findViewById(R.id.mydeleteBtn);
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            mysaveBtn.setVisibility(View.GONE);   // because if something is coming from ListView then only we will show the update and delete btn
            //fetchvalues(srno);                     //calling featchvalues to get the previous title and dis
            myupdateBtn.setVisibility(View.VISIBLE);

            //mydeleteBtn.setVisibility(View.VISIBLE);
        }
        else {      //else the btn will be invisiblr
            myupdateBtn.setVisibility(View.GONE);
            imageBox.setOnKeyListener(null);
           // mydeleteBtn.setVisibility(View.GONE); // Initially the buttons will not be visible
        }

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mydialog=new Dialog(SaveSecretNotes.this);
                mydialog.setContentView(R.layout.mycameragallerydialog);
                mydialog.show();
                dialogCameraBtn=mydialog.findViewById(R.id.dialogCameraBtn);
                dialogGalleryBtn=mydialog.findViewById(R.id.dialogGalleryBtn);
                mydialog.setTitle("Choose From");

                dialogCameraBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydialog.dismiss();
                        if(checkandRequestPermission())
                        {
                            imageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Manager");
                            imageFolder.mkdirs();
                            if(imageFolder.exists())
                            {
                                String filename="img" + new java.util.Date().getTime() + ".jpg";
                                File myimage =new File(imageFolder,filename);

                                Intent myintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                                    String authorities = BuildConfig.APPLICATION_ID + ".provider";
                                    imageUri = FileProvider.getUriForFile(SaveSecretNotes.this, authorities, myimage);
                                    myintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                }
                                else
                                    {
                                         myimage=new File(imageFolder, new java.util.Date().getTime()+".jpg");
                                        imageUri=Uri.fromFile(myimage);

                                        myintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                            }
                               startActivityForResult(myintent, 1);
                            }
                        }
                    }
                });//camera from dialog

                dialogGalleryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydialog.dismiss();

                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, 2);
                    }
                });//gallery dialog


            }
        });//main camera btn
        if(getIntent().getExtras()!=null)
        {
            srno=getIntent().getExtras().getString("serialno");
            fetchvalues(srno);
            mysaveBtn.setVisibility(View.GONE);
            myupdateBtn.setVisibility(View.VISIBLE);
            //deletebtn.setVisibility(View.VISIBLE);

        }
        else {
            myupdateBtn.setVisibility(View.GONE);
            //deletebtn.setVisibility(View.GONE);
        }

        mysaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topicBox.getText().toString().equals(""))        // validation in Android , title and description should not be empty
                {
                    topicBox.setError("Title cannot be blank");
                }
                else if(contentBox.getText().toString().equals(""))
                {
                    contentBox.setError("Description cannot be blank");
                }
                else {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {
                            mydb.execSQL("create table if not exists secretnotesInfo(srno Integer PRIMARY KEY AUTOINCREMENT, " +
                                    "title varchar(100), description text, entrydate date,image varchar(200))");

                            java.util.Date cdate = new java.util.Date();
                            SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                mydb.execSQL("insert into secretnotesInfo(title, description, entrydate,image) values(?,?,?,?)",
                                        new Object[]{topicBox.getText().toString(), contentBox.getText().toString(),
                                                myformat.format(cdate),myuri.toString()});
                            } catch (Exception e) {
                                Toast.makeText(SaveSecretNotes.this, "Unable to insert in table" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            mydb.close();
                            Toast.makeText(SaveSecretNotes.this, "Saved Sucssefully!!", Toast.LENGTH_SHORT).show();
                            SaveSecretNotes.this.finish();


                        } catch (Exception e) {
                            Toast.makeText(SaveSecretNotes.this, "Unable to create table!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(SaveSecretNotes.this, "Unable to Create Database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        myupdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                    try {
                        java.util.Date cdate=new java.util.Date();
                        SimpleDateFormat myformat=new SimpleDateFormat("yyyy-MM-dd");

                        mydb.execSQL("update secretnotesInfo set title=?, description=?, entrydate=? " +
                                        "where srno=?",
                                new String[]{topicBox.getText().toString(),
                                        contentBox.getText().toString(),
                                        myformat.format(cdate), srno});


                        mydb.close();
                        Toast.makeText(SaveSecretNotes.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e)
                    {
                       // Toast.makeText(SaveSecretNotes.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    //Toast.makeText(SaveSecretNotes.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

imageBox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myintent=new Intent(getBaseContext(),Photo.class);
        myintent.putExtra("photo",imageUri.toString());
        startActivity(myintent);

    }
});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK) {
            myuri = imageUri;
            imageBox.setImageURI(myuri);
        }
        else if(requestCode==2 && resultCode==RESULT_OK)
        {
            Uri myuri2=data.getData();
            imageBox.setImageURI(myuri2);

            imageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Manager");
            if(!imageFolder.exists())
            {
                imageFolder.mkdirs();
            }
            if(imageFolder.exists())
            {
                String filename="img" + new java.util.Date().getTime() + ".jpg";
                File myimage =new File(imageFolder,filename);
                myuri = Uri.fromFile(new File(myimage.getPath()));
                try {
                    myimage.createNewFile();
                    copyFile(new File(getRealPathFromURI(data.getData())), myimage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }



    void fetchvalues(String id)
    {
        try {
            SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
            try {
                Cursor myresult = mydb.rawQuery("select * from secretnotesInfo where srno=?", new String[]{id});
                if(myresult.moveToNext())
                {

                    topicBox.setText(myresult.getString(myresult.getColumnIndex("title")));
                    contentBox.setText(myresult.getString(myresult.getColumnIndex("description")));
                    if(myresult.getString(myresult.getColumnIndex("image"))==null)
                    {
                        imageBox.setImageResource(R.drawable.contactsicon);
                    }
                    else {
                        imageBox.setImageURI(Uri.parse(myresult.getString(myresult.getColumnIndex("image"))));
                    }


                }

                mydb.close();

            }
            catch(Exception e)
            {
                Toast.makeText(getBaseContext(), "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(), "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    boolean checkandRequestPermission()
    {
        ArrayList<String> listPermissionsNeeded = new ArrayList<>();
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        if(requestCode==100) {

            boolean flag=false;
            if (grantResults.length > 0)
            {
                if(grantResults.length==1)
                {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        flag=true;
                    }
                }
                else if(grantResults.length==2)
                {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1]==PackageManager.PERMISSION_GRANTED)
                    {
                        flag=true;
                    }
                }

                if(flag) {
                    imageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Manager");
                    imageFolder.mkdirs();
                    if(imageFolder.exists())
                    {
                        String filename="img" + new java.util.Date().getTime() + ".jpg";
                        File myimage =new File(imageFolder,filename);

                        Intent myintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        imageUri = FileProvider.getUriForFile(SaveSecretNotes.this, authorities, myimage);

                        myintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(myintent, 1);
                    }
                }
                else
                {
                    Toast.makeText(this, "Please give all permissions", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Please give all permissions", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mydeletemenu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.delete)
        {
            //delete code
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(SaveSecretNotes.this);
            mybuilder.setMessage("Do you really want to delete?");
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        SQLiteDatabase mydb = openOrCreateDatabase("myManagerDataBase", MODE_PRIVATE, null);
                        try {

                            mydb.execSQL("delete from secretnotesInfo where srno=?", new String[]{srno});
                            mydb.close();
                            Toast.makeText(SaveSecretNotes.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            SaveSecretNotes.this.finish();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SaveSecretNotes.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(SaveSecretNotes.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
}
