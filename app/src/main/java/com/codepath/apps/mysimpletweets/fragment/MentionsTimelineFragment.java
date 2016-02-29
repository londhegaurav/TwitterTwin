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
 * Created by glondhe on 2/23/16.
 */
public class MentionsTimelineFragment extends TweetListsFragments {


    private TwitterClient client;

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
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
                                       final Context context = getActivity();

                                       @Override
                                       public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                                           Log.d(getClass().toString(), json.toString());
                                           addAll(Tweet.fromJsonArray(json));
                                       }

                                       @Override
                                       public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                           Log.d(getClass().toString(), errorResponse.toString());
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
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                clearAll();
                addAll(Tweet.fromJsonArray(json));
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
            }
        });
    }

}
