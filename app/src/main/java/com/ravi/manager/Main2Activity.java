package com.ravi.manager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Main2Activity extends AppCompatActivity {
    ImageView img;
    ImageView save,delete;
    String imagePath;
    Uri imageUri=null;
    File imageFolder=null;
    String srno;
    Uri myuri;
    String filename;
    String image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        img=findViewById(R.id.img);
        save=findViewById(R.id.sav);
        delete=findViewById(R.id.del);
        delete.setVisibility(View.GONE);
        if(getIntent().getExtras()!=null)
        {
           image1=getIntent().getExtras().getString("key");
           save.setVisibility(View.GONE);
           delete.setVisibility(View.VISIBLE);
            img.setImageURI(Uri.parse(image1));
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 2);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver contentResolver = getContentResolver();
                contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{ image1 });
              //  Toast.makeText(Main2Activity.this, "Image Deleted!!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),MainActivity.class));

                try {
                    SQLiteDatabase mydatabase = openOrCreateDatabase("vault12", MODE_PRIVATE, null);//mode is fore visibility of database
                    try {
                        mydatabase.execSQL("create table if not exists v12345(srno Integer PRIMARY KEY AUTOINCREMENT,title varchar(100) )");

                        mydatabase.execSQL("delete from v12345 where title=?", new Object[]{image1.toString()});//inserting into database and describing whuch entries (because we use auto increment) after comma telling what we are sending
                        mydatabase.close();

                        Toast.makeText(Main2Activity.this, "Image Deleted!!!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Main2Activity.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Toast.makeText(Main2Activity.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagePath!=null) {

                    // Toast.makeText(Main2Activity.this, filename, Toast.LENGTH_SHORT).show();
                    try {
                        SQLiteDatabase mydatabase = openOrCreateDatabase("vault12", MODE_PRIVATE, null);//mode is fore visibility of database
                        try {
                            mydatabase.execSQL("create table if not exists v12345(srno Integer PRIMARY KEY AUTOINCREMENT,title varchar(100) )");

                            mydatabase.execSQL("insert into v12345(title) values(?) ", new Object[]{myuri.toString()});//inserting into database and describing whuch entries (because we use auto increment) after comma telling what we are sending
                            mydatabase.close();

                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    MediaStore.Images.ImageColumns.DATA + "=?", new String[]{imagePath});
                            Toast.makeText(Main2Activity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                        } catch (Exception e) {
                            Toast.makeText(Main2Activity.this, "Error in Creating Table due to " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Main2Activity.this, "Error in Creating Database due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2&&resultCode==RESULT_OK)
        {

            Uri myuri2=data.getData();
            img.setImageURI(myuri2);

            //imageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "GTB");
            imageFolder = new File(Environment.getExternalStorageDirectory(), "GTB12");
            if(!imageFolder.exists())
            {
                imageFolder.mkdirs();

            }
            if(imageFolder.exists())
            {
                // Toast.makeText(this, "inside if", Toast.LENGTH_SHORT).show();
                filename="img" + new java.util.Date().getTime() + ".jpg";
                File myimage =new File(imageFolder,filename);
                Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                myuri = Uri.fromFile(new File(myimage.getPath()));
                try {
                    myimage.createNewFile();
                    imagePath=getRealPathFromURI(data.getData());
                    copyFile(new File(getRealPathFromURI(data.getData())), myimage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"gallery");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1)
        {
           // startActivity(new Intent(Main2Activity.this, MainActivity.class));
            Main2Activity.this.finish();
        }
        return super.onOptionsItemSelected(item);
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

}
