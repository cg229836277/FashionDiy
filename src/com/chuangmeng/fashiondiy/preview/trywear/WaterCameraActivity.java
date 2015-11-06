package com.chuangmeng.fashiondiy.preview.trywear;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.chuangmeng.fashiondiy.DisplayDesignClothActivity;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.R.id;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.service.SaveTrywearClothService;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import com.chuangmeng.fashiondiy.util.CollectionUtil;
import com.chuangmeng.fashiondiy.util.SavePictureBean;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.jni.bitmap.operations.JniBitmapHolder;
import com.squareup.picasso.Picasso;

public class WaterCameraActivity extends BaseFragmentActivity implements OnClickListener{

	private Button backBtn;
	private SurfaceView surfaceView;
	private ImageView saveImageView;
	private ImageView takePicImageView;
	private ViewPager viewPager;

	private Camera camera;
	private Camera.Parameters parameters = null;
	private List<View> views = new ArrayList<View>();
	public static final String CHOOSED_CLOTH_LIST = "choosed_data";
	public static final String CHOOSED_DESIGN_CLOTH_LIST = "dsign_data";
	private ArrayList<String> choosedList = new ArrayList<String>();
	private int waterType = 0;

	private float surfaceW = 0f, surfaceH = 0f; // surfaceview的宽和高
	
	private boolean isSave = true;
	
	private boolean isDesignCloth = false;
	
	private SavePictureBean currentBeanData;
	
	private final String TAG = "WaterCameraActivity";
	
	private MyViewPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_trywear_camera);
		bindEvent();
		initData();
	}
	
	private void bindEvent(){
		saveImageView = (ImageView)findViewById(id.preview_trywear_camear_camera_reset_iv);
        surfaceView = (SurfaceView)findViewById(id.preview_trywear_camear_surfaceView_sv);
        backBtn = (Button)findViewById(id.preview_trywear_camear_back_iv);
        viewPager = (ViewPager)findViewById(id.preview_trywear_camear_viewPager_vp);
        takePicImageView = (ImageView)findViewById(id.preview_trywear_camear_take_pic_iv);
        
        saveImageView.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        takePicImageView.setOnClickListener(this);
	}

	private void initData() {
		choosedList = getIntent().getStringArrayListExtra(CHOOSED_CLOTH_LIST);
		String chooseDesignCloth = getIntent().getStringExtra(CHOOSED_DESIGN_CLOTH_LIST);
		if(!StringUtil.isEmpty(chooseDesignCloth)){
			isDesignCloth = true;
		}
		init();
	}

	private void preview_trywear_camear_take_pic_iv() {
		if(isSave){
			if (camera != null) {
				camera.takePicture(null, null, new MyPictureCallback());
				isSave = false;
			}
		}
	}

	private void preview_trywear_camear_camera_reset_iv() {
		Intent intent = new Intent(this , DisplayDesignClothActivity.class);
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivity(intent);
	}

	private void preview_trywear_camear_back_iv() {
		finish();
	}

	private void init() {
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setKeepScreenOn(true);
		surfaceView.getHolder().addCallback(new MySurfaceViewCallback());

		// viewPager
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		
		if(isDesignCloth && !CollectionUtil.isArrayListNull(appInstance.getBitmaps())){
			ArrayList<Bitmap> bitmapList = appInstance.getBitmaps();
			for(Bitmap tempData : bitmapList){
				View view = inflater.inflate(R.layout.preview_trywear_water_forward_camera, null);
				ImageView clothWaterPicture = (ImageView) view.findViewById(R.id.two_photo);
				clothWaterPicture.setImageBitmap(tempData);
				views.add(view);
			}
		}
		
		if (!CollectionUtil.isArrayListNull(choosedList)) {
			for (int i = 0; i < choosedList.size(); i++) {
				View view = inflater.inflate(R.layout.preview_trywear_water_forward_camera, null);
				ImageView clothWaterPicture = (ImageView) view.findViewById(R.id.two_photo);
				Picasso.with(this).load(new File(choosedList.get(i))).into(clothWaterPicture);
				//appInstance.getImageLoader().displayImage("file://" + choosedList.get(i), clothWaterPicture);
				views.add(view);
			}
		}
		adapter = new MyViewPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new MyOnPagerChangeListener());

	}

	private class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			adapter.notifyDataSetChanged();
			
			Bitmap viewBitmap = BitmapUtil.getBitmapFromView(views.get(waterType));
			if(viewBitmap != null && surfaceH != 0f && surfaceW != 0f){
				currentBeanData = new SavePictureBean(data , viewBitmap, surfaceW, surfaceH);
				appInstance.setTryWearBeanData(currentBeanData);
			}
			
			Intent intent = new Intent(WaterCameraActivity.this , SaveTrywearClothService.class);
			startService(intent);
			
			isSave = true;
			
			if (camera != null) {
				camera.startPreview();
			}
			
			showToast("衣服图片保存在sd卡fashion/tryware文件夹中");				
		}
		
		
	}

	private class MySurfaceViewCallback implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			if (camera == null) {
				camera = Camera.open();
			}

			surfaceH = height;
			surfaceW = width;
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(90);
				parameters = camera.getParameters();// 设置相机参数
				parameters.setPictureFormat(ImageFormat.JPEG);
				//parameters.setPreviewFrameRate(5);// 帧
				Size supportSize = getPictureSize();
				if(supportSize != null){
					parameters.setPictureSize(supportSize.width, supportSize.height);// 保存图片大小
				}				
				parameters.setJpegQuality(100);// 图片质量
				camera.setParameters(parameters);
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
	
	public Size getPictureSize(){
		if(parameters != null){
			List<Size> supportSize = parameters.getSupportedPictureSizes();
			if(!CollectionUtil.isListNull(supportSize)){
				for(Size tempSize : supportSize){
					if(tempSize != null){
						if(tempSize.width > 1000 && tempSize.width < 2000
								|| tempSize.height > 1000 && tempSize.height < 2000){
							return tempSize;
						}
					}
				}	
				//如果没有符合条件的图片尺寸大小
				return supportSize.get(0);
			}
		}
		return null;
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

		if (!CollectionUtil.isArrayListNull(choosedList)) {
			choosedList.clear();
			choosedList = null;
		}
		
		isDesignCloth = false;
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
			Log.w(TAG, "onPageScrollStateChanged");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			Log.w(TAG, "onPageScrolled");
		}

		@Override
		public void onPageSelected(int arg0) {
			waterType = arg0;
		}
	}
	
	public void showToast(final String detail){
		runOnUiThread(new Runnable() {
			public void run() {
				progressDialog.dismiss();		
				Toast.makeText(WaterCameraActivity.this, detail, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.preview_trywear_camear_camera_reset_iv:
			preview_trywear_camear_camera_reset_iv();
			break;
		case R.id.preview_trywear_camear_back_iv:
			preview_trywear_camear_back_iv();
			break;
		case R.id.preview_trywear_camear_take_pic_iv:
			preview_trywear_camear_take_pic_iv();
			break;
		default:
			break;
		}
	}
}