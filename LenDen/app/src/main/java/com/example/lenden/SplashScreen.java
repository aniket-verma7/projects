package com.example.lenden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private  boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
        check = sharedPreferences.getBoolean("check",false);

        if(ActivityCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.READ_CONTACTS) ==
                PermissionChecker.PERMISSION_GRANTED)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (check) {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, FirstTimeUser.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 1500);

        }
        else
        {
            ActivityCompat.requestPermissions(SplashScreen.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED)
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (check) {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, FirstTimeUser.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 1500);

        }
        else
        {
            Toast.makeText(this,
                    "We won't be able to read contacts until you grant the permission",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
