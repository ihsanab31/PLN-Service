// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.sundevs.ihsan.plnservice.view.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624061, "field 'coordinatorLayout'");
    target.coordinatorLayout = finder.castView(view, 2131624061, "field 'coordinatorLayout'");
    view = finder.findRequiredView(source, 2131624062, "field 'tamper'");
    target.tamper = finder.castView(view, 2131624062, "field 'tamper'");
    view = finder.findRequiredView(source, 2131624063, "field 'pass'");
    target.pass = finder.castView(view, 2131624063, "field 'pass'");
    view = finder.findRequiredView(source, 2131624064, "method 'masuk'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.masuk();
        }
      });
    view = finder.findRequiredView(source, 2131624065, "method 'tanyaregister'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.tanyaregister();
        }
      });
  }

  @Override public void unbind(T target) {
    target.coordinatorLayout = null;
    target.tamper = null;
    target.pass = null;
  }
}
