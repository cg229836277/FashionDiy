package com.chuangmeng.fashiondiy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.CollectionUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.SavePictureBean;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.jni.bitmap.operations.JniBitmapHolder;
import com.jni.bitmap.operations.JniBitmapHolder.ScaleMethod;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;

public class SaveTrywearClothService extends IntentService {

	public static final String SAVE_PICTURE_TYPE = "type";
	private JniBitmapHolder bitmapHolder;
	private SavePictureBean beanData = null;
	private boolean isStart = false;
	private File saveFileDir = new File(Constant.DIY_CLOTH_PICTURE_PATH);
	
	private FashionDiyApplication appInstance;
	
	public SaveTrywearClothService() {
		super("savePicture");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();		
		bitmapHolder = new JniBitmapHolder();
		appInstance = FashionDiyApplication.getInstance();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		isStart = true;
		
		String tag = intent.getStringExtra(SAVE_PICTURE_TYPE);
		if(!StringUtil.isEmpty(tag) && tag.equals("preview")){
			saveDesignCloth();
		}else{		
			beanData = appInstance.getTryWearBeanData();
			if(beanData != null){
				processBitmapAsync(beanData.getByteData());
				appInstance.setTryWearBeanData(null);
			}
		}
	}
	
	public void processBitmapAsync(final byte[] data) {		
		// 根据surfaceview的宽和高为标准获取到bitmap，作为照片的尺寸
		Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		
        bitmapHolder.storeBitmap(cameraBitmap);
        bitmapHolder.rotateBitmapCw90();
        
        cameraBitmap = bitmapHolder.getBitmapAndFree();

		if (cameraBitmap != null) {
			dealWithPhotos(cameraBitmap);	
		}
	}
	
	public void dealWithPhotos(Bitmap result){
		if (result != null) {
			// 以时间戳作为文件名
			String photoName = System.currentTimeMillis() + ".png";
			String fileName = Constant.DIY_TRYWARE_PICTURE_PATH + photoName;

			File file = new File(Constant.DIY_TRYWARE_PICTURE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			anotherWayToDraw(result,beanData.getBitmap(), fileName);
		}
	}
	
	public void anotherWayToDraw(Bitmap sourceBitmap , Bitmap waterBitmap, String fileName){
		int sourceWidth = sourceBitmap.getWidth();
		int sourceHeight = sourceBitmap.getHeight();

		// 水印图片的大小
		int waterWidth = waterBitmap.getWidth();
		int waterHeight = waterBitmap.getHeight();

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);

		// Copy the original bitmap into the new one
		Canvas c = new Canvas(sourceBitmap);
		c.drawBitmap(sourceBitmap, 0, 0, paint);

		// Load the watermark
		float scaleW = sourceWidth / beanData.getWidth();// 宽度缩放比例
		float scaleH = sourceHeight / beanData.getHeight();// 高度缩放
		
		int scaleWInt = (int)(waterWidth * scaleW);
		int scaleHInt = (int)(waterHeight * scaleH);
		
		bitmapHolder.storeBitmap(waterBitmap);
        bitmapHolder.scaleBitmap(scaleWInt,scaleHInt, ScaleMethod.BilinearInterpolation);
        Bitmap scaledWaterBitmap = bitmapHolder.getBitmapAndFree();
        
		// Determine the post-scaled size of the watermark
        int scaleNewWidth = scaledWaterBitmap.getWidth();
        int scaleNewHeight = scaledWaterBitmap.getHeight();
		int left = (sourceWidth - scaleNewWidth) / 2;
		int top = (sourceHeight - scaleNewHeight) / 2;
		int right = left + scaleNewWidth;
		int bottom = top + scaleNewHeight;
		Rect rect1 = new Rect(left, top, right, bottom);
		
		Rect rect2 = new Rect(0, 0, scaleNewWidth, scaleNewHeight);

		// Draw the watermark
		c.drawBitmap(scaledWaterBitmap, rect2, rect1, paint);

		saveImage(sourceBitmap, fileName);
	}

	/**
	 * 保存添加水印的照片
	 * 
	 * @author Administrator
	 * @date 2014-12-5 上午11:05:39
	 * @param bitmap
	 * @param imageName
	 * @throws  
	 */
	private void saveImage(Bitmap bitmap, String imageName){
		try{
			//获取图片的名称就是该图片的序号的名称
			File f = new File(imageName);
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
			fOut.flush();
			fOut.close();
		}catch(IOException e){
			
		}
	}
	
	private synchronized void saveDesignCloth(){
		ArrayList<Bitmap> bitmapList = appInstance.getBitmaps();
		if(CollectionUtil.isArrayListNull(bitmapList)){
			return;
		}
		
		if(!saveFileDir.exists()){
			saveFileDir.mkdirs();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			
		Iterator<Bitmap> iterator = bitmapList.iterator();
		synchronized (saveFileDir) {
			while (iterator.hasNext()) {
				Bitmap currentBitmap = iterator.next();
				String fileName = dateFormat.format(new Date()) + ".png";
				try {
					File diyClothImage = new File(saveFileDir + "/" + fileName);
					if (diyClothImage.exists()) {
						diyClothImage.delete();
					}
					FileOutputStream out = new FileOutputStream(diyClothImage);
					currentBitmap.compress(CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
		appInstance.clearBitmapArray();
	}
	
	@Override
	public void onDestroy() {		
		super.onDestroy();
		if(bitmapHolder != null){
			bitmapHolder.freeBitmap();
			bitmapHolder = null;
		}
	}
}
