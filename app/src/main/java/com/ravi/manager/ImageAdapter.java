package com.ravi.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.ravi.manager.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    String t[];

    public ImageAdapter(Context c, String[] b) {
        mContext = c;
        t=b;
    }

    public int getCount() {
        if(t==null)
        {return 1;}
        return t.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        if (t == null) {
            imageView.setImageResource(R.drawable.addicon);
        } else
            {
            //  Toast.makeText(mContext, t[position], Toast.LENGTH_SHORT).show();
            imageView.setImageURI(Uri.parse(t[position]));
                }//imageView.setImageResource(mThumbIds[position]);

        return imageView;
        }


    // references to our images
    private Integer[] mThumbIds = {

    };
}