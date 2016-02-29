// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TweetListsFragments$$ViewBinder<T extends com.codepath.apps.mysimpletweets.fragment.TweetListsFragments> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624093, "field 'rvTimelineGrid'");
    target.rvTimelineGrid = finder.castView(view, 2131624093, "field 'rvTimelineGrid'");
    view = finder.findRequiredView(source, 2131624063, "field 'swipeContainer'");
    target.swipeContainer = finder.castView(view, 2131624063, "field 'swipeContainer'");
  }

  @Override public void unbind(T target) {
    target.rvTimelineGrid = null;
    target.swipeContainer = null;
  }
}
