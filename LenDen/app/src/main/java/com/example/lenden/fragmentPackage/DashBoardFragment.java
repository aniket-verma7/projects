package com.example.lenden.fragmentPackage;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lenden.Database.dataHelper;
import com.example.lenden.R;

public class DashBoardFragment extends Fragment
{
    private EditText edtName,edtIn,edtOut,edtNet;
    private dataHelper data;
    private SharedPreferences sharedPreferences;
    private String name;
    private TextView txtNet;

    public DashBoardFragment(String name)
    {
        this.name = name;
    }

    public DashBoardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.frag_dash,container,false);
        edtName = v.findViewById(R.id.edtName);
        edtIn = v.findViewById(R.id.edtIn);
        edtOut = v.findViewById(R.id.edtOut);
        edtNet = v.findViewById(R.id.edtNet);
        txtNet = v.findViewById(R.id.txtNet);

        edtName.setEnabled(false);
        edtIn.setEnabled(false);
        edtOut.setEnabled(false);
        edtNet.setEnabled(false);


        data = new dataHelper(getContext());
        edtName.setText(name);
        edtIn.setText(data.netAmount()+"");
        edtOut.setText(data.totalDebit()+"");
        edtNet.setText(data.leftAmount()+"");

        return v;
    }


}
