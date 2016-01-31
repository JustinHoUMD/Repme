package com.hoyahacks.repme.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.hoyahacks.repme.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

import models.Question;
import utilities.RPrefs;

/**
 * Created by me on 1/30/16.
 */
public class ResultActivity extends ActionBarActivity {
    public static final String TAG = "ResultActivity";
    private TextView mOverallScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mOverallScore = (TextView) findViewById(R.id.score_num);
        mOverallScore.setText(RPrefs.getScore()+"");
        //Grab questions from parse and store in ArrayList
        ParseQuery<ParseObject> query = ParseQuery.getQuery("candidate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject o : objects) {
                    for (String key:o.keySet()) {
                        HashMap<String,Integer> cand = (HashMap<String,Integer>) o.get(key);
                        Log.d(TAG, cand.get("criminal")+"");
                    }
                }
            }
        });


    }


}
