package com.example.user.phonebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    ListView listView;
    String[] numbers;
    SimpleCursorAdapter cursorAdapter;
    String phoneNumber;

    CustomAdapter adapter;

    static ArrayList <String> contact_name_list = new ArrayList<String>();
    static ArrayList <String> contact_phone_list = new ArrayList<String>();

    List<String> turkcellList = new ArrayList<String>();
    List<String> vodafoneList = new ArrayList<String>();
    List<String> ttnetList = new ArrayList<String>();

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

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.button_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, final int checkedId) {
                // checkedId is the RadioButton selected

                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                String hat = checkedRadioButton.getText().toString();

                if(hat.equals("Tttnet")) {
                    //ttnetList.clear();
                    List<String> list = getTtnetNumbers();
                    updateListView(list);
                }else if(hat.equals("Turkcell")) {
                    turkcellList.clear();
                    getTurkcellNumbers();
                    updateListView(turkcellList);

                }else if(hat.equals("Vodafone")) {
                    vodafoneList.clear();
                    getVodafoneNumbers();
                    updateListView(vodafoneList);

                }else if(hat.equals("Restore All")) {
                    restoreAll();
                }

            }
        });

        searchView = (SearchView) findViewById(R.id.search_view);
        listView = (ListView) findViewById(R.id.list_view);
        numbers = getResources().getStringArray(R.array.numbers);

        searchView.setFocusable(false);
        listView.setTextFilterEnabled(true);

        getNumber(this.getContentResolver());
        List<ArrayList> a = new ArrayList<ArrayList>();
        a.add(contact_name_list);
        a.add(contact_phone_list);
        adapter = new CustomAdapter(this,contact_name_list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setUpSearchView();
    }

    public void updateListView(List<String> numbers) {
        CustomAdapter adapter = new CustomAdapter(this,numbers);
        //adapter.notifyDataSetChanged();
        listView.destroyDrawingCache();
        listView.setVisibility(ListView.INVISIBLE);
        listView.setAdapter(adapter);
        listView.setVisibility(ListView.VISIBLE);
    }

    public void reduceToTurkcell() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");
            //turkcellList.clear();
            if(number.startsWith("0530") ||
                    number.startsWith("0531") ||
                    number.startsWith("0532") ||
                    number.startsWith("0533") ||
                    number.startsWith("0534") ||
                    number.startsWith("0535") ||
                    number.startsWith("0536") ||
                    number.startsWith("0537") ||
                    number.startsWith("0538") ||
                    number.startsWith("0539")) {
                turkcellList.add(contact_name_list.get(i));
                //listView.setAdapter(new CustomAdapter(this,turkcellList));
                adapter = new CustomAdapter(this,turkcellList);
                //adapter.notifyDataSetChanged();
                listView.destroyDrawingCache();
                listView.setVisibility(ListView.INVISIBLE);
                listView.setAdapter(adapter);
                listView.setVisibility(ListView.VISIBLE);

            }
        }
    }

    public void reduceToVodafone() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");

            if(number.startsWith("0540") ||
                    number.startsWith("0541") ||
                    number.startsWith("0542") ||
                    number.startsWith("0543") ||
                    number.startsWith("0544") ||
                    number.startsWith("0545") ||
                    number.startsWith("0546") ||
                    number.startsWith("0547") ||
                    number.startsWith("0548") ||
                    number.startsWith("0549")) {
                vodafoneList.add(contact_name_list.get(i));
                //listView.setAdapter(new CustomAdapter(this,turkcellList));
                adapter = new CustomAdapter(this,vodafoneList);
                //adapter.notifyDataSetChanged();
                listView.destroyDrawingCache();
                listView.setVisibility(ListView.INVISIBLE);
                listView.setAdapter(adapter);
                listView.setVisibility(ListView.VISIBLE);
                //turkcellList.clear();
            }
        }
    }

    public void reduceToTtnet() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");

            if(number.startsWith("0505") ||
                    number.startsWith("0506") ||
                    number.startsWith("0507") ||
                    number.startsWith("0555") ||
                    number.startsWith("0556") ||
                    number.startsWith("0557") ||
                    number.startsWith("0558") ||
                    number.startsWith("0559")) {
                ttnetList.add(contact_name_list.get(i));
                //listView.setAdapter(new CustomAdapter(this,turkcellList));
                adapter = new CustomAdapter(this,ttnetList);
                //adapter.notifyDataSetChanged();
                listView.destroyDrawingCache();
                listView.setVisibility(ListView.INVISIBLE);
                listView.setAdapter(adapter);
                listView.setVisibility(ListView.VISIBLE);
                //turkcellList.clear();
            }
        }
    }

    public void restoreAll() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");



                adapter = new CustomAdapter(this,contact_name_list);
                //adapter.notifyDataSetChanged();
                listView.destroyDrawingCache();
                listView.setVisibility(ListView.INVISIBLE);
                listView.setAdapter(adapter);
                listView.setVisibility(ListView.VISIBLE);
                turkcellList.clear();

        }
    }

    public void onSaveButtonClick(View v) {
        writeToFile(contact_name_list);
    }

    public void onRecoverButtonClick(View v) {
        readFromFile();
    }

    private void writeToFile(List<String> values) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("config.txt", Context.MODE_PRIVATE));
            for(int i = 0; i < values.size(); i++) {
                String[] parts = values.get(i).split("-");
                outputStreamWriter.write(parts[0]+" "+parts[1]);
                outputStreamWriter.write("\n");
            }
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                System.out.println(ret);
            }else {
                Toast.makeText(this,"There are no recovery files",Toast.LENGTH_LONG);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            Toast.makeText(this, "There are no recovery files", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void getVodafoneNumbers() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");

            if(number.startsWith("+90")) {
                number = number.substring(3);

            }else {
                if(number.startsWith("0540") ||
                        number.startsWith("0541") ||
                        number.startsWith("0542") ||
                        number.startsWith("0543") ||
                        number.startsWith("0544") ||
                        number.startsWith("0545") ||
                        number.startsWith("0546") ||
                        number.startsWith("0547") ||
                        number.startsWith("0548") ||
                        number.startsWith("0549")) {
                    vodafoneList.add(contact_name_list.get(i));


                }
            }

        }
    }

    public void getTurkcellNumbers() {
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");

            if(number.startsWith("+90")) {
                number = number.substring(3);

            }else {
                if(number.startsWith("0530") ||
                        number.startsWith("0531") ||
                        number.startsWith("0532") ||
                        number.startsWith("0533") ||
                        number.startsWith("0534") ||
                        number.startsWith("0535") ||
                        number.startsWith("0536") ||
                        number.startsWith("0537") ||
                        number.startsWith("0538") ||
                        number.startsWith("0539")) {
                    turkcellList.add(contact_name_list.get(i));
                    //listView.setAdapter(new CustomAdapter(this,turkcellList));


                }
            }

        }
    }

    public List<String> getTtnetNumbers() {
        List<String> ttnetList = new ArrayList<>();
        for(int i = 0; i < contact_name_list.size(); i++) {
            String[] parts = contact_name_list.get(i).split("-");
            String number = parts[1].replaceAll("[-+^:.]","");

            if(number.startsWith("+90")) {
                number = number.substring(3);

            }else {
                if(number.startsWith("0505") ||
                        number.startsWith("0506") ||
                        number.startsWith("0507") ||
                        number.startsWith("0555") ||
                        number.startsWith("0556") ||
                        number.startsWith("0557") ||
                        number.startsWith("0558") ||
                        number.startsWith("0559")) {
                    ttnetList.add(contact_name_list.get(i));
                    //listView.setAdapter(new CustomAdapter(this,turkcellList));

                }
            }

        }
        return ttnetList;
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

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onResume();
        getNumber(this.getContentResolver());
        this.recreate();
    }

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
