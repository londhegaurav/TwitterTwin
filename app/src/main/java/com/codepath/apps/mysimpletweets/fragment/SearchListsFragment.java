package com.codepath.apps.mysimpletweets.fragment;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Statuses;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by glondhe on 2/26/16.
 */
public class SearchListsFragment extends TweetListsFragments {

    public TwitterClient client;

    public  static SearchListsFragment newInstance(String queryParam ){
        SearchListsFragment searchListsFragment = new SearchListsFragment();
        Bundle args = new Bundle();
        args.putString("queryParam", queryParam);
        searchListsFragment.setArguments(args);
        return searchListsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    protected void populateTimeline() {
        client.getSearchList(getArguments().getString("queryParam"), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)  {
                // JSONObject jsonObject = new JSONObject(response.toString()).getJSONObject("Statuses");
                // Tweet tweet = Tweet.fromJson(jsonObject);
                Log.d("DEBUG", "Response: " + "Success: " + Statuses.fromJson(response));
                Statuses statuses = Statuses.fromJson(response);
                Tweet teTest = statuses.tw.get(0);
                System.out.println("Bagh ale barka :" + teTest.user.screenName);
                Log.d("DEBUG", "Response: " + "Success: " + response.toString());
                addAll(statuses.tw);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", "Response: " + "Failure: "+ errorResponse.toString());
                Log.d("DEBUG: " + getClass().toString(), errorResponse.toString());
            }
        });
        Log.d("DEBUG", "Response: " + "Going out");
    }

    @Override
    protected void fetchTimelineAsync() {

    }
}
