package com.example.dgunda.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditItems;
    private final int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditItems =(EditText)findViewById(R.id.etEditItems);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  itemValue    = (String) lvItems.getItemAtPosition(position);
                launchComposeView(position, itemValue);
                aToDoAdapter.notifyDataSetChanged();
            }
        });
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String index = data.getExtras().getString("index");
            String editedValue = data.getExtras().getString("editedValue");
            int code = data.getExtras().getInt("responseCode", 0);
            //todoItems.add(Integer.parseInt(index),editedValue);
            todoItems.set(Integer.parseInt(index),editedValue);
            writeItems();
            aToDoAdapter.notifyDataSetChanged();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, editedValue, Toast.LENGTH_SHORT).show();
        }
    }
    public void launchComposeView(int itemPosition, String lvItems) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//        int j= 0;
//        for(String s: todoItems)
//        {
//            i.putExtra(Integer.toString(j),s);
//            j++;
//        }
        i.putExtra("value",lvItems);
        i.putExtra("position", Integer.toString(itemPosition));
        startActivityForResult(i, REQUEST_CODE); // brings up the second activity
    }
    public void populateArrayItems()
    {
//        todoItems = new ArrayList<String>();
//        todoItems.add("Items 1");
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    public void readItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e)
        {

        }
    }
    public void writeItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
           FileUtils.writeLines(file, todoItems);
        }catch (IOException e)
        {
          System.out.println(e.getMessage());
        }
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

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditItems.getText().toString());
        etEditItems.setText("");
        writeItems();

    }
}
