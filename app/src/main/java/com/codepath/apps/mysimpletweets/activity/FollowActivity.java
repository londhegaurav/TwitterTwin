package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragment.FollowersFragment;

/**
 * Created by glondhe on 2/28/16.
 */
public class FollowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        String screenName = getIntent().getStringExtra("screen_name");
        if(savedInstanceState == null) {
            FollowersFragment followersFragment = FollowersFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainerFollow, followersFragment);
            ft.commit();
        }
    }

}

