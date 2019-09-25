package com.gbreed.weatherdownloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;

        try
        {
            result = task.execute("https://samples.openweathermap.org/data/2.5/weather?zip=11758,us&appid=b6907d289e10d714a6e88b30761fae22").get();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... urls)
        {
            String result = "";
            URL url;
            HttpURLConnection connection = null;

            try{
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content",weatherInfo);

                JSONArray jsonArray = new JSONArray(weatherInfo);

                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));
                    Log.i("icon", jsonPart.getString("icon"));
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
