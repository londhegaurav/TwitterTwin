package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragment.ComposeTweetDailog;
import com.codepath.apps.mysimpletweets.fragment.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragment.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragment.SearchListsFragment;
import com.codepath.apps.mysimpletweets.interfaces.OnFiltersSaveListener;
import com.codepath.apps.mysimpletweets.models.Tweet;


public class TimeLineActivity extends AppCompatActivity implements OnFiltersSaveListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);


        //First param is number of columns and second param is orientation i.e Vertical or Horizontal
        //StaggeredGridLayoutManager gridLayoutManager =
        //new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //Attach the layout manager to the recycler view


//
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (!isNetworkAvailable()) {
//                    Toast.makeText(TimeLineActivity.this, "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();
//                    swipeContainer.setRefreshing(false);
//                } else {
//                    actionBar.hide();
//                    Log.d("DEBUG", "Calling Refresh");
//                    fetchTimelineAsync();
//                    actionBar.show();
//                }
//            }
//        });

        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//        android.R.color.holo_green_light,
//        android.R.color.holo_orange_light,
//        android.R.color.holo_red_light);
    }
//
//    public void setActionBar() {
//
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.mipmap.twitterimg);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setElevation(0);
//        actionBar.setTitle("TwitterClient");
//    }

public void onProfileView(MenuItem mi){
    Intent i = new Intent(this,ProfileActivity.class);
    startActivity(i);
}

public void callComposeTweet(MenuItem item) {

    FragmentManager fm = getSupportFragmentManager();
    ComposeTweetDailog editNameDialog = ComposeTweetDailog.newInstance("Some Title");
    editNameDialog.show(fm, "fragment_edit_name");
}

public void callMessageList(MenuItem item) {

//    Intent intent = new Intent(this,MessageActivity.class);
//    startActivity(intent);

}


//    @Override
//    public void onFiltersSave(Tweet tweet) {
//        Log.i("DEBUG body", tweet.body);
//
//        TweetListsFragments tweetListsFragments = new TweetListsFragments() {
//            @Override
//            protected void populateTimeline() {
//
//            }
//
//            @Override
//            protected void fetchTimelineAsync() {
//
//            }
//        };
//        tweetListsFragments.addFirst(tweet);
//        //    homeTimeLineFragment.addFirst(tweet);
//        Log.i("DEBUG", "beforefromjson");
//
//        //Toast.makeText(this, "new tweet" + this.tweetText, Toast.LENGTH_LONG).show();
//    }

//    @Override
//    public void onScrollChanged(int i, boolean b, boolean b1) {
//
//    }
//
//    @Override
//    public void onDownMotionEvent() {
//
//    }
//
//    @Override
//    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//
//        if (scrollState == ScrollState.UP) {
//            if (actionBar.isShowing()) {
//                actionBar.hide();
//            }
//        } else if (scrollState == ScrollState.DOWN) {
//            if (!actionBar.isShowing()) {
//                actionBar.hide();
//            }
//        }
//    }


//
//    public void fetchTimelineAsync() {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                //aTweets.clearData();
//                // ...the data has come back, add new items to your adapter...
//                tweets.addAll(Tweet.fromJsonArray(json));
//                //databaseHelper.deleteAllPostsAndUsers();
//                //databaseHelper.deleteAllPostsAndUsers();
//                databaseHelper.addAllPosts(Tweet.fromJsonArray(json));
//                int cnt = databaseHelper.getProfilesCount();
//                Log.d("DEBUG", "cnt" + String.valueOf(cnt));
//                aTweets.notifyItemInserted(cnt - 1);
//                aTweets.notifyDataSetChanged();
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
//            }
//        });
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem messageUser = menu.findItem(R.id.messageUser);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final ImageView msgList = (ImageView) MenuItemCompat.getActionView(messageUser);

//        msgList.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v)
//            {
//                setContentView(R.layout.message_list);
//                Toast.makeText(getApplicationContext(),"in Message", Toast.LENGTH_LONG).show();
//                MessageActivity messageListFragment = MessageActivity.newInstance();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.flContainerMsg, messageListFragment);
//                ft.commit();
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                setContentView(R.layout.search_list);
                SearchListsFragment searchListsFragment = SearchListsFragment.newInstance(query);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContainerSearch, searchListsFragment);
                ft.commit();

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
//       getMenuInflater().inflate(R.menu.menu_timeline, menu);
//        if (!isNetworkAvailable())
//            Toast.makeText(TimeLineActivity.this, "Please check your Internet connectivity.", Toast.LENGTH_LONG).show();
//        else populateTimeline();
//        setActionBar();
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.componseTweet) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFiltersSave(Tweet tweet) {


    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter{
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Home", "Mentions"};

                public TweetsPagerAdapter(FragmentManager fm){
                    super(fm);
                }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimelineFragment();
            } else if (position ==1){
                return new MentionsTimelineFragment();
            } else return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }


}
