package com.chuangmeng.fashiondiy.util;

import java.io.File;
import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.view.svg.SvgImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

/**
 * @Title：FashionDIY
 * @Description：bitmap的处理函数，避免处理的照片过大出现OOM
 * @date 2014-11-5 下午3:06:00
 * @author chengang
 * @version 1.0
 */

public class BitmapUtil {

	private static String TAG = "BitmapUtil";
	/**
	 * 限定为int和String（Object）
	 * 
	 * @author admin
	 * @date 2015-6-3 上午9:32:39
	 * @param photoPath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap getBitmap(Context context , Object photoPath,int reqWidth, int reqHeight) {
		// 通过设置inJustDecodeBounds=true解码，获取照片的尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		if (photoPath instanceof String) {
			BitmapFactory.decodeFile((String) photoPath, options);
		} else if(photoPath instanceof Integer){
			BitmapFactory.decodeResource(context.getResources(),(Integer) photoPath, options);
		}else{
			BitmapFactory.decodeStream((InputStream)photoPath, null, options);
		}
		
		options.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);

		// 通过inSampleSize的大小返回对应处理后的图片大小
		options.inJustDecodeBounds = false;
		if (photoPath instanceof String) {
			return BitmapFactory.decodeFile((String) photoPath, options);
		}else if(photoPath instanceof Integer){
			return BitmapFactory.decodeResource(context.getResources(),(Integer)photoPath, options);
		}else{
			return BitmapFactory.decodeStream((InputStream)photoPath, null, options);
		}
	}
	
	private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
	
	public static void loadLocalImage(Context context ,ImageView view , String localPath , int width , int height){
		if(!StringUtil.isEmpty(localPath)){
			Picasso.with(context).load(new File(localPath)).resize(width, height).into(view);
		}
	}
	
	public static void loadNetImage(Context context ,ImageView view , String imageUrl , int width , int height){
		if(!StringUtil.isEmpty(imageUrl)){
			Picasso.with(context).load(imageUrl).resize(width, height).placeholder(R.drawable.app_icon).into(view);
		}
	}
	
	public static void loadResourceImage(Context context ,ImageView view , int resourceId , int width , int height){
		if(resourceId != 0){
			Picasso.with(context).load(resourceId).placeholder(R.drawable.app_icon).resize(width, height).into(view);
		}
	}
	
	public static void loadFileImage(Context context ,ImageView view , File imageFile , int width , int height){
		if(imageFile != null && imageFile.exists()){
			Picasso.with(context).load(imageFile).placeholder(R.drawable.app_icon).resize(width, height).into(view);
		}
	}
	
	public static void loadLocalImage(Context context ,ImageView view , String localPath){
		if(!StringUtil.isEmpty(localPath)){
			Picasso.with(context).load(localPath).into(view);
		}
	}
	
	public static void loadPhotoPath(Context context ,SvgImageView view , String localPath){
		if(!StringUtil.isEmpty(localPath)){
			Drawable drawable = BitmapDrawable.createFromPath(localPath);
			view.setImageDrawable(drawable);
		}
	}
	
	public static void loadLocalImage(Context context ,SvgImageView view , String localPath){
		if(!StringUtil.isEmpty(localPath)){
			Picasso.with(context).load(new File(localPath)).into	(view);
		}
	}
	
	public static void loadNetImage(Context context ,ImageView view , String imageUrl){
		if(!StringUtil.isEmpty(imageUrl)){
			Picasso.with(context).load(imageUrl).placeholder(R.drawable.app_icon).into(view);
		}
	}
	
	public static void loadResourceImage(Context context ,ImageView view , int resourceId){
		if(resourceId != 0){
			Picasso.with(context).load(resourceId).placeholder(R.drawable.app_icon).into(view);
		}
	}
	
	public static void loadFileImage(Context context ,ImageView view , File imageFile){
		if(imageFile != null && imageFile.exists()){
			Picasso.with(context).load(imageFile).placeholder(R.drawable.app_icon).into(view);			
		}
	}
	
	public static void loadUriImageView(final Context context ,final SvgImageView view, Uri uri){
		if(uri != null){
			view.setImageURI(uri);
		}
	}

	/**
	 * 将view转化为bitmap
	 * 
	 * @author Administrator
	 * @date 2014-12-5 上午8:56:05
	 * @param sourceView
	 * @return
	 */
	public static Bitmap getBitmapFromView(View sourceView) {
		if (sourceView == null) {
			return null;
		}
//		Bitmap returnedBitmap = Bitmap.createBitmap(sourceView.getWidth(),sourceView.getHeight(), Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(returnedBitmap);
//		Drawable bgDrawable = sourceView.getBackground();
//		if (bgDrawable != null) {
//			bgDrawable.draw(canvas);
//		} else {
//			canvas.drawColor(Color.TRANSPARENT);
//		}
//		sourceView.draw(canvas);
//		return returnedBitmap;
		
		sourceView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		sourceView.layout(0, 0, sourceView.getMeasuredWidth(), sourceView.getMeasuredHeight());
		sourceView.buildDrawingCache();
        Bitmap bitmap = sourceView.getDrawingCache();

        return bitmap;
	}
}
