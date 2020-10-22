package com.personal.whatsappchatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private ArrayList<Integer> lst;
    private RecyclerView list;
    private NumberAdapter arrayAdapter;
    private int temp;
    private static String emoji="";
    private Database database;
    private int totalNumber = 90;
    private TextView noLeft;
    private boolean check = false;
    private Intent whatsappIntent;
    private Button btnCancel,btnSearch;
    private EditText searchDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));

        noLeft = findViewById(R.id.noLeft);

        lst = new ArrayList<>();

        noLeft.setText(totalNumber+"");

        database = new Database(this);
        lst = database.getAllNumber();

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (lst.size() == 90)
                {
                    startActivity(new Intent(MainActivity.this, Finish.class));
                    finish();
                }
                else
                    {
                    if (totalNumber >= 0)
                        totalNumber = totalNumber - 1;


                    if (totalNumber == -1)
                    {
                        totalNumber = 0;
                        noLeft.setText("0");
                    }
                    else
                    {
                        noLeft.setText(totalNumber + "");
                    }

                    temp = Integer.parseInt(tambolaNumber());

                    getNumberEmoji(temp);

                    if (!lst.contains(temp))
                    {
                        database.insertNumber(temp);
                        check = true;
                        lst.add(0, temp);

                        arrayAdapter = new NumberAdapter(MainActivity.this,lst);
                        list.setAdapter(arrayAdapter);

                        whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, emoji + "");

                        try {
                            startActivity(whatsappIntent);
                            emoji = "";
                            whatsappIntent=null;
                        } catch (android.content.ActivityNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        if (totalNumber >= 0)
                        {
                            emoji="";
                            whatsappIntent = null;
                            totalNumber += 1;
                            onClick(v);
                        }

                    }
                    if (totalNumber == 0) {
                        startActivity(new Intent(MainActivity.this, Finish.class));
                        finish();
                    }


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.clear,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.clear:
            if (database.deleteNumber()) {
                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
                lst.clear();
                arrayAdapter.notifyDataSetChanged();
                totalNumber = 90;
                noLeft.setText(totalNumber + "");
            }
            break;

            case R.id.total:
                Toast.makeText(this, lst.size()+"", Toast.LENGTH_SHORT).show();
                break;

            case R.id.searchNumber:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btnSearch = dialog.findViewById(R.id.btnSearch);
                btnCancel = dialog.findViewById(R.id.btnCancel);
                searchDialog = dialog.findViewById(R.id.searchDialog);

                btnSearch.setBackground(new ColorDrawable(Color.TRANSPARENT));
                btnCancel.setBackground(new ColorDrawable(Color.TRANSPARENT));

                btnCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                btnSearch.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(!searchDialog.getText().toString().equals(""))
                        {
                            Log.e("STATUS","in");
                            int raw = Integer.parseInt(searchDialog.getText().toString());
                            if(lst.contains(raw))
                            {
                                Toast.makeText(MainActivity.this, "Number is present", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Number is not present",Toast.LENGTH_LONG).show();
                            }
                        }

                        if(searchDialog.getText().equals(""))
                        {
                            Toast.makeText(MainActivity.this, "Please input valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                dialog.show();
        }
        return true;
    }

    private String tambolaNumber()
    {
        Random r = new Random();
        return (r.nextInt(91 - 1)+1)+"";
    }

    private String getEmojiByUnicode(int unicode)
    {
        return new String(Character.toChars(unicode));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        emoji="";

        if(lst!=null)
        {

            arrayAdapter = new NumberAdapter(MainActivity.this,lst);
            list.setAdapter(arrayAdapter);

            totalNumber = 90-lst.size();
            noLeft.setText(totalNumber+"");
        }


            if (lst.size() == 90)
            {
                totalNumber = 0;
                noLeft.setText(totalNumber + "");
            }


    }

    private void getNumberEmoji(int num)
    {
        int a[] = new int[2];
        int temp1=num%10;
        a[1]=temp1;
        num=num/10;
        a[0]=num;
        getEmoji(a[0]);
        getEmoji(a[1]);
    }

    private void getEmoji(int singleDigit)
    {
        switch (singleDigit)
        {
            case 0:
                if(emoji.equals(""))
                emoji=getString(R.string.zero);
                else emoji = emoji +getString(R.string.zero);
                break;

            case 1:
                if(emoji.equals(""))
                    emoji=getString(R.string.one);
                else emoji = emoji +getString(R.string.one);
                break;
            case 2:
                if(emoji.equals(""))
                    emoji=getString(R.string.two);
                else emoji = emoji +getString(R.string.two);
                break;
            case 3:
                if(emoji.equals(""))
                    emoji=getString(R.string.three);
                else emoji = emoji +getString(R.string.three);
                break;
            case 4:
                if(emoji.equals(""))
                    emoji=getString(R.string.four);
                else emoji = emoji +getString(R.string.four);
                break;
            case 5:
                if(emoji.equals(""))
                emoji=getString(R.string.five);
                else emoji = emoji +getString(R.string.five);
                break;
            case 6:
                if(emoji.equals(""))
                    emoji=getString(R.string.six);
                else emoji = emoji +getString(R.string.six);
                break;
            case 7:
                if(emoji.equals(""))
                    emoji=getString(R.string.seven);
                else emoji = emoji +getString(R.string.seven);
                break;
            case 8:
                if(emoji.equals(""))
                    emoji=getString(R.string.eight);
                else emoji = emoji +getString(R.string.eight);
                break;
            case 9:
                if(emoji.equals(""))
                    emoji=getString(R.string.nine);
                else emoji = emoji +getString(R.string.nine);
                break;


        }
    }
}
