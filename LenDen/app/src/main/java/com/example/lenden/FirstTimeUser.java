package com.example.lenden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FirstTimeUser extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText edtF,edtL;
    private boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_user);

        edtF = findViewById(R.id.edtF);
        edtL = findViewById(R.id.edtL);

        sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);

            findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String name = edtF.getText().toString() + " " + edtL.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("NAME", name);
                    editor.putBoolean("check", true);
                    editor.commit();

                    if(edtF.getText().toString().equals("") || edtL.getText().toString().equals(""))
                    {
                        Toast.makeText(FirstTimeUser.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(FirstTimeUser.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }
}
