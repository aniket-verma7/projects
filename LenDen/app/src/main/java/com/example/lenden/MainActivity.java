package com.example.lenden;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lenden.Database.dataHelper;
import com.example.lenden.customAdapter.adapter;
import com.example.lenden.fragmentPackage.AccountFragment;
import com.example.lenden.fragmentPackage.DashBoardFragment;
import com.example.lenden.fragmentPackage.SettingFragment;
import com.example.lenden.userClass.Details;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private adapter custom;
    private BottomNavigationView bottom;
    private dataHelper data;
    private ArrayList<Details> details;
    boolean check=false;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom = findViewById(R.id.bottom_Nav);
        bottom.setBackgroundColor(Color.parseColor("#D2000000"));
        bottom.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        activity = new MainActivity();
        data = new dataHelper(this);
        details = data.getAllDetails();
        custom = new adapter(this,details);
        setTitle("Dashboard");
        final SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                Fragment fragment = null;
                switch (menuItem.getItemId())
                {
                    case R.id.account:
                        check=true;
                        setTitle("Accounts");
                        fragment = new AccountFragment();
                        break;

                    case R.id.dashboard:
                        check=false;
                        setTitle("Dashboard");
                        fragment = new DashBoardFragment(sharedPreferences.getString("NAME",""));
                        break;

                    case R.id.setting:
                        check=false;
                        setTitle("Setting");
                        fragment = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont,fragment).commit();
                return true;
            }
        });
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont,new DashBoardFragment(sharedPreferences.getString("NAME",""))).commit();
    }


    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        if(check)
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont,new AccountFragment()).commit();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }

}
