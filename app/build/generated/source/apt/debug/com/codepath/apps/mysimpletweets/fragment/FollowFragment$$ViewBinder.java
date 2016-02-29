// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FollowFragment$$ViewBinder<T extends com.codepath.apps.mysimpletweets.fragment.FollowFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624085, "field 'rvTimelineGridFollow'");
    target.rvTimelineGridFollow = finder.castView(view, 2131624085, "field 'rvTimelineGridFollow'");
    view = finder.findRequiredView(source, 2131624084, "field 'swipeContainerFollow'");
    target.swipeContainerFollow = finder.castView(view, 2131624084, "field 'swipeContainerFollow'");
  }

  @Override public void unbind(T target) {
    target.rvTimelineGridFollow = null;
    target.swipeContainerFollow = null;
  }
}
