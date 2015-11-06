package com.chuangmeng.fashiondiy.base;

import java.util.ArrayList;
import com.chuangmeng.fashiondiy.CrashHandler;
import com.chuangmeng.fashiondiy.util.IsListNotNull;
import com.chuangmeng.fashiondiy.util.SavePictureBean;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

/**
 * @Title：FashionDIY
 * @Description：
 * @date 2014-10-29 上午9:21:50
 * @author chengang
 * @version 1.0
 */

public class FashionDiyApplication extends Application {

	public DisplayMetrics screenSize;
	public static FashionDiyApplication instance;
	public Bitmap positiveViewBitmap;// 正面预览图
	public Bitmap negativeViewBitmap;// 反面预览图
	public Bitmap femalePositiveBitmap;
	public Bitmap femaleNegativeBitmap;
	private ArrayList<Bitmap> bitmaplist;
	
	private SavePictureBean tryWearBeanData;	

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CrashHandler crashHandler = CrashHandler.getInstance(); 
        crashHandler.init(getApplicationContext()); 
	}

	public static FashionDiyApplication getInstance() {
		synchronized (instance) {
			return instance;
		}		
	}

	/**
	 * 获取屏幕尺寸
	 * 
	 * @author chengang
	 * @date 2014-10-29 上午9:26:14
	 * @return
	 */
	public DisplayMetrics getScreenSize() {
		return screenSize;
	}

	/**
	 * 设置屏幕尺寸
	 * 
	 * @author chengang
	 * @date 2014-10-29 上午9:26:29
	 * @param screenSize
	 */
	public void setScreenSize(DisplayMetrics screenSize) {
		this.screenSize = screenSize;
	}

	public Bitmap getPositiveViewBitmap() {
		return positiveViewBitmap;
	}

	public void setPositiveViewBitmap(Bitmap positiveViewBitmap) {
		this.positiveViewBitmap = positiveViewBitmap;
	}

	public Bitmap getNegativeViewBitmap() {
		return negativeViewBitmap;
	}

	public void setNegativeViewBitmap(Bitmap negativeViewBitmap) {
		this.negativeViewBitmap = negativeViewBitmap;
	}

	public Bitmap getFemalePositiveBitmap() {
		return femalePositiveBitmap;
	}

	public Bitmap getFemaleNegativeBitmap() {
		return femaleNegativeBitmap;
	}

	public void setFemalePositiveBitmap(Bitmap femalePositiveBitmap) {
		this.femalePositiveBitmap = femalePositiveBitmap;
	}

	public void setFemaleNegativeBitmap(Bitmap femaleNegativeBitmap) {
		this.femaleNegativeBitmap = femaleNegativeBitmap;
	}

	public ArrayList<Bitmap> getBitmaps() {
		if(IsListNotNull.isListNotNull(bitmaplist)){
			return bitmaplist;
		}
		return null;
	}

	public void setBitmaps(ArrayList<Bitmap> bitmaps) {
		bitmaplist = bitmaps;
	}
	
	public void clearBitmapArray(){
		if(IsListNotNull.isListNotNull(bitmaplist)){
			bitmaplist.clear();
			bitmaplist = null;				
		}
	}
	
	public SavePictureBean getTryWearBeanData() {
		return tryWearBeanData;
	}

	public void setTryWearBeanData(SavePictureBean tryWearBeanData) {
		this.tryWearBeanData = tryWearBeanData;
	}
}
