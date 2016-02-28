// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class followRecycleAdapter$ViewHolder$$ViewBinder<T extends com.codepath.apps.mysimpletweets.adapter.followRecycleAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624079, "field 'ivProfileImageFollow'");
    target.ivProfileImageFollow = finder.castView(view, 2131624079, "field 'ivProfileImageFollow'");
    view = finder.findRequiredView(source, 2131624080, "field 'tvUsernameFollow'");
    target.tvUsernameFollow = finder.castView(view, 2131624080, "field 'tvUsernameFollow'");
    view = finder.findRequiredView(source, 2131624081, "field 'tvScreenNameFollow'");
    target.tvScreenNameFollow = finder.castView(view, 2131624081, "field 'tvScreenNameFollow'");
  }

  @Override public void unbind(T target) {
    target.ivProfileImageFollow = null;
    target.tvUsernameFollow = null;
    target.tvScreenNameFollow = null;
  }
}
