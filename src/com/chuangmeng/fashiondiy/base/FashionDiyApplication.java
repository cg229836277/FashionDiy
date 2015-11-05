package com.chuangmeng.fashiondiy.base;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.chuangmeng.fashiondiy.CrashHandler;
import com.chuangmeng.fashiondiy.util.IsListNotNull;
import com.chuangmeng.fashiondiy.util.SavePictureBean;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

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
	
	ImageLoader imageLoader;	

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CrashHandler crashHandler = CrashHandler.getInstance(); 
        crashHandler.init(getApplicationContext()); 
        
        initImageLoader();
	}

	public static FashionDiyApplication getInstance() {
		synchronized (instance) {
			return instance;
		}		
	}
	
	private void initImageLoader(){
		File cacheDir = StorageUtils.getOwnCacheDirectory(getBaseContext(), "fashiondiy/cache");
		ImageLoaderConfiguration options = new ImageLoaderConfiguration.Builder(getBaseContext())
		.memoryCacheSize(4 * 1024 * 1024)
		//.imageDownloader(new BaseImageDownloader(getContext(),5 * 1000,30 * 1000))
		.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
		.threadPoolSize(5)                         // default  
		.threadPriority(Thread.NORM_PRIORITY - 1)   // default  
		.tasksProcessingOrder(QueueProcessingType.FIFO) // default 
		//.defaultDisplayImageOptions(displayOptions)
		.diskCache(new LimitedAgeDiskCache(cacheDir , 7 * 24 * 60 * 60))
		.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(options);
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

	public ImageLoader getImageLoader() {
		return imageLoader;
	}
}
