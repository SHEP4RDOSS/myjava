package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
public class AddType extends AppCompatActivity {
    public static final String SELECTED_ITEM = "SELECTED_ITEM";
    private EditText ruleText;
    private EditText labelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        ruleText = (EditText) findViewById(R.id.ruleText);
        labelText = (EditText) findViewById(R.id.labelText);
    }

    public void newType(View view) {
        String rule = ruleText.getText().toString();
        String label = labelText.getText().toString();
        if (rule.length() > 0 && label.length() > 0) {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.KEY_RULE, rule);
            contentValues.put(DatabaseHelper.KEY_LABEL, label);
            database.insert(DatabaseHelper.TABLE_TYPE, null, contentValues);
        }


    }
}