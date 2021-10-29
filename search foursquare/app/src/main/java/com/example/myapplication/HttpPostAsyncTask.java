package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpPostAsyncTask extends AsyncTask<String, Void, List<String>> {

    String category;
    String ll;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        List<String>returnInfo=new ArrayList<>();

        try {
            String url_="https://api.foursquare.com/v2/venues/explore?client_id=3CWW1LSQYG24GJJMFQ30QDRAUKBZ0M4VC4OGPTY35TCTBWL5&client_secret=UM3OCGBKOQQN1VQMZXU4I1ARJVTQMAFQCSHT1LGS0JMOWC2A&v=20180323&limit=5&ll="+ll+"&query="+category;
            URL url = new URL("https://api.foursquare.com/v2/venues/explore?client_id=3CWW1LSQYG24GJJMFQ30QDRAUKBZ0M4VC4OGPTY35TCTBWL5&client_secret=UM3OCGBKOQQN1VQMZXU4I1ARJVTQMAFQCSHT1LGS0JMOWC2A&v=20180323&limit=5&ll="+ll+"&query="+category);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                returnInfo.add("No result found");
                return returnInfo;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            JSONObject jObject=null;

            String line;
            while ((line = reader.readLine()) != null) {
                jObject = new JSONObject(line);
                buffer.append(line + "\n");
            }
            JSONObject response = (JSONObject) jObject.get("response");
            JSONArray groups = response.getJSONArray("groups");

            for (int i=0; i < groups.length(); i++)
            {
                try {
                    JSONObject oneObject = groups.getJSONObject(i);
                    JSONArray items = oneObject.getJSONArray("items");
                    for (int j=0; j < items.length(); j++){
                        JSONObject venue = items.getJSONObject(j).getJSONObject("venue");
                        String name = venue.getString("name");
                        String address = venue.getJSONObject("location").getString("address");
                        returnInfo.add("Name: "+name+"\nAddress: "+address);
                    }


                } catch (JSONException e) {
                }
            }

            if (buffer.length() == 0) {
                returnInfo.add("No results found");
                return returnInfo;

            }
            return returnInfo;
        } catch (IOException | JSONException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            returnInfo.add("No result found");
            return returnInfo;
        } finally{
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