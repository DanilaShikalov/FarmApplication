package com.example.farmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class getFoodActivity extends AppCompatActivity {
    private List<String> listApp = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_food);
        listView = findViewById(R.id.listfood);
        try {
            new JSONTask(this).execute("https://farm-mirea.herokuapp.com//farm/crops-and-fertilizers").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class JSONTask extends AsyncTask<String, String, String>
    {
        private Context mContext;
        public JSONTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer= new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return String.valueOf(buffer);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                {
                    connection.disconnect();
                }
                try {
                    if (reader != null)
                    {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray parentArray = new JSONArray(result);
                Log.i("JSON", result);
                for (int i = 0; i < parentArray.length(); i++)
                {
                    listApp.add(
//                            parentArray.getJSONObject(i).toString()
                            "ID: " + parentArray.getJSONObject(i).getJSONObject("crops").getString("id") + "\nПосевы: \n\tНазвание: " + parentArray.getJSONObject(i).getJSONObject("crops").getString("title") +
                                    " \n\tЖелаемый тип почвы: " + parentArray.getJSONObject(i).getJSONObject("crops").getString("desiredTypeodSoil") +
                                    " \n\tУрожай: " + parentArray.getJSONObject(i).getJSONObject("crops").getString("cropYield") +
                                    " \n\tКоличество: " + parentArray.getJSONObject(i).getJSONObject("crops").getString("quantity") +
                            " \nУдобрения: \n\tНазвание: " + parentArray.getJSONObject(i).getJSONObject("fertilizers").getString("title") +
                                    " \n\tКоличество: " + parentArray.getJSONObject(i).getJSONObject("fertilizers").getString("quantity")
                    );
                }
                Log.i("RESULT", listApp.toString());
                String[] arr = new String[listApp.size()];
                arr = listApp.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_list_item_1, arr);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}