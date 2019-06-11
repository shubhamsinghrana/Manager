package com.ravi.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MyVaultGridLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vault_grid_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

     getMenuInflater().inflate(R.menu.myaddmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add1)
        {

        }

        return true;
    }
}
