package com.example.lenden;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenden.Database.dataHelper;
import com.example.lenden.ReadContact.GetAllContact;
import com.example.lenden.userClass.Details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addNew extends AppCompatActivity
{
    FloatingActionButton fab;
    private EditText edtName,edtAmount,edtContact,edtDate;
    private dataHelper data;
    private Details details;
    private Intent intent;
    private RadioGroup radioGroup;
    private RadioButton rbCredit,rbDebit;
    private Button btnAdd;
    private String name;
    private TextView txtName,txtDate,txtMob,txtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        intent = getIntent();
        int x = intent.getIntExtra("fab",-1);

        fab = findViewById(R.id.fabEdit);

        edtName = findViewById(R.id.edtName);
        edtAmount = findViewById(R.id.edtAmount);
        edtContact = findViewById(R.id.edtContact);
        edtDate = findViewById(R.id.edtDate);

        txtName = findViewById(R.id.txtName);
        txtAmount = findViewById(R.id.txtAmount);
        txtDate = findViewById(R.id.txtDate);
        txtMob = findViewById(R.id.txtMob);

        btnAdd = findViewById(R.id.btnAdd);

        radioGroup = findViewById(R.id.radioGroup);
        rbCredit = findViewById(R.id.rbCredit);
        rbDebit = findViewById(R.id.rbDebit);


        txtName.setVisibility(View.GONE);
        txtDate.setVisibility(View.GONE);
        txtMob.setVisibility(View.GONE);
        txtAmount.setVisibility(View.GONE);

        data = new dataHelper(this);
        details = new Details();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(x == 1)// NON-UPDATION
        {
            fab.show();

            edtName.setEnabled(false);
            edtAmount.setEnabled(false);
            edtContact.setEnabled(false);
            edtDate.setEnabled(false);
            rbCredit.setEnabled(false);
            rbDebit.setEnabled(false);

            txtName.setVisibility(View.VISIBLE);
            txtDate.setVisibility(View.VISIBLE);
            txtMob.setVisibility(View.VISIBLE);
            txtAmount.setVisibility(View.VISIBLE);

            btnAdd.setVisibility(View.GONE);

            edtName.setText(intent.getStringExtra("name"));
            edtAmount.setText(intent.getStringExtra("amount"));
            edtDate.setText(intent.getStringExtra("date"));
            edtContact.setText(intent.getStringExtra("mob"));

            if(intent.getStringExtra("type").equals("Credit"))
            {
                rbCredit.setChecked(true);
            }
            else
            {
                rbDebit.setChecked(true);
            }
            //UPDATION.............................................
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();

                    edtName.setEnabled(true);
                    edtAmount.setEnabled(true);
                    edtContact.setEnabled(true);
                    rbCredit.setEnabled(true);
                    rbDebit.setEnabled(true);

                    btnAdd.setVisibility(View.VISIBLE);
                    btnAdd.setText("Save");

                    edtContact.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v)
                        {
                            Intent inCon = new Intent(addNew.this,GetAllContact.class);
                            startActivityForResult(inCon,200);
                        }
                    });

                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                switch (checkedId) {
                                    case R.id.rbCredit:
                                        details.setType(rbCredit.getText().toString());
                                        break;

                                    case R.id.rbDebit:
                                        details.setType(rbDebit.getText().toString());
                                        break;
                                }
                            }
                        });
                    if(rbCredit.isChecked())
                    {
                        details.setType(rbCredit.getText().toString());
                    }
                    if(rbDebit.isChecked())
                    {
                        details.setType(rbDebit.getText().toString());
                    }

                    btnAdd.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            name = edtName.getText().toString();

                            details.setName(name);
                            details.setAmount(edtAmount.getText().toString());
                            details.setMob(edtContact.getText().toString());
                            details.setLocalDate(getDate());
                            details.setId(intent.getIntExtra("id",-1));

                            if (data.updateData(details))
                            {
                                    Toast.makeText(addNew.this, "updated", Toast.LENGTH_SHORT).show();
                                    finish();
                            }
                             else
                                Toast.makeText(addNew.this, "Enter User Name", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }
        else                          //INSERTION...............
         {
            fab.hide();

             edtDate.setVisibility(View.GONE);
             edtContact.setEnabled(true);
             rbCredit.setChecked(true);
             radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup group, int checkedId)
                 {

                     switch (checkedId)
                     {
                         case R.id.rbCredit:

                             details.setType(rbCredit.getText().toString());
                             break;

                         case R.id.rbDebit:

                             details.setType(rbDebit.getText().toString());
                             break;
                     }

                 }
             });
                if(rbCredit.isChecked())
                {
                    details.setType(rbCredit.getText().toString());
                }

             edtContact.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View v)
                 {
                        Intent inCon = new Intent(addNew.this,GetAllContact.class);
                        startActivityForResult(inCon,200);
                 }
             });
            btnAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    name = edtName.getText().toString();

                    details.setName(name);
                    details.setAmount(edtAmount.getText().toString());
                    details.setMob(edtContact.getText().toString());

                    details.setLocalDate(getDate());

                    if (edtContact.getText().toString().equals("")) {
                        details.setMob("");
                    }
                    if (name.equals("") || edtAmount.equals(""))
                    {
                        Toast.makeText(addNew.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                    } else {
                        if (data.insertData(details)) {
                            //Toast.makeText(addNew.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else
                            Toast.makeText(addNew.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
        {
            edtContact.setText("Mobile No.");
        }
        else
        {
                if (requestCode == 200)
                edtContact.setText(data.getStringExtra("mob"));
        }
    }

    private String getDate()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        return simpleDateFormat.format(date);
    }
}
