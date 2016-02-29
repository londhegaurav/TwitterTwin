package com.codepath.apps.mysimpletweets.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activity.TimeLineActivity;
import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComposeTweetDailog} interface
 * to handle interaction events.
 * Use the {@link ComposeTweetDailog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeTweetDailog extends DialogFragment {

    private TwitterClient client;
    public ComposeTweetDailog() {}

    @Bind(R.id.tvTweetSize) TextView mTextView;
    @Bind(R.id.etComposeTweet) EditText mEditText;
    @Bind(R.id.tvUsername) TextView tvUsername;
    @Bind(R.id.ivProfileImage2) ImageView ivProfileImage2;
    @Bind(R.id.ibTweetSubmit) Button btSave;
    @Bind(R.id.ibCancel) ImageButton btCancel;

    public static ComposeTweetDailog newInstance(String title) {
        ComposeTweetDailog frag = new ComposeTweetDailog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        String myValue = bundle.getString("screenName");
        client = TwitterApplication.getRestClient();

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View tweetView = inflater.inflate(R.layout.fragment_compose_tweet_dailog, container);
        ButterKnife.bind(this, tweetView);
        getUserInfoAndSet();

        if(myValue!=null){
            myValue = "@"+ myValue;
            mEditText.setText(myValue);
        }

        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforetextchange", String.valueOf(count));
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                Log.d("textchange", String.valueOf(s));

                if (s.length() > 140) {
                    int n = (s.length() - 140) * -1;
                    mTextView.setText(String.valueOf(n));
                    mTextView.setTextColor(Color.RED);
                    btSave.setEnabled(false);
                } else {
                    btSave.setEnabled(true);
                    mTextView.setTextColor(Color.GRAY);
                    mTextView.setText(String.valueOf(s.length()));
                }
            }

            public void afterTextChanged(Editable s) {
                Log.d("aftertextchange", String.valueOf(s));
            }
        };

        mEditText.addTextChangedListener(mTextEditorWatcher);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = String.valueOf(mEditText.getText());
                postStatus(status);

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return tweetView;
    }

    private void postStatus(String status) {

        client.postTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(getClass().toString(), response.toString());
                Tweet tweet = Tweet.fromJson(response);
                Intent intent = new Intent(getActivity(),TimeLineActivity.class);
                tweet.addKey = "true";
                intent.putExtra("tweet", tweet);
                startActivity(intent);

                android.support.v4.app.Fragment newFragment = new TweetListsFragments() {
                    @Override
                    protected void populateTimeline() {

                    }

                    @Override
                    protected void fetchTimelineAsync() {

                    }

                };

                newFragment.setArguments(intent.getExtras());
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.commit();
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("DEBUG", "IN POST failure");
                Log.e(getClass().toString(), responseString);
            }
        }, status);
    }

    private void getUserInfoAndSet() {

        TwitterClient client = TwitterApplication.getRestClient();
        client.getUserInfo(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(getClass().toString(), response.toString());
                User user = User.fromJSON(response);

                tvUsername.setText("\t\t" + user.getName() + "\n" + "@" + user.getScreenName());
                tvUsername.setTextColor(getResources().getColor(R.color.blue));
                Glide.with(getContext()).load(user.getProfileIamgeURL().replace("normal", "bigger")).fitCenter().into(ivProfileImage2);
                mTextView.setTextColor(Color.GRAY);
                mTextView.setText(String.valueOf(0));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(getClass().toString(), responseString.toString());
            }
        });
    }


}
