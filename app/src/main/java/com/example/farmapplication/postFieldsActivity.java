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

public class postFieldsActivity extends AppCompatActivity {
    private String square;
    private String soil;
    private String distanceForEquipment;
    private String owner;
    private String status;
    private String rent;
    private String startofTheLease;
    private String endofTheLease;
    private String termofTheAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_fields);
    }

    public void postFood(View view)
    {
        TextView error = (TextView) findViewById(R.id.textView8);
        TextView surnameClient = (TextView) findViewById(R.id.editTextSquare);
        square = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextSoil);
        soil = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextDistance);
        distanceForEquipment = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextOwner);
        owner = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextStatus);
        status = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextRent);
        rent = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextstartLease);
        startofTheLease = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTextendLease);
        endofTheLease = surnameClient.getText().toString();
        surnameClient = (TextView) findViewById(R.id.editTexttermo);
        termofTheAgreement = surnameClient.getText().toString();
        if (!square.equals("") && !soil.equals("") &&
                !distanceForEquipment.equals("") && !owner.equals("") && !status.equals("") &&
                !rent.equals("") && !startofTheLease.equals("") && !endofTheLease.equals("") &&
                !termofTheAgreement.equals(""))
        {
            error.setText("");
            new JSONTask().execute("https://farm-mirea.herokuapp.com/farm/fields");
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
                        "  \"fields\" : {\n" +
                        "    \"square\" : " + square + ",\n" +
                        "    \"soil\" : " + soil + ",\n" +
                        "    \"distanceForEquipment\" : " + distanceForEquipment + ",\n" +
                        "    \"owner\" : " + owner + ",\n" +
                        "    \"status\" : " + status + "\n" +
                        "  },\n" +
                        "  \"leaseAgreement\" : {\n" +
                        "    \"rent\" : " + rent + ",\n" +
                        "    \"startofTheLease\" : \"" + startofTheLease + "\",\n" +
                        "    \"endofTheLease\" : \"" + endofTheLease + "\",\n" +
                        "    \"termofTheAgreement\" : " + termofTheAgreement + "\n" +
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