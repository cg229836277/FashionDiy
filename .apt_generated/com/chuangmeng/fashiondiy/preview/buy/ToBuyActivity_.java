//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.chuangmeng.fashiondiy.preview.buy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.chuangmeng.fashiondiy.R.id;
import com.chuangmeng.fashiondiy.R.layout;
import com.chuangmeng.fashiondiy.view.viewflow.CircleFlowIndicator;
import com.chuangmeng.fashiondiy.view.viewflow.ViewFlow;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ToBuyActivity_
    extends ToBuyActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_preview_buy_show);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static ToBuyActivity_.IntentBuilder_ intent(Context context) {
        return new ToBuyActivity_.IntentBuilder_(context);
    }

    public static ToBuyActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ToBuyActivity_.IntentBuilder_(fragment);
    }

    public static ToBuyActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ToBuyActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        design_preview_buy_show_vf = ((ViewFlow) hasViews.findViewById(id.design_preview_buy_show_vf));
        design_buy_cloth_money_tv = ((TextView) hasViews.findViewById(id.design_buy_cloth_money_tv));
        design_buy_cloth_no_s_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_s_bt));
        design_buy_cloth_ok_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_ok_bt));
        design_buy_cloth_num_sub_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_num_sub_bt));
        design_buy_cloth_num_tv = ((TextView) hasViews.findViewById(id.design_buy_cloth_num_tv));
        design_buy_cloth_no_xxxl_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_xxxl_bt));
        design_buy_cloth_no_xxl_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_xxl_bt));
        design_buy_cloth_no_xl_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_xl_bt));
        design_buy_cloth_no_m_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_m_bt));
        design_buy_cloth_no_l_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_no_l_bt));
        design_preview_buy_show_cfi = ((CircleFlowIndicator) hasViews.findViewById(id.design_preview_buy_show_cfi));
        design_buy_cloth_num_add_bt = ((Button) hasViews.findViewById(id.design_buy_cloth_num_add_bt));
        initData();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ToBuyActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ToBuyActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ToBuyActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ToBuyActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

    }

}