package com.codepath.apps.mysimpletweets.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.FollowRecycleAdapter;
import com.codepath.apps.mysimpletweets.filter.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.models.User;

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
    Context context;
    private ArrayList<User> users = null;
    private FollowRecycleAdapter aTweets = null;
    private RecyclerView rvTweets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        this.context = getContext();


        View v = inflater.inflate(R.layout.follow_list, parent, false);
        ButterKnife.bind(this, v);
        rvTweets = rvTimelineGridFollow;

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
                    Log.d("DEBUG", "Calling Refresh");
                    fetchTimelineAsync();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        if (!isNetworkAvailable())
            Toast.makeText(getActivity(), "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();
        else
            aTweets = new FollowRecycleAdapter(getActivity(), users);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void addAll(ArrayList<User> usr) {
        Log.i("DEBUG bodyall", String.valueOf(aTweets.getItemCount()));
        Log.i("DEBUG bodyall", String.valueOf(usr.get(0)));

        users.addAll(usr);
        aTweets.notifyItemInserted(aTweets.getItemCount() - 1);
        aTweets.notifyDataSetChanged();
    }

    public void clearAll() {
        aTweets.clearData();
    }

}

