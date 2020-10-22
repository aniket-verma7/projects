package com.example.lenden.customAdapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lenden.MainActivity;
import com.example.lenden.R;
import com.example.lenden.addNew;
import com.example.lenden.userClass.ContactClass;
import com.example.lenden.userClass.Details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.Holder>
{
    Context context;
    ArrayList<Details> lstDetails;
    ArrayList<Details> lstAll;
    public adapter() {
    }

    public adapter(Context context, ArrayList<Details> lstDetails)
    {
        this.context = context;
        this.lstDetails = lstDetails;
        this.lstAll = new ArrayList<>(lstDetails);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.mycard,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position)
    {

        Details details = new Details();

        holder.txtName.setText(lstDetails.get(position).getName());
        String type = lstDetails.get(position).getType();
        if(type.equals("Credit"))
        {
            holder.txtAmount.setTextColor(Color.GREEN);
        }
        else
        {
            holder.txtAmount.setTextColor(Color.RED);
        }
        holder.txtAmount.setText(lstDetails.get(position).getAmount());

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,addNew.class);
                intent.putExtra("id",lstDetails.get(position).getId());
                intent.putExtra("name",lstDetails.get(position).getName());
                intent.putExtra("amount",lstDetails.get(position).getAmount());
                intent.putExtra("type",lstDetails.get(position).getType());
                intent.putExtra("mob",lstDetails.get(position).getMob());
                intent.putExtra("date",lstDetails.get(position).getLocalDate());
                intent.putExtra("fab",1);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return lstDetails.size();
    }


    public class Holder extends RecyclerView.ViewHolder
    {
        TextView txtName,txtAmount;
        CardView crd;

        public Holder(@NonNull View v)
        {

            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtAmount = v.findViewById(R.id.txtAmount);


        }
    }

}
