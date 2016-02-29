package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.fragment.TweetListsFragments;
import com.codepath.apps.mysimpletweets.models.Message;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by glondhe on 2/27/16.
 */
public class MessageActivity extends TweetListsFragments {

    public TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }
    @Override
    protected void populateTimeline() {
        client.getmessageList(new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // JSONObject jsonObject = new JSONObject(response.toString()).getJSONObject("Statuses");
                // Tweet tweet = Tweet.fromJson(jsonObject);
                Log.d("DEBUG", "Response: " + "Success: " + Message.parseJSON(response.toString()));
                Message message = Message.parseJSON(response.toString());
              //  Tweet teTest = statuses.tw.get(0);
                //System.out.println("Bagh ale barka :" + teTest.user.screenName);
                Log.d("DEBUG", "Response: " + "Success: " + response.toString());
                //addAll(statuses.tw);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", "Response: " + "Failure: " + errorResponse.toString());
                Log.d("DEBUG: " + getClass().toString(), errorResponse.toString());
            }
        });
        Log.d("DEBUG", "Response: " + "Going out");
    }

    @Override
    protected void fetchTimelineAsync() {

    }

}
