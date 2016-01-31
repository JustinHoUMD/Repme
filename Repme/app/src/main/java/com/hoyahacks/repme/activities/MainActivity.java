package com.hoyahacks.repme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hoyahacks.repme.R;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashSet;

import utilities.RPrefs;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mRepublicanButton, mIndependentButton, mDemocratButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepublicanButton = (Button) findViewById(R.id.republican);
        mIndependentButton = (Button) findViewById(R.id.independent);
        mDemocratButton = (Button) findViewById(R.id.democrat);

        mRepublicanButton.setOnClickListener(this);
        mIndependentButton.setOnClickListener(this);
        mDemocratButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.democrat:
            case R.id.independent:
            case R.id.republican:
                //Democrat
                Intent intent = new Intent(this, SurveyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
