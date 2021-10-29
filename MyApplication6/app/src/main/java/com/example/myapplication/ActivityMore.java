package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityMore extends AppCompatActivity {
    public static final String SELECTED_ITEM = "SELECTED_ITEM";
    String selectedItem = "Не выбрано";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            selectedItem = extras.getString(SELECTED_ITEM);
    }


    @Override
    protected void onResume() {
        super.onResume();
        FragmentMore fragment = (FragmentMore) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_all);
        if (fragment != null)
            fragment.setSelectedItem(selectedItem);
    }
}
