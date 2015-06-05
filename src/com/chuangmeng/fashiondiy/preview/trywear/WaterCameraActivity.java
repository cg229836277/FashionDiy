package com.chuangmeng.fashiondiy.preview.trywear;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.BitmapUtil;

@EActivity(R.layout.activity_preview_trywear_camera)
public class WaterCameraActivity extends BaseFragmentActivity {

	@ViewById
	ImageView preview_trywear_camear_back_iv;

	@ViewById
	SurfaceView preview_trywear_camear_surfaceView_sv;

	@ViewById
	ImageView preview_trywear_camear_camera_reset_iv;

	@ViewById
	ImageView preview_trywear_camear_tack_pic_iv;

	@ViewById
	TextView preview_trywear_camear_camera_save_tv; 

	@ViewById
	ViewPager preview_trywear_camear_viewPager_vp;

	private Camera camera;
	private Camera.Parameters parameters = null;
	private List<View> views = new ArrayList<View>();
	private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
	private final String SAVE_BASE_PATH = Environment.getExternalStorageDirectory() + "/Mydream/";
	private int waterType = 0;

	private float surfaceW, surfaceH; // surfaceview的宽和高

	private MediaPlayer music = null;// 播放器引用
	
	private boolean isSave = true;

	@AfterViews
	void initData() {
		bitmapList = FashionDiyApplication.getApplicationInstance().getBitmaps();
		init();
	}

	@Click
	void preview_trywear_camear_tack_pic_iv() {
		if(isSave){
			if (camera != null) {
				camera.takePicture(null, null, new MyPictureCallback());
//				PlayMusic(R.raw.take_picture);
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
				isSave = false;
			}
		}
	}

	@Click
	void preview_trywear_camear_camera_reset_iv() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivity(intent);

	}

	@Click
	public void preview_trywear_camear_back_iv() {
		backImageClicked();
	}

	private void init() {
		preview_trywear_camear_surfaceView_sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		preview_trywear_camear_surfaceView_sv.getHolder().setKeepScreenOn(true);
		preview_trywear_camear_surfaceView_sv.getHolder().addCallback(new MySurfaceViewCallback());

		RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parms.topMargin = (int) (100 * screenMetric.density);
		// viewPager
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		if (bitmapList != null) {
			for (int i = 0; i < bitmapList.size(); i++) {
				View view = inflater.inflate(R.layout.preview_trywear_water_forward_camera, null);
				ImageView two_photo = (ImageView) view.findViewById(R.id.two_photo);
				two_photo.setImageBitmap(bitmapList.get(i));
				two_photo.setLayoutParams(parms);
				views.add(view);
			}
		}

		preview_trywear_camear_viewPager_vp.setAdapter(new MyViewPagerAdapter());
		preview_trywear_camear_viewPager_vp.setOnPageChangeListener(new MyOnPagerChangeListener());

	}

	private class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			processBitmapAsync(data);
		}
	}

	public void processBitmapAsync(final byte[] data) {
		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... arg0) {
				// 根据surfaceview的宽和高为标准获取到bitmap，作为照片的尺寸
				Bitmap cameraBitmap = BitmapUtil.getBitmap(WaterCameraActivity.this , data, (int) surfaceW, (int) surfaceH);
				Matrix matrix = new Matrix();

				int height = cameraBitmap.getWidth();
				int width = cameraBitmap.getHeight();

				// 设置旋转角度（如果只做缩放处理，旋转这一步可以不要）
				matrix.preRotate(90);
				cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, height, width, matrix, true);

				if (cameraBitmap != null) {
					return cameraBitmap;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);
				if (result != null) {
					// 以时间戳作为文件名
					String photoName = System.currentTimeMillis() + ".png";
					String fileName = SAVE_BASE_PATH + photoName;

					File file = new File(SAVE_BASE_PATH);
					if (!file.exists()) {
						file.mkdirs();
					}

					generateImageWithWater(result, BitmapUtil.getBitmapFromView(views.get(waterType)), fileName);
				}
			}
		}.execute();
	}

	private class MySurfaceViewCallback implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			if (camera == null) {
				camera = Camera.open();
			}

			surfaceH = height;
			surfaceW = width;
			// Log.e("Water", "Surface的尺寸是" + surfaceW + "//" + surfaceH);
			parameters = camera.getParameters();// 设置相机参数
			parameters.setPictureFormat(PixelFormat.JPEG);

			parameters.setPreviewSize(width, height);// 预览大小
			parameters.setPreviewFrameRate(5);// 帧
			parameters.setPictureSize(width, height);// 保存图片大小
			parameters.setJpegQuality(100);// 图片质量
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(90);
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

			if (camera != null) {
				camera.release();
				camera = null;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (camera != null) {
			camera.startPreview();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (camera != null) {
			camera.release();
			camera = null;
		}

		if (views != null && views.size() > 0) {
			views.clear();
			views = null;
		}

		if (bitmapList != null && bitmapList.size() > 0) {
			bitmapList.clear();
			bitmapList = null;
		}

		FashionDiyApplication.getApplicationInstance().clearBitmapArray();
	}

	// viewPager
	private class MyViewPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

	}

	private class MyOnPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			waterType = arg0;
		}

	}

	/**
	 * 生成有水印的照片
	 * 
	 * @author Administrator
	 * @date 2014-12-5 下午1:21:34
	 * @param cameraBitmap
	 * @param waterBitmap
	 * @param fileName
	 */
	private void generateImageWithWater(Bitmap cameraBitmap, Bitmap waterBitmap, String fileName) {
		// 水印图片的大小
		int portrait_W = waterBitmap.getWidth();
		int portrait_H = waterBitmap.getHeight();
		int CAMERA_SIZE_W = cameraBitmap.getWidth();
		int CAMERA_SIZE_H = cameraBitmap.getHeight();

		// Log.e("Water", "照片的尺寸是" + CAMERA_SIZE_W + "//" + CAMERA_SIZE_H);

		float currentW = (float) CAMERA_SIZE_W;
		float currentH = (float) CAMERA_SIZE_H;

		// 通过获取到的照片的尺寸除以surfaceview的尺寸，由此可以得到预览的时候相对于获得的照片的缩放比例
		float scaleW = currentW / surfaceW;// 宽度缩放比例
		float scaleH = currentH / surfaceH;// 高度缩放

		// Log.e("Water", "水印尺寸" + scaleW + "//" + scaleH);

		Matrix waterMatrix = new Matrix();
		waterMatrix.postScale(scaleW, scaleH);
		// 根据照片和预览视图的缩放比例，缩放水印
		waterBitmap = Bitmap.createBitmap(waterBitmap, 0, 0, portrait_W, portrait_H, waterMatrix, false);
		portrait_W = waterBitmap.getWidth();
		portrait_H = waterBitmap.getHeight();

		// 设置头像要显示的位置，即居中显示 ， 水印的父视图的大小和预览的大小一致，缩放大小一致，实际上是在照片上绘制一张大小一致的图片，
		// 此时水印作为子视图还是在最初的位置，没有变动
		int left = (CAMERA_SIZE_W - portrait_W) / 2;
		int top = (CAMERA_SIZE_H - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		// Log.e("Water", "左" + left + "上" + top + "右" + right + "下" + bottom);

		Canvas canvas = new Canvas(cameraBitmap);

		// 设置我们要绘制的范围大小，也就是头像的大小范围
		Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
		// 开始绘制
		canvas.drawBitmap(waterBitmap, rect2, rect1, null);

		saveImage(cameraBitmap, fileName);
	}

	/**
	 * 保存添加水印的照片
	 * 
	 * @author Administrator
	 * @date 2014-12-5 上午11:05:39
	 * @param bitmap
	 * @param imageName
	 */
	private void saveImage(Bitmap bitmap, String imageName) {
		// //获取图片的名称就是该图片的序号的名称
		File f = new File(imageName);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
		}
		Toast.makeText(this, "保存成功", 1).show();
		Log.e("Water", "保存成功" + imageName);
		
		isSave = true;

		if (camera != null) {
			camera.startPreview();
		}
	}

	/**
	 * 播放音乐，参数是资源id
	 * 
	 * @param MusicId
	 */
	private void PlayMusic(int MusicId) {

		music = MediaPlayer.create(this, MusicId);
		music.start();

	}
}