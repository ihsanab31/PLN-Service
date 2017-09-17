// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SplashScreenActivity$$ViewBinder<T extends com.sundevs.ihsan.plnservice.view.SplashScreenActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624077, "field 'imageView'");
    target.imageView = finder.castView(view, 2131624077, "field 'imageView'");
  }

  @Override public void unbind(T target) {
    target.imageView = null;
  }
}
