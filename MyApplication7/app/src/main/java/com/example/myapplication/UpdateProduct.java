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

public class UpdateProduct extends AppCompatActivity {
    private EditText name;
    private EditText count;
    private EditText checked;
    public static final String CHOICE_INFO = "CHOICE_INFO";
    String choiceInfo="null";
    private RadioGroup listId;
    private RadioGroup countType;

    Cursor cursor;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            choiceInfo = extras.getString(CHOICE_INFO);

        name=(EditText) findViewById(R.id.name);
        count=(EditText) findViewById(R.id.count);
        checked=(EditText) findViewById(R.id.checked);

        name.setText(choiceInfo.split(";")[1].split("=")[1]);
        count.setText(choiceInfo.split(";")[2].split("=")[1]);
        checked.setText(choiceInfo.split(";")[4].split("=")[1]);

        listId=(RadioGroup) findViewById(R.id.list_id);
        countType=(RadioGroup) findViewById(R.id.count_type);

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
                String tmp=choiceInfo.split(";")[3].split("=")[1].split(" ")[1];
                if(tmp.equals(cursor.getString(id)))
                    rd1.setTextColor(getResources().getColor(R.color.teal_200));


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
                String tmp=choiceInfo.split(";")[5].split("=")[1].split(" ")[1];
                if(tmp.equals(cursor.getString(id)))
                    rd1.setTextColor(getResources().getColor(R.color.teal_200));
                countType.addView(rd1);
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();
    }

    public void Update(View view){
        String name_ = name.getText().toString();
        String count_ = count.getText().toString();
        String checked_ = checked.getText().toString();
        String id = choiceInfo.split(";")[0].split("=")[1];
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
        database.update(DatabaseHelper.TABLE_PRODUCT, contentValues,DatabaseHelper.KEY_ID + "= ?",new String[]{id});
        } catch (Exception e) {}
    }

}