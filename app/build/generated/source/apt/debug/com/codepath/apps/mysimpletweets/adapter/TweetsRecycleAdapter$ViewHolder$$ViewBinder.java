// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TweetsRecycleAdapter$ViewHolder$$ViewBinder<T extends com.codepath.apps.mysimpletweets.adapter.TweetsRecycleAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624053, "field 'ivProfileImg'");
    target.ivProfileImg = finder.castView(view, 2131624053, "field 'ivProfileImg'");
    view = finder.findRequiredView(source, 2131624097, "field 'ivPostTweet'");
    target.ivPostTweet = finder.castView(view, 2131624097, "field 'ivPostTweet'");
    view = finder.findRequiredView(source, 2131624095, "field 'ivMedia'");
    target.ivMedia = finder.castView(view, 2131624095, "field 'ivMedia'");
    view = finder.findRequiredView(source, 2131624088, "field 'tvUsername'");
    target.tvUsername = finder.castView(view, 2131624088, "field 'tvUsername'");
    view = finder.findRequiredView(source, 2131624094, "field 'tvTweet'");
    target.tvTweet = finder.castView(view, 2131624094, "field 'tvTweet'");
    view = finder.findRequiredView(source, 2131624099, "field 'tvReTweet'");
    target.tvReTweet = finder.castView(view, 2131624099, "field 'tvReTweet'");
    view = finder.findRequiredView(source, 2131624100, "field 'tvStar'");
    target.tvStar = finder.castView(view, 2131624100, "field 'tvStar'");
    view = finder.findRequiredView(source, 2131624098, "field 'ivReTweet'");
    target.ivReTweet = finder.castView(view, 2131624098, "field 'ivReTweet'");
    view = finder.findRequiredView(source, 2131624083, "field 'ivStar'");
    target.ivStar = finder.castView(view, 2131624083, "field 'ivStar'");
  }

  @Override public void unbind(T target) {
    target.ivProfileImg = null;
    target.ivPostTweet = null;
    target.ivMedia = null;
    target.tvUsername = null;
    target.tvTweet = null;
    target.tvReTweet = null;
    target.tvStar = null;
    target.ivReTweet = null;
    target.ivStar = null;
  }
}
