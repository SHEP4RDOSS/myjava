package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;

public class AddProduct extends AppCompatActivity {
    public static final String SELECTED_ITEM = "SELECTED_ITEM";

    private RadioGroup listId;
    private RadioGroup countType;
    private EditText name;
    private EditText count;
    private EditText checked;

    Cursor cursor;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        listId=(RadioGroup) findViewById(R.id.list_id);
        countType=(RadioGroup) findViewById(R.id.count_type);
        name=(EditText)findViewById(R.id.name);
        count=(EditText)findViewById(R.id.count);
        checked=(EditText)findViewById(R.id.checked);


        List<String> info = new ArrayList<>();
        cursor=db.query(DatabaseHelper.TABLE_LISTS,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int id = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int name=cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
            int date=cursor.getColumnIndex(DatabaseHelper.KEY_DATE);
            int description=cursor.getColumnIndex(DatabaseHelper.KEY_DESCRIPTION);
            info.add("Lists");
            do{
                RadioButton rd1=new RadioButton(this);
                rd1.setText("id = "+cursor.getInt(id) + "; name = "+cursor.getString(name)+";");
                listId.addView(rd1);

            }while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");
        cursor.close();


        cursor = db.query(DatabaseHelper.TABLE_TYPE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            int label = cursor.getColumnIndex(DatabaseHelper.KEY_LABEL);
            int rule = cursor.getColumnIndex(DatabaseHelper.KEY_RULE);
            info.add("Type");
            do {
                RadioButton rd1=new RadioButton(this);
                rd1.setText("id = "+cursor.getInt(id) + "; label = "+cursor.getString(label)+";");
                countType.addView(rd1);
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();

    }

    public void add(View view){
        String name_ = name.getText().toString();
        String count_ = count.getText().toString();
        String checked_ = checked.getText().toString();
        if (name_.length() > 0 && count_.length() > 0 && checked_.length() > 0) {
            try {
                RadioButton rb1 = (RadioButton) listId.findViewById(listId.getCheckedRadioButtonId());
                RadioButton rb2 = (RadioButton) countType.findViewById(countType.getCheckedRadioButtonId());
                int id1 = Integer.valueOf(rb1.getText().toString().split(";")[0].split(" ")[2]);
                int id2 = Integer.valueOf(rb2.getText().toString().split(";")[0].split(" ")[2]);

                SQLiteDatabase database = databaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.KEY_NAME, name_);
                contentValues.put(DatabaseHelper.KEY_COUNT, count_);
                contentValues.put(DatabaseHelper.KEY_CHECKED, checked_);
                contentValues.put(DatabaseHelper.KEY_LIST_ID, id1);
                contentValues.put(DatabaseHelper.KEY_COUNT_TYPE, id2);
                database.insert(DatabaseHelper.TABLE_PRODUCT, null, contentValues);
            } catch (Exception e) {}
        }
    }




}