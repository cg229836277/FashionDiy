package com.chuangmeng.fashiondiy.base;

import com.chuangmeng.fashiondiy.view.MyProgressDialog;
import com.chuangmeng.fashiondiy.view.MyToastDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * 
 * @Title：FashionDIY
 * @Description：fragment的基础父类
 * @date 2014-10-31 上午11:14:32
 * @author chengang
 * @version 1.0
 */
public class BaseFragmentActivity extends FragmentActivity {
	public LinearLayout loadingPanelBase;
	public static DisplayMetrics screenMetric; 
	public MyProgressDialog progressDialog;
	public MyToastDialog toastDialog;
	
	public FashionDiyApplication appInstance;
	/*
	 * private Button back_bt; private Button back_nav_bt; private LinearLayout
	 * float1_lv; private LinearLayout float2_lv; private LinearLayout
	 * float3_lv; private LinearLayout float4_lv; private LinearLayout
	 * float5_lv; private PopupWindow popupWindow;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initScreenSize();
		
		appInstance = FashionDiyApplication.getInstance();
		
		progressDialog = MyProgressDialog.createDialog(BaseFragmentActivity.this);
		toastDialog = new MyToastDialog(BaseFragmentActivity.this);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	protected void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		MobclickAgent.onPause(this);
	}
	
	/**
	 * 设置屏幕的尺寸
	 * 
	 * @author chengang
	 * @date 2014-10-29 上午9:42:26
	 */
	public void initScreenSize(){
		screenMetric = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(screenMetric);
        if(screenMetric != null){
        	FashionDiyApplication.getInstance().setScreenSize(screenMetric);  
        }
	}
}
