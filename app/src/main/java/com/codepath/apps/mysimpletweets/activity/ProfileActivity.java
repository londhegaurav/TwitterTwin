package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.fragment.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.tvFullName) TextView tvName;
    @Bind(R.id.tvTagLine) TextView tvTagLine;
    @Bind(R.id.tvFollowers) TextView tvFollowers;
    @Bind(R.id.tvFollowing) TextView tvFollowing;
    @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
    @Bind(R.id.ivCoverImage) ImageView ivCoverImage;
    @Bind(R.id.tvScreenName) TextView tvScreenName;

    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        String screenName = getIntent().getStringExtra("screen_name");
        client = TwitterApplication.getRestClient();
        client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.screenName);
                    populateProfileHeader(user);
                    Log.d("DEBUG", "Response: " + "Success: " + user.screenName);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", "Response: " + "Failure: " + responseString);
                    Log.d(getClass().toString(), responseString.toString());
                }
            }
        );

        if (savedInstanceState ==null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        tvName.setText(user.getName());
        tvTagLine.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingsCount + " Following");
        tvScreenName.setText("@" + user.screenName);
        tvFollowers.setTag(user.screenName);
        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),FollowActivity.class);
                i.putExtra("screen_name",tvFollowers.getTag().toString());
                startActivity(i);
            }
        });
        Glide.with(this).load(user.profileBannerUrl.replace("normal", "bigger")).fitCenter().into(ivCoverImage);
        Glide.with(this).load(user.profileIamgeURL.replace("normal", "bigger")).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
