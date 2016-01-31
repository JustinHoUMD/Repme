package com.hoyahacks.repme.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hoyahacks.repme.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import models.Category;
import models.Difference;
import models.Legislator;
import models.Question;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import utilities.RPrefs;

/**
 * Created by me on 1/30/16.
 */
public class ResultActivity extends ActionBarActivity {
    public static final String TAG = "ResultActivity";
    private static final String API_KEY = "GQW6JFUW1LNE8B54T96WNFT34S5HCYP2";
    private TextView mOverallScore;
    private HashMap<String, Double> difference;
    private HashMap<String, ArrayList<Category>> categoryMap;
    private ArrayList<Difference> differences;
    private ListView mPoliticianList;
    private ArrayList<Legislator> legislators;
    private Activity activity = this;

    public interface LegislatorService {
        @GET("/legislator/{id}")
        Call<Legislator> getLegislator(@Path("id") String id, @Query("apikey") String apikey);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        legislators = new ArrayList<Legislator>();
        difference = new HashMap<String, Double>();
        differences = new ArrayList<Difference>();
        categoryMap = new HashMap<String, ArrayList<Category>>();


        mPoliticianList = (ListView) findViewById(R.id.candidate_list);
        mPoliticianList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, CandidateActivity.class);
                intent.putExtra("legislator", legislators.get(position));
                intent.putExtra("categories", categoryMap.get(legislators.get(position).id));
                startActivity(intent);
            }
        });
        mOverallScore = (TextView) findViewById(R.id.score_num);
        mOverallScore.setText(RPrefs.getScore() + "");
        //Grab questions from parse and store in ArrayList
        ParseQuery<ParseObject> query = ParseQuery.getQuery("candidate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // Get the one row
                for (ParseObject o : objects) {
                    // For each politician
                    for (String key : o.keySet()) {
                        // Convert each politician's scores into a HashMap
                        HashMap<String, String> cand = (HashMap<String, String>) o.get(key);
                        Log.d(TAG, cand.get("criminal") + "");
                        // For each category in the HashMap
                        ArrayList<Category> categoryArray = new ArrayList<Category>();
                        for (String category : cand.keySet()) {
                            if (category.equals("overall_score")) {
                                continue;
                            }
                            double diff = Math.abs(((double)RPrefs.getInt(category)) -
                                    Double.valueOf(cand.get(category)));
                            Category c = new Category(category,diff);
                            categoryArray.add(c);
                            Log.d(TAG, "difference: " + diff);
                            if (difference.containsKey(key)) {
                                difference.put(key, difference.get(key) + diff);
                            } else {
                                difference.put(key, diff);
                            }
                        }
                        categoryMap.put(key, categoryArray);

                    }
                }
                for (String p : difference.keySet()) {
                    Difference d = new Difference(p, difference.get(p));
                    Log.d(TAG, "Politician: " + d.politician);
                    differences.add(d);
                }
                Collections.sort(differences);
                for (Difference d : differences) {
                    Log.d(TAG, "diff: " + d.politician + " " + d.difference);
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://hackathon.fiscalnote.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LegislatorService service = retrofit.create(LegislatorService.class);
                for (int i = 0; i < 5; i++) {
                    Call<Legislator> call = service.getLegislator(differences.get(i).politician, API_KEY);
                    call.enqueue(new Callback<Legislator>() {

                        @Override
                        public void onResponse(Response<Legislator> response, Retrofit retrofit) {
                            if (response.isSuccess()) {
                                Log.d(TAG, "Message: " + response.message());
                                Legislator leg = response.body();
                                Log.d(TAG, "Legislator name: "+leg.full_name);
                                legislators.add(leg);
                                LegislatorAdapter adapter = new LegislatorAdapter(activity, legislators);
                                mPoliticianList.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }



                    });
                }
            }
        });



    }

    private class LegislatorAdapter extends ArrayAdapter {
        private Context context;
        private ArrayList<Legislator> legs;
        public LegislatorAdapter(Context context, ArrayList<Legislator> legs) {
            super(context, R.layout.politician_list_element, legs);
            this.context = context;
            this.legs = legs;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Legislator leg = legislators.get(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.politician_list_element, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.politician_name);
            TextView ranking = (TextView) rowView.findViewById(R.id.ranking);
            name.setText(leg.full_name);
            ranking.setText("Rank: " + (position + 1));
            return rowView;
        }
    }
}
