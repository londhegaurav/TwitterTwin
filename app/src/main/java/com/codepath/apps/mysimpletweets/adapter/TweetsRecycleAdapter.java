package com.codepath.apps.mysimpletweets.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.DB.PostsDatabaseHelper;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activity.DetailActivity;
import com.codepath.apps.mysimpletweets.activity.ProfileActivity;
import com.codepath.apps.mysimpletweets.application.TwitterApplication;
import com.codepath.apps.mysimpletweets.application.TwitterClient;
import com.codepath.apps.mysimpletweets.fragment.ComposeTweetDailog;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by glondhe on 2/17/16.
 */

//taking the tweets objects and turing then into view displayed in the recycle view
public class TweetsRecycleAdapter extends RecyclerView.Adapter<TweetsRecycleAdapter.ViewHolder> {

    private final Context context;
    private final FragmentManager fm;
    public ArrayList<Tweet> mTweet = null;
    private String relativeDate;
    private PostsDatabaseHelper databaseHelper;
    private TextView tvUsername;
    private Tweet tweet = null;
    private Boolean flag = false;
    private int position = -1;
    private ImageButton ivretweet ;
    private ImageButton ivstar ;

    public TweetsRecycleAdapter(Context context, ArrayList<Tweet> tweets, Boolean flag, FragmentManager fm) {
        this.context = context;
        //databaseHelper = PostsDatabaseHelper.getInstance(this.context);
        if (flag.equals(true)) {
            mTweet = new ArrayList<Tweet>();
            List<Tweet> twt = Tweet.getAll();
            mTweet.addAll(twt);
        }
        else
            mTweet = tweets;

        this.fm = fm;
    }

    @Override
    public TweetsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TweetsRecycleAdapter.ViewHolder viewHolder, final int position) {

       // final ViewHolder viewHolder1 = null;
        this.position = position;
        tweet = null;
        tweet = mTweet.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = mTweet.get(position);
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                //you can pass on the Pojo with PARCELABLE
                intent.putExtra("tweet", tweet);
                v.getContext().startActivity(intent);
            }
        });

        TextView tvTweet = viewHolder.tvTweet;
        tvUsername = viewHolder.tvUsername;
        ImageView ivProfileImg = viewHolder.ivProfileImg;
        ivProfileImg.setImageDrawable(null);
        tvUsername.setText(null);
        tvTweet.setText(tweet.body);
        tvTweet.setTextColor(Color.parseColor("#000000"));
        tvTweet.setTextSize(12);
        tvTweet.setLinksClickable(true);
        Pattern httpPattern = Pattern.compile("[a-z]+:\\/\\/[^ \\n]*");
        Linkify.addLinks(tvTweet, httpPattern, "");

        Log.d("cScreenName", tweet.user.screenName);
        Log.d("cScreenName", tweet.user.profileIamgeURL);
        Log.d("cScreenName", tweet.body);
       // Log.d("MediaURL: ",tweet.medias.get(0).mediaUrl);

        if(tweet.medias != null){
            Log.d("MediaURL: ",tweet.medias.get(0).mediaUrl);
            ImageView ivMedia = viewHolder.ivMedia;
            Glide.with(context).load(tweet.medias.get(0).mediaUrl.replace("normal", "bigger")).into(ivMedia);
        }

        tvUsername = viewHolder.tvUsername;
        String date = tweet.createdAt;
        relativeDate = getRelativeTimeAgo(date);
        tvUsername.setText(tweet.user.name + " " + "@" + tweet.user.screenName + " * " + relativeDate);
        tvUsername.setTextSize(14);
        tvUsername.setLinksClickable(true);
        Pattern httpPattern2 = Pattern.compile("@[^ \\n]*");
        Linkify.addLinks(tvUsername, httpPattern2, "");

        final TextView retweet = viewHolder.tvReTweet;
        final TextView tvStar = viewHolder.tvStar;
        Log.d("retweet", String.valueOf(tweet.reTweetCnt));
        retweet.setText(String.valueOf(tweet.reTweetCnt));
        tvStar.setText(String.valueOf(tweet.favCnt));

        final ImageView ivPostTweet = viewHolder.ivPostTweet;

        viewHolder.ivProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = mTweet.get(position);
                Intent i = new Intent(v.getContext(),ProfileActivity.class);
                i.putExtra("screen_name", tweet.user.screenName);
                v.getContext().startActivity(i);
            }
        });

        viewHolder.ivPostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = mTweet.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("screenName", tweet.user.screenName );
                ComposeTweetDailog editNameDialog = ComposeTweetDailog.newInstance("Some Title");
                editNameDialog.setArguments(bundle);
                editNameDialog.show(fm, "fragment_edit_name");
            }
        });

        ivretweet = viewHolder.ivReTweet;
        ivstar = viewHolder.ivStar;

        ivretweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet = mTweet.get(position);
                final int t;
                if (!ivretweet.isSelected()) {
                    tweet = mTweet.get(position);
                    Long id = tweet.uid;

                    TwitterClient client = TwitterApplication.getRestClient();
                    client.updateRetweet(id, new JsonHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(getClass().toString(), response.toString());
                            Tweet tweet = Tweet.fromJson(response);
                            viewHolder.ivReTweet.setImageResource(R.drawable.retweetorange);
                            retweet.setText(String.valueOf(tweet.reTweetCnt));
                            tvUsername.setText("\t\t" + tweet.user.getName() + "\n" + "@" + tweet.user.getScreenName());
                            Log.d("DEBUG", "Response - Success" + "RetweetOriginal: "+ tweet.reTweetCnt + "RetweetNew: " + tweet.reTweetCnt);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("DEBUG", "Response - Failure" + "RetweetOriginal: "+ tweet.reTweetCnt + "RetweetNew: " + tweet.reTweetCnt);
                            Log.d(getClass().toString(), responseString.toString());
                        }
                    });

                } else {
                    ivretweet.setImageResource(R.drawable.retweetgray);
                    t = Integer.valueOf(String.valueOf(retweet.getText())) - 1;
                    retweet.setText(String.valueOf(t));
                }
            }
        });

        ivstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int t;
                if (!ivstar.isSelected()) {
                    ivstar.setSelected(true);
                    tweet = mTweet.get(position);
                    viewHolder.ivStar.setImageResource(R.drawable.starorange);
                    t = Integer.valueOf(String.valueOf(tvStar.getText())) + 1;
                    tvStar.setText(String.valueOf(t));

                } else {
                    ivstar.setSelected(false);
                    ivstar.setImageResource(R.drawable.starnocolor);
                   // t = Integer.valueOf(String.valueOf(tvStar.getText())) - 1;
                   // tvStar.setText(String.valueOf(t));
                }
            }
        });

        Glide.with(context).load(tweet.user.profileIamgeURL.replace("normal", "bigger")).into(ivProfileImg);

    }

    public static String getRelativeTimeAgo(String rawJsonDate) {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        relativeDate = relativeDate.replace("hours", "h");
        relativeDate = relativeDate.replace("minutes", "m");
        relativeDate = relativeDate.replace("minute", "m");
        relativeDate = relativeDate.replace("seconds", "s");
        relativeDate = relativeDate.replace("ago", "");
        relativeDate = relativeDate.replace("in", "");
        return relativeDate;
    }

    public static String getTime(String rawJsonDate) {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";

        Date date = null;
        try {
            date = sf.parse(rawJsonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String afterTwitterFormat = "h:mm a EEE, MMM d, ''yy";
        SimpleDateFormat sfi = new SimpleDateFormat(afterTwitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        relativeDate = sfi.format(date);

        return relativeDate;
    }

    public void clearData() {
        int size = this.mTweet.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mTweet.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mTweet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivProfileImage) ImageView ivProfileImg;
        @Bind(R.id.ivPostTweet) ImageView ivPostTweet;
        @Bind(R.id.ivMedia)  ImageView ivMedia;
        @Bind(R.id.tvUsername) TextView tvUsername;
        @Bind(R.id.tvTweet) TextView tvTweet;
        @Bind(R.id.tvreTweet) TextView tvReTweet;
        @Bind(R.id.tvStar) TextView tvStar;
        @Bind(R.id.ivretweet) ImageButton ivReTweet;
        @Bind(R.id.ivstar) ImageButton ivStar;


//        ImageView ivProfileImg;
//        TextView tvUsername;
//        TextView tvTweet;
//        ImageView ivMedia;
//        TextView tvReTweet;
//        TextView tvStar;
//        ImageButton ivReTweet;
//        ImageButton ivStar;
//        ImageView ivPostTweet;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
            //            ivProfileImg = (ImageView) itemView.findViewById(R.id.ivProfileImage);
//            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
//            tvTweet = (TextView) itemView.findViewById(R.id.tvTweet);
//            ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);
//            tvReTweet = (TextView) itemView.findViewById(R.id.tvreTweet);
//            tvStar = (TextView) itemView.findViewById(R.id.tvStar);
//            ivReTweet = (ImageButton) itemView.findViewById(R.id.ivretweet);
//            ivStar = (ImageButton) itemView.findViewById(R.id.ivstar);
//            ivPostTweet = (ImageView) itemView.findViewById(R.id.ivPostTweet);

        }

    }
}