package com.example.martin.zapu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        text = (TextView)findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button1);
        String URL1 ="http://api.openweathermap.org/data/2.5/weather?q=";
        String city ="Nürnberg";
        String URL2 ="&appid=7c513ca27cb7e1578a8e4190af0dbae9";
        final  String urltext = URL1+city+URL2;
        assert button != null;
        assert button1 != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(urltext);
            }

        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask1().execute(urltext);
            }

        });

    }

    public class JSONTask extends AsyncTask <String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpURLConnection connection;

                final URL url = new URL(params[0]);
                try {
                    connection =(HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream stream =connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(stream));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())){
                        buffer.append(line);
                    }
                  //  int responseCode = ((HttpURLConnection) url.openConnection()).getResponseCode();
                  //  System.out.println(responseCode);
                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            text.setText(result);
            super.onPostExecute(result);
        }
    }
    public class JSONTask1 extends AsyncTask <String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpURLConnection connection;

                final URL url = new URL(params[0]);
                try {
                    connection =(HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream stream =connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(stream));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())){
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONArray parentArray =parentObject.getJSONArray("weather");
                    JSONObject finalobject = parentArray.getJSONObject(0);
                    String moviename = finalobject.getString("main");
                    //int lon =parentObject.getInt("id");
                    return "" +moviename;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }



            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            text.setText(result);
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
