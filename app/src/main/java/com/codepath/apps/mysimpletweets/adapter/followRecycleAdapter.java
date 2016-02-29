package com.codepath.apps.mysimpletweets.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by glondhe on 2/17/16.
 */

public class FollowRecycleAdapter extends RecyclerView.Adapter<FollowRecycleAdapter.ViewHolder> {

    private final Context context;
    public ArrayList<User> mTweet = null;
    private User user = null;

    public FollowRecycleAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        mTweet = users;
    }

    @Override
    public FollowRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.follow_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FollowRecycleAdapter.ViewHolder viewHolder, final int position) {

        user = null;
        user = mTweet.get(position);
        viewHolder.tvUsernameFollow.setText(user.name);
        viewHolder.tvUsernameFollow.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Black(1).ttf"));
        viewHolder.tvScreenNameFollow.setText("@" + user.screenName);
        Pattern httpPattern2 = Pattern.compile("@[^ \\n]*");
        Linkify.addLinks(viewHolder.tvScreenNameFollow, httpPattern2, "");
        viewHolder.tvScreenNameFollow.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Gotham-Medium(1).ttf"));
        Glide.with(context).load(user.getProfileIamgeURL().replace("normal", "bigger")).fitCenter().into(viewHolder.ivProfileImageFollow);
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
        @Bind(R.id.ivProfileImageFollow)
        ImageView ivProfileImageFollow;
        @Bind(R.id.tvUsernameFollow)
        TextView tvUsernameFollow;
        @Bind(R.id.tvScreenNameFollow)
        TextView tvScreenNameFollow;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }

}
