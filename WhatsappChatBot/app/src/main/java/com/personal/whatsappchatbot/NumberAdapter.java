package com.personal.whatsappchatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.Holder>
{
    private Context context;
    private ArrayList<Integer> lst;

    public NumberAdapter(Context context, ArrayList<Integer> lst)
    {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.number_card,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        holder.number.setText(lst.get(position)+"");
    }

    @Override
    public int getItemCount()
    {
        return lst.size();
    }


    class Holder extends RecyclerView.ViewHolder
    {
        TextView number;
        public Holder(@NonNull View itemView)
        {
            super(itemView);
            number = itemView.findViewById(R.id.txtNumber);
        }
    }
}
