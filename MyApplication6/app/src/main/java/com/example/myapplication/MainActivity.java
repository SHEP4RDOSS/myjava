package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  implements NamesFragment.OnFragmentSendDataListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSendData(String selectedItem) {
        FragmentMore fragment = (FragmentMore) getSupportFragmentManager()
                .findFragmentById(R.id.names_fragment);
        if (fragment != null && fragment.isVisible())
            fragment.setSelectedItem(selectedItem);
        else {
            Intent intent = new Intent(getApplicationContext(),
                    ActivityMore.class);
            intent.putExtra(ActivityMore.SELECTED_ITEM, selectedItem);
            startActivity(intent);
        }
    }
}