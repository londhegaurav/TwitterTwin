
package com.codepath.apps.mysimpletweets.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by glondhe on 2/23/16.
 */
public class FollowersFragment extends FollowFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        if (isNetworkAvailable()) {
            populateTimeline();
        }
    }

    public  static FollowersFragment newInstance(String screen_name ){
        FollowersFragment followersFragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        followersFragment.setArguments(args);
        return followersFragment;
    }

    // send an API request to get the timeline json
    //Fill the timeline by creating the tweet object from the json
    protected void populateTimeline() {
        client.getFollowers(getArguments().getString("screen_name"), new JsonHttpResponseHandler() {
                                final Context context = getActivity();

                    @Override
                    public void onStart() {
                        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
                        Log.d("DEBUG context", context.toString());
                        System.out.println("Test this first");
                    }
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                                    Log.d(getClass().toString(), json.toString());
                                    System.out.println("Test this");
                                    //json here
                                    // desrialize json
                                    // create models and add them to adapter
                                    // load the model data into listview
                                    //databaseHelper.deleteAllPostsAndUsers();
                                    //databaseHelper.addAllPosts(Tweet.fromJsonArray(json));
                                    //tw = new ArrayList<Tweet>(databaseHelper.getAllPosts());
                                    //tweets = tw;
                                    Log.d("DEBUG:", json.toString());
                                    ArrayList<User> t = User.getArrayList(json);

                                    Log.d("DEBUG bodyall8999",t.get(0).screenName);
                                    //ArrayList<User> userArray =  user.getUsers();
                                    addAll(User.getArrayList(json));
//                   String tweetTableName = Cache.getTableInfo(Tweet.class).getTableName();
                                    //  String userTableName = Cache.getTableInfo(User.class).getTableName();
//                   List<Tweet> importantItems =
//                           SQLiteUtils.rawQuery(Tweet.class,
//                                   "SELECT * from " + tweetTableName,
//                                           //+ " left outer join " + userTableName + " on " + tweetTableName + ".id = " + userTableName + ".id;",
//                                   new String[]{"high"});
                                    // Log.d("DEBUG_TWeetSize", String.valueOf(importantItems.size()));
                                    //int cnt = databaseHelper.getProfilesCount();
                                    //tweets.addAll(Tweet.fromJsonArray(json));
                                    //aTweets.clearData();
                                    //aTweets.notifyItemInserted(aTweets.getItemCount() - 1);
                                    //aTweets.notifyDataSetChanged();


                                    //               swipeContainer.setRefreshing(false);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    Log.d("DEBUG: On failure ", errorResponse.toString());
                                }

                                @Override
                                public void onUserException(Throwable error) {
                                    super.onUserException(error);
                                    Log.d("DEBUG: User ", error.toString());
                                    //                                       flag = true;
                                    //                                       //aTweets.notifyItemInserted(aTweets.getItemCount() - 1);
                                    //                                       aTweets.notifyDataSetChanged();
                                    Toast.makeText(context, "Rate limit Exceeded", Toast.LENGTH_LONG).show();
                                }
                            }
        );
    }

    public void fetchTimelineAsync() {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        client.getFollowers(getArguments().getString("screen_name"), new JsonHttpResponseHandler() {
            final Context context = getActivity();
            @Override

            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
              // Remember to CLEAR OUT old items before appending in the new ones
              //aTweets.clearData();
              // ...the data has come back, add new items to your adapter...
//                tweets.addAll(Tweet.fromJsonArray(json));
//                //databaseHelper.deleteAllPostsAndUsers();
//                //databaseHelper.deleteAllPostsAndUsers();
//                databaseHelper.addAllPosts(Tweet.fromJsonArray(json));
//                int cnt = databaseHelper.getProfilesCount();
//                Log.d("DEBUG", "cnt" + String.valueOf(cnt));
//                aTweets.notifyItemInserted(cnt - 1);
//                aTweets.notifyDataSetChanged();
              clearAll();
              User user = User.parseJSON(json.toString());
              ArrayList<User> userArray = user.getUsers();
              addAll(userArray);
              // Now we call setRefreshing(false) to signal refresh has finished
              swipeContainerFollow.setRefreshing(false);
          }

          @Override
          public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
              Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
          }
      });
    }


}
