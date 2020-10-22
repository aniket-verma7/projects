package com.example.lenden.fragmentPackage;
import com.example.lenden.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lenden.Database.dataHelper;
import com.example.lenden.R;
import com.example.lenden.addNew;
import com.example.lenden.customAdapter.adapter;
import com.example.lenden.userClass.Details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;

public class AccountFragment extends Fragment
{


    private RecyclerView rcv;
    private adapter custom;
    private dataHelper data;
    private Details del;
    private Details temp;
    private ArrayList<Details> details;
    private FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View v= inflater.inflate(R.layout.frag_account,container,false);
        rcv = v.findViewById(R.id.rcv);
        fabAdd = v.findViewById(R.id.fabAdd);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));

        del = new Details();
        temp = new Details();
        data = new dataHelper(getActivity());


/////////////////////////////////////////////////////////////////////////////////////////////////////

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

                    boolean check=true;
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
                    {
                        temp.setName(details.get(viewHolder.getAdapterPosition()).getName());
                        temp.setAmount(details.get(viewHolder.getAdapterPosition()).getAmount());
                        temp.setMob(details.get(viewHolder.getAdapterPosition()).getMob());
                        temp.setType(details.get(viewHolder.getAdapterPosition()).getType());
                        temp.setLocalDate(details.get(viewHolder.getAdapterPosition()).getLocalDate());

                        del.setId(details.get(viewHolder.getAdapterPosition()).getId());
                        data.deleteData(del);
                        details.remove(viewHolder.getAdapterPosition());
                        custom.notifyItemRemoved(viewHolder.getAdapterPosition());

                        Snackbar snackbar = Snackbar.make(getView(),"Account Deleted.",Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if(data.insertData(temp))
                                {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont,new AccountFragment()).commit();
                                }

                            }
                        });
                        snackbar.show();
                                        
                        if (custom.getItemCount() == 0) {
                            v.findViewById(R.id.txtStart).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                    }

                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rcv);
/////////////////////////////////////////////////////////////////////////////////////////////////////
        v.findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addNew.class);
                startActivityForResult(intent,100);
            }
        });
        details = data.getAllDetails();
        custom = new adapter(getActivity(),details);
        rcv.setAdapter(custom);


        if(custom.getItemCount()==0)
        {
            v.findViewById(R.id.txtStart).setVisibility(View.VISIBLE);

        }
        else
        {
            v.findViewById(R.id.txtStart).setVisibility(View.GONE);

        }
        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont,new AccountFragment()).commit();
        }

    }

}
