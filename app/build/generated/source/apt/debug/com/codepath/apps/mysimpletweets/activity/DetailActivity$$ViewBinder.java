// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailActivity$$ViewBinder<T extends com.codepath.apps.mysimpletweets.activity.DetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624066, "field 'tvTweetDetail'");
    target.tvTweetDetail = finder.castView(view, 2131624066, "field 'tvTweetDetail'");
    view = finder.findRequiredView(source, 2131624065, "field 'tvUsernameDetail'");
    target.tvUsernameDetail = finder.castView(view, 2131624065, "field 'tvUsernameDetail'");
    view = finder.findRequiredView(source, 2131624064, "field 'ivProfileImageDetail'");
    target.ivProfileImageDetail = finder.castView(view, 2131624064, "field 'ivProfileImageDetail'");
    view = finder.findRequiredView(source, 2131624067, "field 'ivMediaDetail'");
    target.ivMediaDetail = finder.castView(view, 2131624067, "field 'ivMediaDetail'");
    view = finder.findRequiredView(source, 2131624068, "field 'tvTimeDetail'");
    target.tvTimeDetail = finder.castView(view, 2131624068, "field 'tvTimeDetail'");
    view = finder.findRequiredView(source, 2131624071, "field 'tvFavContsDetail'");
    target.tvFavContsDetail = finder.castView(view, 2131624071, "field 'tvFavContsDetail'");
    view = finder.findRequiredView(source, 2131624070, "field 'tvRetweetsDetail'");
    target.tvRetweetsDetail = finder.castView(view, 2131624070, "field 'tvRetweetsDetail'");
    view = finder.findRequiredView(source, 2131624075, "field 'ivRetweetDetail'");
    target.ivRetweetDetail = finder.castView(view, 2131624075, "field 'ivRetweetDetail'");
    view = finder.findRequiredView(source, 2131624076, "field 'ivStarDetail'");
    target.ivStarDetail = finder.castView(view, 2131624076, "field 'ivStarDetail'");
    view = finder.findRequiredView(source, 2131624074, "field 'ivPostTweetDetail'");
    target.ivPostTweetDetail = finder.castView(view, 2131624074, "field 'ivPostTweetDetail'");
  }

  @Override public void unbind(T target) {
    target.tvTweetDetail = null;
    target.tvUsernameDetail = null;
    target.ivProfileImageDetail = null;
    target.ivMediaDetail = null;
    target.tvTimeDetail = null;
    target.tvFavContsDetail = null;
    target.tvRetweetsDetail = null;
    target.ivRetweetDetail = null;
    target.ivStarDetail = null;
    target.ivPostTweetDetail = null;
  }
}
