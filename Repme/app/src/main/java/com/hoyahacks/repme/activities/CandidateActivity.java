package com.hoyahacks.repme.activities;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hoyahacks.repme.R;

import java.util.ArrayList;

/**
 * Created by me on 1/30/16.
 */
public class CandidateActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);
    }


/*    private class CandidateAdapter extends ArrayAdapter {
        private Context context;
        private ArrayList<String> issues;
        public CandidateAdapter(Context context, ArrayList<String> issues) {
            super(context, )
            this.context = context;
            this.issues = issues;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


        }
    }*/
}
