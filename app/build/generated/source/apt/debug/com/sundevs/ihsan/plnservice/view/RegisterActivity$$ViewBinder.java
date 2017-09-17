// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.sundevs.ihsan.plnservice.view.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624069, "field 'tamper'");
    target.tamper = finder.castView(view, 2131624069, "field 'tamper'");
    view = finder.findRequiredView(source, 2131624070, "field 'password'");
    target.password = finder.castView(view, 2131624070, "field 'password'");
    view = finder.findRequiredView(source, 2131624071, "field 'upassword'");
    target.upassword = finder.castView(view, 2131624071, "field 'upassword'");
    view = finder.findRequiredView(source, 2131624068, "field 'coordinatorLayout'");
    target.coordinatorLayout = finder.castView(view, 2131624068, "field 'coordinatorLayout'");
    view = finder.findRequiredView(source, 2131624072, "method 'daftar'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.daftar();
        }
      });
  }

  @Override public void unbind(T target) {
    target.tamper = null;
    target.password = null;
    target.upassword = null;
    target.coordinatorLayout = null;
  }
}
