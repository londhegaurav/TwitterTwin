package com.codepath.apps.mysimpletweets.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by glondhe on 2/24/16.
 */
public class UserTimelineFragment extends TweetListsFragments {
    private TwitterClient client;

    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        if (isNetworkAvailable()) {
            populateTimeline();
        }
    }

    // send an API request to get the timeline json
    //Fill the timeline by creating the tweet object from the json
    protected void populateTimeline() {
        client.getUserTimeline(getArguments().getString("screen_name"), new JsonHttpResponseHandler() {
                    final Context context = getActivity();

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        Log.d(getClass().toString(), json.toString());
                        addAll(Tweet.fromJsonArray(json));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG: On failure ", errorResponse.toString());
                    }

                    @Override
                    public void onUserException(Throwable error) {
                        super.onUserException(error);
                        Log.d("DEBUG: User ", error.toString());
                        Toast.makeText(context, "Rate limit Exceeded", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void fetchTimelineAsync() {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        client.getUserTimeline(getArguments().getString("screen_name"),
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        clearAll();
                        addAll(Tweet.fromJsonArray(json));
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
                    }
                });
    }


}
