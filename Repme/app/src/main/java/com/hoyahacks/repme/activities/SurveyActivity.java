package com.hoyahacks.repme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hoyahacks.repme.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import models.Question;
import utilities.RPrefs;

public class SurveyActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String TAG = "SurveyActivity";
    private ArrayList<Question> questions;
    private int questionCounter = 0;
    private HashSet<String> issues;

    private Button mOption1, mOption2, mOption3;
    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        questions = new ArrayList<Question>();
        issues = new HashSet<String>();
        question = (TextView) findViewById(R.id.question);
        mOption1 = (Button) findViewById(R.id.option_1);
        mOption2 = (Button) findViewById(R.id.option_2);
        mOption3 = (Button) findViewById(R.id.option_3);

        mOption1.setOnClickListener(this);
        mOption2.setOnClickListener(this);
        mOption3.setOnClickListener(this);

        //Grab questions from parse and store in ArrayList
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for(ParseObject o: objects) {
                    issues.add(o.getString("type").toLowerCase().replace(" ", "+"));
                    Question q = new Question(o.getString("question"), o.getString("type").toLowerCase().replace(" ","+"), o.getString("party"));
                    Log.d(TAG, "question: " + q.question);
                    questions.add(q);
                }
                question.setText(questions.get(0).question);
                // Reset counts
                for (String issue: issues) {
                    RPrefs.putInt(issue, 0);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
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
        Question curr = questions.get(questionCounter);
        if ((questionCounter + 1) < questions.size()) {
            question.setText(questions.get(++questionCounter).question);
        } else {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
        }
        switch (v.getId()) {
            case R.id.option_1:
                int count = RPrefs.getInt(curr.type);
                if (curr.party.equals("left")) {
                    Log.d(TAG, "Type: "+curr.type+" "+"Val: "+count);
                    RPrefs.putInt(curr.type, --count);
                } else {
                    Log.d(TAG, "Type: "+curr.type+" "+"Val: "+count);
                    RPrefs.putInt(curr.type, ++count);
                }
                break;
            case R.id.option_2:
                int count2 = RPrefs.getInt(curr.type);
                if (curr.party.equals("left")) {
                    Log.d(TAG, "Type: "+curr.type+" "+"Val: "+count2);
                    RPrefs.putInt(curr.type, ++count2);
                } else {
                    Log.d(TAG, "Type: "+curr.type+" "+"Val: "+count2);
                    RPrefs.putInt(curr.type, --count2);
                }
                break;
            case R.id.option_3:
                break;
        }
    }
}
