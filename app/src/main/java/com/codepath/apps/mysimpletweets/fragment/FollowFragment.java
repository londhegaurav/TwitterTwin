

package com.codepath.apps.mysimpletweets.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.DB.PostsDatabaseHelper;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.followRecycleAdapter;
import com.codepath.apps.mysimpletweets.filter.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.models.User;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by glondhe on 2/23/16.
 */

public abstract class FollowFragment extends Fragment {
    @Bind(R.id.rvTimelineGridFollow)
    RecyclerView rvTimelineGridFollow;
    @Bind(R.id.swipeContainerFollow)
    SwipeRefreshLayout swipeContainerFollow;
//    @Bind(R.id.ivProfileImageDetail) ImageView ivProfileImageDetail;

    private ArrayList<User> users = null;
    private ArrayList<User> tw;
    private followRecycleAdapter aTweets = null;
    private RecyclerView rvTweets;
    private String tweetText;
    private JSONObject jsonTweet;
    private ActionBar actionBar;
    private PostsDatabaseHelper databaseHelper;
    private boolean flag = false;
    private FragmentManager fm;

    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        this.context = getContext();


        View v = inflater.inflate(R.layout.follow_list,parent, false);
        ButterKnife.bind(this, v);
        rvTweets = rvTimelineGridFollow;
        // rvTweets = (RecyclerView) v.findViewById(R.id.rvTimelineGrid);

        rvTweets.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getActivity(), "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();

                } else {
                    Log.d("DEBUG", "Loading more API calls");
                    populateTimeline();
                }
            }
        });


        // Setup refresh listener which triggers new data loading
        swipeContainerFollow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getActivity(), "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();
                    swipeContainerFollow.setRefreshing(false);
                } else {
//                    actionBar.hide();
                    Log.d("DEBUG", "Calling Refresh");
                    fetchTimelineAsync();
//                    actionBar.show();
                }
            }
        });

        //Configure the refreshing colors
        swipeContainerFollow.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }

    protected abstract void populateTimeline();

    protected abstract void fetchTimelineAsync();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(getActivity().toString().equalsIgnoreCase("TimeLineActivity")) {
//            Bundle b = getArguments();
//            if (b != null) {
//                Log.d("DEBUG", b.size() + "");
//                Tweet t = b.getParcelable("tweet");
//                Log.d("DEBUG", "Got arraylist");
//
//                if (t.addKey.toString().equalsIgnoreCase("true"))
//                    addFirst(t);
//            } else {
//                Log.d("DEBUG", "Null");
//            }
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        fm = getActivity().getSupportFragmentManager();
        //   actionBar = getSupportActionBar();
        //   setActionBar();
        // databaseHelper = PostsDatabaseHelper.getInstance(getActivity());
        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();
            flag = true;
        }
        if (flag == true)
            aTweets = new followRecycleAdapter(getActivity(), users, true, fm );
        else
            aTweets = new followRecycleAdapter(getActivity(), users, false, fm);

    }



    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    @Override
//    public void onFiltersSave(Tweet tweet) {
//        Log.i("DEBUG body", tweet.body);
//
//
//        addFirst(tweet);
//        //    homeTimeLineFragment.addFirst(tweet);
//        Log.i("DEBUG", "beforefromjson");
//
//        //Toast.makeText(this, "new tweet" + this.tweetText, Toast.LENGTH_LONG).show();
//    }



    public void addAll(ArrayList<User> usr){
        Log.i("DEBUG bodyall", String.valueOf(aTweets.getItemCount()));
        Log.i("DEBUG bodyall", String.valueOf(usr.get(0)));

        users.addAll(usr);
        aTweets.notifyItemInserted(aTweets.getItemCount() - 1);
        aTweets.notifyDataSetChanged();
    }

    public void clearAll(){
        aTweets.clearData();
    }

    public void addFirst(User user){

        // Log.i("DEBUG Tweet", String.valueOf(tweets.indexOf(0)));
//        Log.i("DEBUG jsonstring", json.toString());
//        Tweet tweet = Tweet.fromJson(json);
        //   tweets = new ArrayList<>();
        Log.i("DEBUG body2", user.screenName);
//        aTweets.onViewAttachedToWindow().insert()
        try {
            users.add(0, user);
        }catch (Exception e){
            Log.d(getClass().toString(),e.toString());
        }

        aTweets.notifyItemInserted(aTweets.getItemCount() - 1);
        aTweets.notifyDataSetChanged();
    }
}

