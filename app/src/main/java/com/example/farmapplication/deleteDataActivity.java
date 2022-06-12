package com.example.farmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class deleteDataActivity extends AppCompatActivity {
    private String[] tables = { "Food", "Contract", "Transport"};
    private Context context;
    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);
        editText = (EditText) findViewById(R.id.editTextId2);
        context = this;
        spinner = findViewById(R.id.spinnerTables2);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Выберите таблицу");
    }

    public void deleteApplication(View view)
    {
        new JSONTask(Integer.parseInt(editText.getText().toString()), spinner.getSelectedItem().toString())
                .execute("https://farm-mirea.herokuapp.com/farm/delete");
    }


    class JSONTask extends AsyncTask<String, String, String>
    {
        private int id;
        private String table;

        public JSONTask(int id, String table) {
            this.id = id;
            this.table = table;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                String json = "{\n" +
                        "    \"id\": " + id + ",\n" +
                        "    \"table\": \"" + table + "\"\n" +
                        "}";
                Log.i("JSON", json);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(json);

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}