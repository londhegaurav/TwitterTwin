package com.codepath.apps.mysimpletweets.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
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
import com.codepath.apps.mysimpletweets.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by glondhe on 2/17/16.
 */

//taking the tweets objects and turing then into view displayed in the recycle view
public class followRecycleAdapter extends RecyclerView.Adapter<followRecycleAdapter.ViewHolder> {

    private final Context context;
    private final FragmentManager fm;
    public ArrayList<User> mTweet = null;
    private String relativeDate;
    private PostsDatabaseHelper databaseHelper;
    private TextView tvUsername;
    private User user = null;
    private Boolean flag = false;
    private int position = -1;
    private ImageButton ivretweet ;
    private ImageButton ivstar ;

    public followRecycleAdapter(Context context, ArrayList<User> users, Boolean flag, FragmentManager fm) {
        this.context = context;
        //databaseHelper = PostsDatabaseHelper.getInstance(this.context);
        Log.d("DEBUG context",this.context.toString() );
        if (flag.equals(true)) {
//            mTweet = new ArrayList<User>();
//            List<User> twt = users.getAll();
//            mTweet.addAll(twt);
        }
        else
            mTweet = users;

        this.fm = fm;
    }

    @Override
    public followRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.follow_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final followRecycleAdapter.ViewHolder viewHolder, final int position) {

        // final ViewHolder viewHolder1 = null;
        this.position = position;
        user = null;
        user = mTweet.get(position);
        viewHolder.tvUsernameFollow.setText(user.name);
        viewHolder.tvScreenNameFollow.setText(user.screenName);
        Glide.with(context).load(user.getProfileIamgeURL().replace("normal", "bigger")).fitCenter().into(viewHolder.ivProfileImageFollow);
        Log.d("cScreenNamefollow", user.screenName);
        Log.d("cScreenNamefollow", user.profileIamgeURL);
        // Log.d("MediaURL: ",tweet.medias.get(0).mediaUrl);

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
        @Bind(R.id.ivProfileImageFollow) ImageView ivProfileImageFollow;
        @Bind(R.id.tvUsernameFollow) TextView tvUsernameFollow;
        @Bind(R.id.tvScreenNameFollow) TextView tvScreenNameFollow;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }

    }
}
