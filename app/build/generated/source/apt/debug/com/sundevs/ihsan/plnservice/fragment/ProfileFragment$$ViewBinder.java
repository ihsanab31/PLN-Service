// Generated code from Butter Knife. Do not modify!
package com.sundevs.ihsan.plnservice.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ProfileFragment$$ViewBinder<T extends com.sundevs.ihsan.plnservice.fragment.ProfileFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624113, "field 'txtnama'");
    target.txtnama = finder.castView(view, 2131624113, "field 'txtnama'");
    view = finder.findRequiredView(source, 2131624116, "field 'txtalamat'");
    target.txtalamat = finder.castView(view, 2131624116, "field 'txtalamat'");
    view = finder.findRequiredView(source, 2131624117, "field 'txtnohp'");
    target.txtnohp = finder.castView(view, 2131624117, "field 'txtnohp'");
    view = finder.findRequiredView(source, 2131624111, "field 'coordinatorLayout'");
    target.coordinatorLayout = finder.castView(view, 2131624111, "field 'coordinatorLayout'");
    view = finder.findRequiredView(source, 2131624114, "method 'editData'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.editData();
        }
      });
  }

  @Override public void unbind(T target) {
    target.txtnama = null;
    target.txtalamat = null;
    target.txtnohp = null;
    target.coordinatorLayout = null;
  }
}
