package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;


 /**
 * @Title：FashionDIY
 * @Description：
 * @date 2014-12-16 上午10:08:46
 * @author Administrator
 * @version 1.0
 */

public class MyToastDialog extends Toast{	
	
	private static Toast toast;
	private static final int TOAST_SHOW_TIME = 3000;

	private static Handler mHandler = new Handler();
	
	private static Context mContext;
	
	private static DisplayMetrics screenMetrics = FashionDiyApplication.getInstance().getScreenSize();
		
	 /**
	 * @param context
	 */
	 
	public MyToastDialog(Context context) {
		super(context);
		
		this.mContext = context;
	}
	
	/**
	 * 销毁吐司，防止吐司重复显示
	 */
	private Runnable r = new Runnable() {
		public void run() {
			cancel();
		}
	};

	//显示图片
	public static void showPhotoDialog(String imagePath) {
		
		int width = screenMetrics.widthPixels / 2;
		int height = screenMetrics.heightPixels / 4;
		
		AlertDialog.Builder  builder=new Builder(mContext);
		final AlertDialog dialog = builder.create();
		LayoutParams params=new LayoutParams(-1,-1);
	    ImageView view=new ImageView(mContext);
	    view.setMaxHeight(height);
	    view.setMaxWidth(width);
	    view.setScaleType(ScaleType.FIT_XY);
		BitmapUtil.loadLocalImage(mContext, view, imagePath, width, height);
	    //Bitmap bitmap = FashionDiyApplication.getInstance().getImageLoader().loadImageSync(imagePath, new ImageSize(width, height));
	   // view.setImageBitmap(bitmap);
		view.setLayoutParams(params);
		WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
		lp.width=width;
		lp.height=height;
		dialog.getWindow().setAttributes(lp);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	public void show(String toastContent) {
		mHandler.removeCallbacks(r);
		View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fashiondiy_toast_dialog, null);
		TextView txt = (TextView) view.findViewById(R.id.toast_dialog_content);
		int width = FashionDiyApplication.getInstance().getScreenSize().widthPixels;
		txt.setWidth(width / 3);
		txt.setHeight(width / 3);
		txt.setText(toastContent);
		setDuration(TOAST_SHOW_TIME);
		setGravity(Gravity.CENTER, 0, 0);
		setView(view);
		super.show();
		mHandler.postDelayed(r, TOAST_SHOW_TIME);
	}
	
	public void cancel() {
		super.cancel();
	}
}
