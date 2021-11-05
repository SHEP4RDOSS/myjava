package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private MapFragment mapFragment;
    private ListFragment listFragment;
    private FrameLayout mapFr;
    private FrameLayout listFr;
    Test test;
    private LocationManager locationManager;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final int INITIAL_REQUEST = 1337;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!canAccessLocation()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
        mapFr = (FrameLayout) findViewById(R.id.mapFr);
        listFr = (FrameLayout) findViewById(R.id.listFr);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        test = new Test();
    }

    public void Map(View view) throws ExecutionException, InterruptedException, IOException {
        mapFragment = new MapFragment(test.getLocation(), returnTSO());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapFr, mapFragment)
                .commit();

        listFr.setVisibility(View.GONE);
        mapFr.setVisibility(View.VISIBLE);
    }

    public void List(View view) {


        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listFr, listFragment)
                .commit();

        mapFr.setVisibility(View.GONE);
        listFr.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,1000*10,10,
                locationListener);
    }

    private LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            test.setLocation(location);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    public List<TSO>returnTSO() throws IOException, ExecutionException, InterruptedException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(test.getLocation().getLatitude(), test.getLocation().getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        String city = addresses.get(0).getLocality();

        AsyncTastk asyncTastk =new AsyncTastk();
        asyncTastk.setCity(city);
        List<TSO> str = new ArrayList<>();
        str.addAll(asyncTastk.execute().get());
        return str;
    }
}