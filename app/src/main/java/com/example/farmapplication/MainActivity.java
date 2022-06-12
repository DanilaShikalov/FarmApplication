package com.example.farmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getFood(View view)
    {
        Intent intent = new Intent("com.example.myapplication.getFoodActivity");
        startActivity(intent);
    }

    public void getFields(View view)
    {
        Intent intent = new Intent("com.example.myapplication.getFieldsActivity");
        startActivity(intent);
    }

    public void getContract(View view)
    {
        Intent intent = new Intent("com.example.myapplication.getContractsActivity");
        startActivity(intent);
    }

    public void patchData(View view)
    {
        Intent intent = new Intent("com.example.myapplication.patchActivity");
        startActivity(intent);
    }

    public void deleteData(View view)
    {
        Intent intent = new Intent("com.example.myapplication.deleteDataActivity");
        startActivity(intent);
    }

    public void postFood(View view)
    {
        Intent intent = new Intent("com.example.myapplication.postDataActivity");
        startActivity(intent);
    }

    public void postFields(View view)
    {
        Intent intent = new Intent("com.example.myapplication.postFieldsActivity");
        startActivity(intent);
    }

    public void getTransports(View view)
    {
        Intent intent = new Intent("com.example.myapplication.getTransportsActivity");
        startActivity(intent);
    }
}