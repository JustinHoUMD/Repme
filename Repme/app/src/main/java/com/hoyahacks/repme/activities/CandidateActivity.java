package com.hoyahacks.repme.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoyahacks.repme.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import models.Category;
import models.Legislator;

/**
 * Created by me on 1/30/16.
 */
public class CandidateActivity extends ActionBarActivity {
    private TextView mName, mParty, mIssues;
    private ImageView mImage;
    private Legislator legislator;
    private ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        Intent intent = getIntent();
        legislator =  (Legislator) intent.getSerializableExtra("legislator");
        categories = (ArrayList<Category>) intent.getSerializableExtra("categories");
        Collections.sort(categories);

        mName = (TextView) findViewById(R.id.candidate_name);
        mParty = (TextView) findViewById(R.id.candidate_party);
        mImage = (ImageView) findViewById(R.id.legislator_image);
        mIssues = (TextView) findViewById(R.id.issues);

        mName.setText(legislator.full_name);
        mParty.setText(legislator.party);
        String s = "";
        for (int i = 0; i < 5; i++) {
            s+=categories.get(i).category+"\n";
        }
        mIssues.setText(s);

        if (legislator.photo_url != null) {
            Picasso.with(this).load(legislator.photo_url).into(mImage);
        }
    }


}
