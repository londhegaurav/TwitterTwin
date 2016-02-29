package com.codepath.apps.mysimpletweets.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.TweetsRecycleAdapter;
import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.fragment.ComposeTweetDailog;
import com.codepath.apps.mysimpletweets.fragment.SearchListsFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by glondhe on 2/20/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.tvTweetDetail)
    TextView tvTweetDetail;
    @Bind(R.id.tvUsernameDetail)
    TextView tvUsernameDetail;
    @Bind(R.id.ivProfileImageDetail)
    ImageView ivProfileImageDetail;
    @Bind(R.id.ivMediaDetail)
    ImageView ivMediaDetail;
    @Bind(R.id.tvTimeDetail)
    TextView tvTimeDetail;
    @Bind(R.id.tvFavContsDetail)
    TextView tvFavContsDetail;
    @Bind(R.id.tvRetweetsDetail)
    TextView tvRetweetsDetail;
    @Bind(R.id.ivRetweetDetail)
    ImageView ivRetweetDetail;
    @Bind(R.id.ivStarDetail)
    ImageView ivStarDetail;
    @Bind(R.id.ivPostTweetDetail)
    ImageView ivPostTweetDetail;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String relativeDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final Tweet tweet = bundle.getParcelable("tweet");
        tvTweetDetail.setText(tweet.body);
        tvTweetDetail.setTextColor(Color.parseColor("#000000"));
        tvTweetDetail.setTextSize(12);
        tvTweetDetail.setLinksClickable(true);
        Pattern httpPattern = Pattern.compile("[a-z]+:\\/\\/[^ \\n]*");
        Linkify.addLinks(tvTweetDetail, httpPattern, "");

        relativeDate = TweetsRecycleAdapter.getRelativeTimeAgo(tweet.createdAt);
        tvUsernameDetail.setText(tweet.user.name + " " + "@" + tweet.user.screenName + " * " + relativeDate);
        tvUsernameDetail.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Gotham-Black(1).ttf"));
        tvUsernameDetail.setTextSize(14);
        tvUsernameDetail.setLinksClickable(true);
        Pattern httpPattern2 = Pattern.compile("@[^ \\n]*");
        Linkify.addLinks(tvUsernameDetail, httpPattern2, "");

        String dateTime = TweetsRecycleAdapter.getTime(tweet.createdAt);
        tvTimeDetail.setText(dateTime);
        tvTimeDetail.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Gotham-Light(1).ttf"));
        tvTimeDetail.setTextSize(10);

        Glide.with(this).load(tweet.user.profileIamgeURL.replace("normal", "bigger")).into(ivProfileImageDetail);
        if (tweet.medias != null) {
            Log.d("MediaURL: ", tweet.medias.get(0).mediaUrl);
            Glide.with(this).load(tweet.medias.get(0).mediaUrl.replace("normal", "bigger")).into(ivMediaDetail);
        }
        Log.d("DetailActivity", tweet.user.screenName);
        Log.d("DetailActivity", tweet.user.name);
        Log.d("DetailActivity", tweet.body);
        Log.d("DetailActivity", tweet.createdAt);
        Log.d("DetailActivity", tweet.user.profileIamgeURL);

        ivPostTweetDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm;
                fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("screenName", tweet.user.screenName);
                ComposeTweetDailog editNameDialog = ComposeTweetDailog.newInstance("Some Title");
                editNameDialog.setArguments(bundle);
                editNameDialog.show(fm, "fragment_edit_name");
            }
        });

        tvRetweetsDetail.setText(String.valueOf(tweet.reTweetCnt + " RETWEETS"));
        tvRetweetsDetail.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Gotham-Black(1).ttf"));
        tvRetweetsDetail.setTextSize(12);
        tvFavContsDetail.setText(String.valueOf(tweet.favCnt + " FAVORITES"));
        tvFavContsDetail.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Gotham-Black(1).ttf"));
        tvFavContsDetail.setTextSize(12);

        ivRetweetDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final int t;
            if (!ivRetweetDetail.isSelected()) {

                TwitterClient client = TwitterApplication.getRestClient();
                client.updateRetweet(tweet.uid, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d(getClass().toString(), response.toString());
                        Tweet tweet = Tweet.fromJson(response);
                        ivRetweetDetail.setImageResource(R.drawable.retweetorange);
                        tvRetweetsDetail.setText(String.valueOf(tweet.reTweetCnt + " RETWEETS"));
                        Log.d("DEBUG", "Response - Success" + "RetweetOriginal: " + tweet.reTweetCnt + "RetweetNew: " + tweet.reTweetCnt);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("DEBUG", "Response - Failure" + "RetweetOriginal: " + tweet.reTweetCnt + "RetweetNew: " + tweet.reTweetCnt);
                        Log.d(getClass().toString(), responseString.toString());
                    }
                });

            } else {
                ivRetweetDetail.setImageResource(R.drawable.retweetgray);
                t = Integer.valueOf(String.valueOf(tvRetweetsDetail.getText())) - 1;
                tvRetweetsDetail.setText(String.valueOf(t));
            }
            }
        });

        ivStarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!ivStarDetail.isSelected()) {
                ivStarDetail.setSelected(true);
                ivStarDetail.setImageResource(R.drawable.starorange);
                tvFavContsDetail.setText(tweet.favCnt + 1 + " FAVORITES");

            } else {
                ivStarDetail.setSelected(false);
                ivStarDetail.setImageResource(R.drawable.starnocolor);
            }
            }
        });

        if (savedInstanceState == null) {
            SearchListsFragment fragmentUserTimeline = SearchListsFragment.newInstance(tweet.user.screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainerDetail, fragmentUserTimeline);
            ft.commit();
        }

    }
}
