package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;


 /**
 * @Title：FashionDIY
 * @Description：
 * @date 2014-12-15 上午9:13:41
 * @author Administrator
 * @version 1.0
 */

public class MyProgressDialog extends Dialog {
	private Context context;
	private static MyProgressDialog customProgressDialog;

	public MyProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);

	}

	/**
	 * 对话框
	 * @date 2014-6-3上午10:54:06
	 * @author hx
	 * @param context
	 * @return
	 */
	public static MyProgressDialog createDialog(Context context) {

		customProgressDialog = new MyProgressDialog(context, R.style.CustomDialog);
//		customProgressDialog.setContentView(R.layout.fashiondiy_progress_dialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		customProgressDialog.setCanceledOnTouchOutside(false);
		customProgressDialog.setCancelable(false);
		return customProgressDialog;

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.fashiondiy_progress_dialog);
		
		int dialogWidth = FashionDiyApplication.getApplicationInstance().screenSize.widthPixels / 3;
		int dialogHeight = FashionDiyApplication.getApplicationInstance().screenSize.widthPixels / 3;
		
		getWindow().setLayout(dialogWidth, dialogHeight);
		
		this.setCanceledOnTouchOutside(false);
	}
	
	/**
	 * 显示对话框
	 */
	@Override
	public void show() {
		if(!isShowing()){
			super.show();
		}		
	}
	
	/**
	 * 关闭对话框
	 * 
	 * @author Administrator
	 * @date 2014-12-15 下午4:06:38
	 */
	@Override
    public void dismiss() {
    	if(isShowing()){
    		super.dismiss();
    	}      	
    }
}
