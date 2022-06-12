package com.example.farmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class postDataActivity extends AppCompatActivity {
    private String TitleCrops;
    private String desiredTypeodSoil;
    private String cropYield;
    private String quantitycrops;
    private String Titlefertilizers;
    private String quantityfertilizers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data);
    }

    public void postFood(View view)
    {
        TextView error = (TextView) findViewById(R.id.textView3);
        TextView surnameClient = (TextView) findViewById(R.id.edittextTitleCrops);
        TitleCrops = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextdesiredTypeodSoil);
        desiredTypeodSoil = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextcropYield);
        cropYield = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextQuantityCrops);
        quantitycrops = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTexttitle);
        Titlefertilizers = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextquantity);
        quantityfertilizers = surnameClient.getText().toString();
        if (!TitleCrops.equals("") && !desiredTypeodSoil.equals("") &&
                !cropYield.equals("") && !quantitycrops.equals("") && !Titlefertilizers.equals("") &&
                !quantityfertilizers.equals(""))
        {
            error.setText("");
            new JSONTask().execute("https://farm-mirea.herokuapp.com/farm/crops-and-fertilizers");
        }
        else {
            error.setText("Введите данные");
        }
    }

    class JSONTask extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                String json = "{\n" +
                        "  \"crops\" : {\n" +
                        "    \"title\" : \"" + TitleCrops +"\",\n" +
                        "    \"desiredTypeodSoil\" : \"" + desiredTypeodSoil + "\",\n" +
                        "    \"cropYield\" : " + cropYield + ",\n" +
                        "    \"quantity\" : " + quantitycrops + "\n" +
                        "  },\n" +
                        "  \"fertilizers\" : {\n" +
                        "    \"title\" : \"" + Titlefertilizers +"\",\n" +
                        "    \"quantity\" : " + quantityfertilizers + "\n" +
                        "  }\n" +
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