package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Update extends AppCompatActivity {
    private EditText labelText;
    private EditText ruleText;
    public static final String CHOICE_INFO = "CHOICE_INFO";
    String choiceInfo="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            choiceInfo = extras.getString(CHOICE_INFO);

        labelText=(EditText) findViewById(R.id.labelText);
        ruleText=(EditText) findViewById(R.id.ruleText);

        labelText.setText(choiceInfo.split(";")[1].split("=")[1]);
        ruleText.setText(choiceInfo.split(";")[2].split("=")[1]);

    }

    public void Update(View view){
        String rule = ruleText.getText().toString();
        String label = labelText.getText().toString();
        String id = choiceInfo.split(";")[0].split("=")[1];
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_RULE, rule);
        contentValues.put(DatabaseHelper.KEY_LABEL, label);
        database.update(DatabaseHelper.TABLE_TYPE, contentValues,DatabaseHelper.KEY_ID + "= ?",new String[]{id});
    }


}