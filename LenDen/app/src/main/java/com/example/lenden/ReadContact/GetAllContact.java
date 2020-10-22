package com.example.lenden.ReadContact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.Person;
import androidx.core.content.PermissionChecker;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lenden.MainActivity;
import com.example.lenden.R;
import com.example.lenden.customAdapter.ContactAdapter;
import com.example.lenden.customAdapter.adapter;
import com.example.lenden.userClass.ContactClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.Inflater;

public class GetAllContact extends AppCompatActivity
{

    private ContentResolver resolver;
    private ContactClass contactClass;
    private ArrayList<ContactClass> lstContact;
    private RecyclerView rcvContact;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_contact);

        //Toast.makeText(this, "Holla Amigo", Toast.LENGTH_SHORT).show();

        rcvContact = findViewById(R.id.rcvContact);
        rcvContact.setLayoutManager(new LinearLayoutManager(this));


        resolver = getContentResolver();

        lstContact = new ArrayList<>();

        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String cols[]=new String[]
                {
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                };

        Cursor cr = resolver.query(uri,cols,null,
                null,null);

        while(cr.moveToNext())
        {
            contactClass = new ContactClass();
            contactClass.setMob(cr.getString(0));
            contactClass.setName(cr.getString(1));
            lstContact.add(contactClass);
        }

        Collections.sort(lstContact,new Comparator<ContactClass>() {
            @Override
            public int compare(ContactClass o1, ContactClass o2)
            {
                return o1.getName().compareTo(o2.getName());
            }


        });

        contactAdapter = new ContactAdapter(this,lstContact);
        rcvContact.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                contactAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }


}

