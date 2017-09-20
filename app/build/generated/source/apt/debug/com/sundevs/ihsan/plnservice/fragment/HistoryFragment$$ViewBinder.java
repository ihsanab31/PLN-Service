// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HistoryFragment$$ViewBinder<T extends com.sundevs.ihsan.plnservice.fragment.HistoryFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624096, "field 'coordinatorLayout'");
    target.coordinatorLayout = finder.castView(view, 2131624096, "field 'coordinatorLayout'");
    view = finder.findRequiredView(source, 2131624097, "field 'swipeRefreshLayout'");
    target.swipeRefreshLayout = finder.castView(view, 2131624097, "field 'swipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624098, "field 'listView'");
    target.listView = finder.castView(view, 2131624098, "field 'listView'");
  }

  @Override public void unbind(T target) {
    target.coordinatorLayout = null;
    target.swipeRefreshLayout = null;
    target.listView = null;
  }
}
