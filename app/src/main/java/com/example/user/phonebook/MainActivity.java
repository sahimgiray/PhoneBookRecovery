package com.example.user.phonebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    ListView listView;
    String[] numbers;
    SimpleCursorAdapter cursorAdapter;
    String phoneNumber;

    static ArrayList <String> contact_name_list = new ArrayList<String>();
    static ArrayList <String> contact_phone_list = new ArrayList<String>();
    List <ContactInformations> contactInformationsArrayList = new ArrayList<ContactInformations>();

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    private final static int[] TO_IDS = {
            android.R.id.text1
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView) findViewById(R.id.search_view);
        listView = (ListView) findViewById(R.id.list_view);
        numbers = getResources().getStringArray(R.array.numbers);

        searchView.setFocusable(false);
        listView.setTextFilterEnabled(true);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers));
        /*cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.contact_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0
                );
        listView.setAdapter(cursorAdapter);
        listView.setTextFilterEnabled(true);
        */

        //getNumber(this.getContentResolver());
        //CustomAdapter ca = new CustomAdapter(this,numbers);
        //listView.setAdapter(ca);
        //setUpSearchView();
        getNumber(this.getContentResolver());
        List<ArrayList> a = new ArrayList<ArrayList>();
        a.add(contact_name_list);
        a.add(contact_phone_list);
        CustomAdapter adapter = new CustomAdapter(this,contact_name_list);
        listView.setAdapter(adapter);
        setUpSearchView();
    }

    public void getNumber(ContentResolver cr)
    {

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(".................."+phoneNumber);
            contact_name_list.add(name + "-" + phoneNumber);
            //contact_name_list.add(phoneNumber);


        }
        phones.close();// close cursor

        //display contact numbers in the list
    }

    private void setUpSearchView() {

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) listView.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

            }

        });
    }
    //ContentResolver cr = this.getContentResolver();
    /*public void onClick(View v) {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(".................."+phoneNumber);
            contact_name_list.add(phoneNumber);
        }
        phones.close();// close cursor
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,contact_name_list);
        listView.setAdapter(adapter);
        //display contact numbers in the list
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }
}
