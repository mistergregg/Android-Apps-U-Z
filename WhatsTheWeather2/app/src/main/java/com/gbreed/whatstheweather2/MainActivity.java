package com.gbreed.whatstheweather2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    TextView textWeather;

    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            String result = "";

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1)
                {
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                return result;

            }catch(Exception e)
            {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Could not get weather", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textWeather = findViewById(R.id.textWeather);

        getWeather(textWeather.getRootView());
    }

    public void getWeather(View view)
    {
        EditText city = findViewById(R.id.textCity);

        String link = "http://api.openweathermap.org/data/2.5/weather?q=" + city.getText() + "&appid=cd9056c6036e1531783ff51913add43d";

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(city.getWindowToken(), 0);

        DownloadTask task = new DownloadTask();

        try
        {
            String result = task.execute(link).get();

            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo = jsonObject.getString("weather");

            JSONArray jsonArray = new JSONArray(weatherInfo);

            String lines = "";

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonPart = jsonArray.getJSONObject(i);

                Log.i("Part", jsonPart.toString());

                lines = lines + jsonPart.getString("main") + ": " + jsonPart.getString("description") + "\n";
            }

            textWeather.setText(lines);
        }catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not get weather", Toast.LENGTH_SHORT).show();
        }
    }
}
