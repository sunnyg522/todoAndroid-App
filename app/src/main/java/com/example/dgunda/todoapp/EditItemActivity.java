package com.example.dgunda.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class EditItemActivity extends AppCompatActivity {

    private ArrayAdapter<String> aToDoAdapter;
    private EditText etTodo;
    private String itemValue;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
         position = getIntent().getStringExtra("position");
        itemValue= getIntent().getStringExtra("value");
        etTodo = (EditText)findViewById(R.id.etTodo);
        etTodo.setText(itemValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
    public void onSave(View view) {

        Intent response = new Intent();
            response.putExtra("editedValue", etTodo.getText().toString());
            response.putExtra("index",position);
            response.putExtra("responseCode", "200");
            setResult(RESULT_OK, response);
            finish();
        this.finish();
    }
}
