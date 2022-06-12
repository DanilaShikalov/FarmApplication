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

public class getFieldsActivity extends AppCompatActivity {
    private List<String> listApp = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_fields);
        listView = findViewById(R.id.listFields);
        try {
            new JSONTask(this).execute("https://farm-mirea.herokuapp.com/farm/fields").get();
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
                            "ID: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("id") + "\nПоля: \n\tПлощадь: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("square") +
                                    " \n\tПочва: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("soil") +
                                    " \n\tОборудование: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("distanceForEquipment") +
                                    " \n\tХозяин: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("owner") +
                                    " \n\tСтатус: " + parentArray.getJSONObject(i).getJSONObject("fields").getString("status") +
                                    " \nАренда: \n\tНачало аренды: " + parentArray.getJSONObject(i).getJSONObject("leaseAgreement").getString("startofTheLease") +
                                    " \n\tКонец аренды: " + parentArray.getJSONObject(i).getJSONObject("leaseAgreement").getString("endofTheLease") +
                                    " \n\tСрок соглашения: " + parentArray.getJSONObject(i).getJSONObject("leaseAgreement").getString("termofTheAgreement") +
                                    " \n\tРента: " + parentArray.getJSONObject(i).getJSONObject("leaseAgreement").getString("rent")
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
