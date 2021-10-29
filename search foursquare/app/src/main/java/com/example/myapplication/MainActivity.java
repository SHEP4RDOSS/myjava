package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private GridView list;
    String[] cat = new String[]{"food", "drinks","shops"};
    private Spinner spinner;
    String category="";
    private EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (GridView) findViewById(R.id.list);
        spinner = (Spinner) findViewById(R.id.spinner);
        location=(EditText)findViewById(R.id.location);

        ArrayAdapter<String> adapterSinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cat);
        adapterSinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSinner);
    }

    public void search(View view) throws ExecutionException, InterruptedException {
        HttpPostAsyncTask httpPostAsyncTask=new HttpPostAsyncTask();
        httpPostAsyncTask.setCategory(spinner.getSelectedItem().toString());
        httpPostAsyncTask.setLl(location.getText().toString());

        List<String> str = new ArrayList<>();
        str.addAll(httpPostAsyncTask.execute().get());
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, str);
        list.setAdapter(adapter);
    }
}