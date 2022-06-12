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
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class patchActivity extends AppCompatActivity {
    private String[] tables = { "crops", "fertilizers", "fields", "leaseagreement", "employmentcontract",
            "competencies", "employees", "specializations", "technicalequipment"};
    private String[] columns;
    private Context context;
    private Spinner spinner;
    private Spinner spinner1;
    private EditText editText;
    private EditText str_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch);
        editText = (EditText) findViewById(R.id.editTextId);
        str_ = (EditText) findViewById(R.id.editTextPatchEmp);
        context = this;
        spinner = findViewById(R.id.spinnerTables);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Выберите таблицу");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinner.getSelectedItem().toString()) {
                    case "crops":
                        columns = new String[]{"title",
                                "desiredtypeofsoil",
                                "cropyield",
                                "quantity"};
                        break;
                    case "fertilizers":
                        columns = new String[]{"title",
                                "quantity"};
                        break;
                    case "fields":
                        columns = new String[]{"soil",
                                "square",
                                "status",
                                "distanceforequipment",
                                "owner"};
                        break;
                    case "leaseagreement":
                        columns = new String[]{"rent",
                                "startofthelease",
                                "endofthelease",
                                "termoftheagreement"};
                        break;
                    case "employmentcontract":
                        columns = new String[]{"startdate",
                                "completiondate"};
                        break;
                    case "competencies":
                        columns = new String[]{"competence"};
                        break;
                    case "employees":
                        columns = new String[]{"name",
                                "workexperience"};
                        break;
                    case "specializations":
                        columns = new String[]{"specialization"};
                        break;
                    case "technicalequipment":
                        columns = new String[]{"title",
                                "fuelconsumption",
                                "idle",
                                "serviceability"};
                        break;
                }
                spinner1 = findViewById(R.id.spinnerColumns);
                ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, columns);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter);
                spinner1.setPrompt("Выберите столбец");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void patchApplication(View view)
    {
        new JSONTask(Integer.parseInt(editText.getText().toString()), spinner.getSelectedItem().toString(), spinner1.getSelectedItem().toString(), str_.getText().toString())
                .execute("https://farm-mirea.herokuapp.com/farm/patch");
    }


    class JSONTask extends AsyncTask<String, String, String>
    {
        private int id;
        private String table;
        private String column;
        private String str;

        public JSONTask(int id, String table, String column, String str) {
            this.id = id;
            this.table = table;
            this.column = column;
            this.str = str;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PATCH");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                String json = "{\n" +
                        "    \"id\": " + id + ",\n" +
                        "    \"str\": \"" + str + "\",\n" +
                        "    \"column\": \"" + column + "\",\n" +
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