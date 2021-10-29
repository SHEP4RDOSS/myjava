package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListView listProduct;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter listsAdapter;
    private String choiceInfo;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listType);
        listProduct = (ListView) findViewById(R.id.listProduct);
        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        ListAdapter adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GetInfoFromType());
        listView.setAdapter(adapterType);
        ListAdapter adapterProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getInfoFromProduct());
        listProduct.setAdapter(adapterProduct);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                choiceInfo = textView.getText().toString();
                choice=1;
            }
        });


        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                choiceInfo = textView.getText().toString();
                choice=2;
            }
        });


//        databaseHelper.close();

    }

    public List<String> GetInfoFromType() {
        List<String> info = new ArrayList<>();
        cursor = db.query(DatabaseHelper.TABLE_TYPE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int label = cursor.getColumnIndex(DatabaseHelper.KEY_LABEL);
            int rule = cursor.getColumnIndex(DatabaseHelper.KEY_RULE);
            info.add("Type");
            do {
                info.add("id = " + cursor.getInt(id) +
                        "; label = " + cursor.getString(label) +
                        "; rule = " + cursor.getString(rule));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();
        return info;
    }

    public List<String> getInfoFromProduct() {
        List<String> info = new ArrayList<>();
        cursor = db.query(DatabaseHelper.TABLE_PRODUCT, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int name = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
            int count = cursor.getColumnIndex(DatabaseHelper.KEY_COUNT);
            int list_id = cursor.getColumnIndex(DatabaseHelper.KEY_LIST_ID);
            int checked = cursor.getColumnIndex(DatabaseHelper.KEY_CHECKED);
            int count_type = cursor.getColumnIndex(DatabaseHelper.KEY_COUNT_TYPE);

            info.add("Product");
            do {
                info.add("id = " + cursor.getInt(id) +
                        "; name = " + cursor.getString(name) +
                        "; count = " + cursor.getDouble(count) +
                        "; list_id = " + cursor.getInt(list_id) +
                        "; checked = " + cursor.getInt(checked) +
                        "; count_type = " + cursor.getInt(count_type));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();
        return info;
    }

    public void AddType(View view) {
        Intent intent = new Intent(getApplicationContext(),
                AddType.class);
        startActivity(intent);
    }

    public void AddProduct(View view) {
        Intent intent = new Intent(getApplicationContext(),
                AddProduct.class);
        startActivity(intent);
    }

    public void Delete(View view) {
        if (choiceInfo.length() > 0) {
            int id = Integer.valueOf(choiceInfo.split(";")[0].split(" ")[2]);
            if (choice == 1) {
                db.delete(DatabaseHelper.TABLE_TYPE, DatabaseHelper.KEY_ID + " = " + id, null);
            } else {
                db.delete(DatabaseHelper.TABLE_PRODUCT, DatabaseHelper.KEY_ID + " = " + id, null);
            }
            Activity();
        }
    }

    public void Activity() {
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    public void Update(View view) {
        if(choice==1) {
            if (choiceInfo.length() > 0) {
                Intent intent = new Intent(getApplicationContext(),
                        Update.class);
                intent.putExtra(Update.CHOICE_INFO, choiceInfo);
                startActivity(intent);
            }
        }else{
            if (choiceInfo.length() > 0) {
                Intent intent = new Intent(getApplicationContext(),
                        UpdateProduct.class);
                intent.putExtra(Update.CHOICE_INFO, choiceInfo);
                startActivity(intent);
            }
        }

    }
}