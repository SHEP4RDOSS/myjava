package com.example.myapplication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
public class AsyncTastk extends AsyncTask<String, Void, List<TSO>> {

    String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    protected List<TSO> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        List<String> returnInfo = new ArrayList<>();
        List<TSO> tso = new ArrayList<>();

        try {
            URL url = new URL("https://api.privatbank.ua/p24api/infrastructure?json&tso&address=&city=" + city);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                returnInfo.add("No result found");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            JSONObject jObject = null;

            String line;
            while ((line = reader.readLine()) != null) {
                jObject = new JSONObject(line);
                buffer.append(line + "\n");
            }
            JSONArray devices = jObject.getJSONArray("devices");

            for (int i = 0; i < devices.length(); i++) {
                try {
                    JSONObject oneObject = devices.getJSONObject(i);
                    List<String> stringList = new ArrayList<>();
                    JSONObject tw = oneObject.getJSONObject("tw");
                    stringList.add(tw.getString("mon"));
                    stringList.add(tw.getString("tue"));
                    stringList.add(tw.getString("wed"));
                    stringList.add(tw.getString("thu"));
                    stringList.add(tw.getString("fri"));
                    stringList.add(tw.getString("sat"));
                    stringList.add(tw.getString("sun"));
                    String address=oneObject.getString("fullAddressUa").split(",")[3]+oneObject.getString("fullAddressUa").split(",")[4];

                    TSO tso1 = new TSO(oneObject.getString("latitude"), oneObject.getString("longitude"),
                            address, oneObject.getString("placeUa"),
                            stringList);

                    tso.add(tso1);

                } catch (JSONException e) {
                }
            }

            if (buffer.length() == 0) {
                returnInfo.add("No result");
                return null;

            }
            return tso;
        } catch (IOException | JSONException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            returnInfo.add("No result");
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }
}
