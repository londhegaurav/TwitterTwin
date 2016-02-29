// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.mysimpletweets.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ComposeTweetDailog$$ViewBinder<T extends com.codepath.apps.mysimpletweets.fragment.ComposeTweetDailog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624092, "field 'mTextView'");
    target.mTextView = finder.castView(view, 2131624092, "field 'mTextView'");
    view = finder.findRequiredView(source, 2131624090, "field 'mEditText'");
    target.mEditText = finder.castView(view, 2131624090, "field 'mEditText'");
    view = finder.findRequiredView(source, 2131624088, "field 'tvUsername'");
    target.tvUsername = finder.castView(view, 2131624088, "field 'tvUsername'");
    view = finder.findRequiredView(source, 2131624089, "field 'ivProfileImage2'");
    target.ivProfileImage2 = finder.castView(view, 2131624089, "field 'ivProfileImage2'");
    view = finder.findRequiredView(source, 2131624091, "field 'btSave'");
    target.btSave = finder.castView(view, 2131624091, "field 'btSave'");
    view = finder.findRequiredView(source, 2131624087, "field 'btCancel'");
    target.btCancel = finder.castView(view, 2131624087, "field 'btCancel'");
  }

  @Override public void unbind(T target) {
    target.mTextView = null;
    target.mEditText = null;
    target.tvUsername = null;
    target.ivProfileImage2 = null;
    target.btSave = null;
    target.btCancel = null;
  }
}
