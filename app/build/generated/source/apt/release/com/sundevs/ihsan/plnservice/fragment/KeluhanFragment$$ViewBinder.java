// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class KeluhanFragment$$ViewBinder<T extends com.sundevs.ihsan.plnservice.fragment.KeluhanFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624098, "field 'coordinatorLayout'");
    target.coordinatorLayout = finder.castView(view, 2131624098, "field 'coordinatorLayout'");
    view = finder.findRequiredView(source, 2131624100, "field 'radioGroup'");
    target.radioGroup = finder.castView(view, 2131624100, "field 'radioGroup'");
    view = finder.findRequiredView(source, 2131624105, "field 'lainnya'");
    target.lainnya = finder.castView(view, 2131624105, "field 'lainnya'");
    view = finder.findRequiredView(source, 2131624107, "field 'imageView' and method 'ambilfoto'");
    target.imageView = finder.castView(view, 2131624107, "field 'imageView'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.ambilfoto();
        }
      });
    view = finder.findRequiredView(source, 2131624109, "method 'ajukankeluhan'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.ajukankeluhan();
        }
      });
  }

  @Override public void unbind(T target) {
    target.coordinatorLayout = null;
    target.radioGroup = null;
    target.lainnya = null;
    target.imageView = null;
  }
}
