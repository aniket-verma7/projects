package com.example.lenden.customAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lenden.R;
import com.example.lenden.addNew;
import com.example.lenden.userClass.ContactClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.Inflater;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> implements Filterable
{
    private Context context;
    private ArrayList<ContactClass> lstContact;
    private ArrayList<ContactClass> allContact;

    public ContactAdapter(Context context, ArrayList<ContactClass> lstContact)
    {
        this.context = context;
        this.lstContact = lstContact;
        this.allContact = new ArrayList<>(lstContact);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_card,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position)
    {
        holder.txtContact.setTextColor(Color.WHITE);
        holder.txtContact.setText(lstContact.get(position).getName());

        holder.txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidMobile(lstContact.get(position).getMob()))
             {
                Intent in = new Intent();
                in.putExtra("mob", lstContact.get(position).getMob());
                 ((Activity)context).setResult(Activity.RESULT_OK,in);
                 ((Activity)context).finish();
            }
                else
                {
                    Toast.makeText(context, "Invalid contact details", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }
            }
        });

    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    public void getNewList(String input)
    {

    }

    @Override
    public int getItemCount() {
        return lstContact.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter()
    {
    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
        ArrayList<ContactClass> filterLst = new ArrayList<>();

        if(constraint.toString().isEmpty())
        {
            filterLst.addAll(allContact);
        }
        else
        {
            for(ContactClass obj : lstContact)
            {
                if(obj.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                {
                    filterLst.add(obj);
                }
            }
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values=filterLst;
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        lstContact.clear();
        lstContact.addAll((Collection<? extends ContactClass>) results.values);
        notifyDataSetChanged();
    }
};

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView txtContact;
        public Holder(@NonNull View v)
        {
            super(v);
            txtContact = v.findViewById(R.id.txtContact);
        }
    }
}
