// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ProfileActivity$$ViewBinder<T extends com.codepath.apps.mysimpletweets.activity.ProfileActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624054, "field 'tvName'");
    target.tvName = finder.castView(view, 2131624054, "field 'tvName'");
    view = finder.findRequiredView(source, 2131624056, "field 'tvTagLine'");
    target.tvTagLine = finder.castView(view, 2131624056, "field 'tvTagLine'");
    view = finder.findRequiredView(source, 2131624059, "field 'tvFollowers'");
    target.tvFollowers = finder.castView(view, 2131624059, "field 'tvFollowers'");
    view = finder.findRequiredView(source, 2131624060, "field 'tvFollowing'");
    target.tvFollowing = finder.castView(view, 2131624060, "field 'tvFollowing'");
    view = finder.findRequiredView(source, 2131624053, "field 'ivProfileImage'");
    target.ivProfileImage = finder.castView(view, 2131624053, "field 'ivProfileImage'");
    view = finder.findRequiredView(source, 2131624052, "field 'ivCoverImage'");
    target.ivCoverImage = finder.castView(view, 2131624052, "field 'ivCoverImage'");
    view = finder.findRequiredView(source, 2131624055, "field 'tvScreenName'");
    target.tvScreenName = finder.castView(view, 2131624055, "field 'tvScreenName'");
  }

  @Override public void unbind(T target) {
    target.tvName = null;
    target.tvTagLine = null;
    target.tvFollowers = null;
    target.tvFollowing = null;
    target.ivProfileImage = null;
    target.ivCoverImage = null;
    target.tvScreenName = null;
  }
}
