package com.chuangmeng.fashiondiy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.CollectionUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.ShareAppUtil;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.squareup.picasso.Picasso;
import de.greenrobot.event.EventBus;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import 	android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ListView;
import android.widget.Toast;

//SimpleOnGestureListener
public class DisplayDesignClothActivity extends BaseFragmentActivity implements OnTouchListener{
	private ListView showPictureListView;
	private ImageView expandImageView;
	
	private Button backBtn , menuBtn;
	
	private ShowClothAdapter adapter;
	
	private List<String> designClothList;
	private List<String> tryWearClothList;
	private ArrayList<String> shareClothList;
	
	private int screenWidth = 0;
	
	private Animator mCurrentAnimator;
	// 加载放大之后视图的控件
	private ImageView expandedImageView;
	private int mShortAnimationDuration;	
	
	private GestureDetector mGestureDetector;
	
	private View currentClickView = null;
	
	private String TAG = "DisplayDesignClothActivity";
	private String currentDeleteCloth = null;
	
	private ShareAppUtil shareAppUtil;
	
	private PopupWindow popupWindow;
	private View popView;
	private TextView shareText;
	private TextView myDesignText;
	private TextView myTryWearText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_design_cloth);
		
		mGestureDetector = new GestureDetector(this, new MygestureDector());
		mGestureDetector.setIsLongpressEnabled(true);
		
		popupWindow = new PopupWindow(this);
		popView = LayoutInflater.from(this).inflate(R.layout.design_show_popwindow_menu, null);
		popupWindow.setContentView(popView);
		popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);  
		popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(-00000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setOutsideTouchable(true);
		
		shareAppUtil = new ShareAppUtil(DisplayDesignClothActivity.this);
		EventBus.getDefault().register(this);	
		bindEvent();
		findLocalCloth();
	}
	
	private void bindEvent(){
		showPictureListView = (ListView)findViewById(R.id.show_design_cloth);
		expandImageView = (ImageView)findViewById(R.id.expanded_image);
		
		backBtn = (Button)findViewById(R.id.design_show_back_iv);
		menuBtn = (Button)findViewById(R.id.design_show_menu_iv);
		
		shareText = (TextView)popView.findViewById(R.id.share);
		myDesignText = (TextView)popView.findViewById(R.id.my_design);
		myTryWearText = (TextView)popView.findViewById(R.id.my_trywear);
		
		expandedImageView = (ImageView)findViewById(R.id.expanded_image);
			
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		menuBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				showPopupWindow(arg0);				
			}
		});
		
		shareText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(CollectionUtil.isArrayListNull(shareClothList)){
					Toast.makeText(DisplayDesignClothActivity.this,"请先选择图片！",Toast.LENGTH_LONG).show();
				}else{
					shareAppUtil.shareAppForManyImage(shareClothList);
					popupWindow.dismiss();
				}
			}
		});
		
		myDesignText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateShowList(true);
				popupWindow.dismiss();
			}
		});
		
		myTryWearText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateShowList(false);
				popupWindow.dismiss();
			}
		});
		
		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
	}
	
	private void showPopupWindow(View anchorView){
		if(popupWindow.isShowing()){
			popupWindow.dismiss();
			return;
		}
		popupWindow.showAsDropDown(anchorView, 6, 6);
	}
	
	private void updateShowList(boolean isDesignCloth){
		if(isDesignCloth){
			if(!CollectionUtil.isListNull(designClothList)){
				adapter.setList(designClothList);
				adapter.notifyDataSetChanged();
			}else{
				Toast.makeText(DisplayDesignClothActivity.this, "您暂时还没有设计作品哦", Toast.LENGTH_SHORT).show();
			}
		}else{
			if(!CollectionUtil.isListNull(tryWearClothList)){
				adapter.setList(tryWearClothList);
				adapter.notifyDataSetChanged();
			}else{
				Toast.makeText(DisplayDesignClothActivity.this, "您暂时还没有试穿哦", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void findLocalCloth(){
		
		screenWidth = FashionDiyApplication.getApplicationInstance().getScreenSize().widthPixels;
		shareClothList = new ArrayList<String>();
		
		File[] designClothFiles = new File(Constant.DIY_CLOTH_PICTURE_PATH).listFiles();
		File[] tryWearFiles = new File(Constant.DIY_TRYWARE_PICTURE_PATH).listFiles();
		if(designClothFiles != null && designClothFiles.length > 0 ){
			designClothList = new ArrayList<String>();
			for(File tempFile : designClothFiles){
				designClothList.add(tempFile.getAbsolutePath());
			}
		}
		if(tryWearFiles != null && tryWearFiles.length > 0 ){
			tryWearClothList = new ArrayList<String>();
			for(File tempFile : tryWearFiles){
				tryWearClothList.add(tempFile.getAbsolutePath());
			}
		}
		
		adapter = new ShowClothAdapter();
		if(!CollectionUtil.isListNull(designClothList)){		
			adapter.setList(designClothList);
		}else if(!CollectionUtil.isListNull(tryWearClothList)){
			adapter.setList(tryWearClothList);
		}else{
			Toast.makeText(DisplayDesignClothActivity.this, "您暂时还没有可以展示的图片哟", Toast.LENGTH_SHORT).show();
			return;
		}
		showPictureListView.setAdapter(adapter);
	}
	
	private class ShowClothAdapter extends BaseAdapter{
		private List<String> mShowList;
		private LayoutInflater  inflater;
		private FrameLayout firstImageLayout;
		private FrameLayout secondImageLayout;
		private LinearLayout.LayoutParams params;
		private int destWidth = screenWidth / 2;
		String fileTag = null;
		
		@SuppressLint("NewApi") 
		public ShowClothAdapter(){
			inflater = LayoutInflater.from(DisplayDesignClothActivity.this);
			params = new LayoutParams((ViewGroup.MarginLayoutParams)(new LayoutParams(destWidth, destWidth)));			
		}
		
		public void setList(List<String> showList){
			mShowList = showList;
		}
		
		@Override
		public int getCount() {
			if(!CollectionUtil.isListNull(mShowList)){
				if(mShowList.size() % 2 == 0){
					return mShowList.size() / 2;
				}else{
					return mShowList.size() / 2 + 1;
				}		
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if(arg1 == null){
				arg1 = inflater.inflate(R.layout.activity_display_design_cloth_item, null);
			}
			firstImageLayout = (FrameLayout)arg1.findViewById(R.id.first_image_layout);
			secondImageLayout = (FrameLayout)arg1.findViewById(R.id.second_image_layout);
			firstImageLayout.setLayoutParams(params);
			secondImageLayout.setLayoutParams(params);
			
			final ImageView showFirstImage = (ImageView)firstImageLayout.findViewById(R.id.display_cloth_image);
			final ImageView showSecondImage = (ImageView)secondImageLayout.findViewById(R.id.display_cloth_image);
			
			if(arg0 * 2 < mShowList.size()){
				fileTag = mShowList.get(arg0 * 2);
				File tempFile = new File(fileTag);
				if(tempFile.exists()){
					showFirstImage.setTag(fileTag);
					Picasso.with(DisplayDesignClothActivity.this).load(tempFile).resize(destWidth, destWidth).into(showFirstImage);
				}			
			}else{
				firstImageLayout.setVisibility(View.INVISIBLE);
			}
			if(arg0 * 2 + 1 < mShowList.size()){
				fileTag = mShowList.get(arg0 * 2 + 1);
				File tempFile = new File(fileTag);
				if(tempFile.exists()){
					showSecondImage.setTag(fileTag);
					Picasso.with(DisplayDesignClothActivity.this).load(tempFile).resize(destWidth, destWidth).into(showSecondImage);			
				}
			}else{
				secondImageLayout.setVisibility(View.INVISIBLE);
			}
			showFirstImage.setOnTouchListener(DisplayDesignClothActivity.this);
			showSecondImage.setOnTouchListener(DisplayDesignClothActivity.this);
			return arg1;
		}	
	}
	
	@SuppressLint("NewApi") 
	private void zoomImageFromThumb(final View thumbView, String imageResPath) {
		// 如果当前有动画正在运行，就迅速结束掉，运行现在这个动画
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		View parentView = null;
		if (thumbView == null || StringUtil.isEmpty(imageResPath)) {
			return;
		}

		parentView = (View) thumbView.getParent().getParent().getParent().getParent();		
//		int resWid = (int)(screenMetric.widthPixels);
//		int resHei = (int)(screenMetric.heightPixels * 0.9);
		//BitmapUtil.loadLocalImage(this, expandedImageView, imageResPath, resWid, resHei);
		Picasso.with(this).load(new File(imageResPath)).into(expandedImageView);
		// 开始略缩图的开始边界和放大视图的结束边界
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		// 开始边界是可见的略缩图的的矩形区域
		// 结束边界是不可见的顶级父视图的矩形区域，称其为容器，并设置这个容器的偏移作为原始视图的边界，这样就可以设置原始视图动画位置的属性，即XY轴的坐标
		thumbView.getGlobalVisibleRect(startBounds);
		parentView.findViewById(R.id.show_container).getGlobalVisibleRect(finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);
		// 使开始视图到结束视图有相同的纵横比采用了中心裁剪技术，这样可以避免在动画过程中的不良拉伸，，也要计算开始视图的拉伸。
		// 结束视图总是没有拉伸的
		float startScale;
		// 结束视图的宽高比大于开始视图的宽高比
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height()) {
			// 开始视图的拉伸率 = 开始视图的高度 / 结束视图的高度，最后就是横向拉伸开始视图
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {// 结束视图的宽高比小于开始视图的宽高比
			// 开始视图的拉伸率 = 开始视图的宽度 / 结束视图的宽度，最后就是纵向拉伸开始视图
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			// 以下两行是将开始视图往下拉一个deltaHeight高度，做一个要放大视图准备
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// 隐藏略缩图（通过设置透明度）并显示放大的视图，此时动画开始，放大试图会占据略缩图的位置
		thumbView.setAlpha(0f);
		expandedImageView.setVisibility(View.VISIBLE);

		// 设置放大视图开始动画的X，Y轴的点在左上角，默认是在视图中间
		expandedImageView.setPivotX(0f);
		expandedImageView.setPivotY(0f);

		// 在四个点和拉伸属性的基础上并行的创建和运行放大动画(X, Y, SCALE_X, and SCALE_Y)
		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left)).with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// 当点击放大视图的时候应该缩回到原来的略缩图，并且取代放大试图显示略缩图
		final float startScaleFinal = startScale;
		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// 在四个点和拉伸属性的基础上并行的创建和运行缩小动画(X, Y, SCALE_X, and SCALE_Y)
				AnimatorSet set = new AnimatorSet();
				set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal)).with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if(arg0 instanceof ImageView){
			currentClickView = arg0;
		}else{
			return false;
		}
		return mGestureDetector.onTouchEvent(arg1);  
	}
	
	public void onEventMainThread(String tag){
		if(!StringUtil.isEmpty(tag)){					
			if(tag.equals("delete")){
				dealwithDeleteCloth();
				return;
			}
		}
	}
	
	private void dealwithDeleteCloth(){
		if(!StringUtil.isEmpty(currentDeleteCloth)){
			int index1 = -1;
			int index2 = -1;
			if(!CollectionUtil.isListNull(designClothList)){
				index1 = designClothList.indexOf(currentDeleteCloth);
			}
			if(!CollectionUtil.isListNull(tryWearClothList)){
				index2 = tryWearClothList.indexOf(currentDeleteCloth);
			}
			if(index1 >= 0){
				designClothList.remove(index1);
			}else if(index2 >= 0){
				tryWearClothList.remove(index2);
			}
			if(adapter != null){
				adapter.notifyDataSetChanged();
			}
		}
	}	
	
	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		if(!CollectionUtil.isListNull(shareClothList)){
			shareClothList.clear();
			shareClothList = null;
		}
		if(!CollectionUtil.isListNull(designClothList)){
			designClothList.clear();
			designClothList = null;
		}
		if(!CollectionUtil.isListNull(tryWearClothList)){
			tryWearClothList.clear();
			tryWearClothList = null;
		}
		super.onDestroy();
	}
	
	private class MygestureDector extends SimpleOnGestureListener{
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.e(TAG, "a双击");
			dealWithDoubleClick();
			return true;
		}
		@Override
		public boolean onDown(MotionEvent e) {
			Log.e(TAG, "单击");
			return true;
		}
		@Override
		public void onLongPress(MotionEvent e) {
			Log.e(TAG, "a长按");
			dealWithLongClick();
			super.onLongPress(e);
		}
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			dealWithSimpleClick();
			return super.onSingleTapConfirmed(e);
		}
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.e(TAG, "双击1");
			return true;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.e(TAG, "单击1");
			return false;
		}
	}
	
	private void dealWithLongClick(){
		if(currentClickView == null){
			return;
		}
		String clothPath = (String)currentClickView.getTag();
		if(!StringUtil.isEmpty(clothPath)){
			currentDeleteCloth = clothPath;				
			Intent intent = new Intent(DisplayDesignClothActivity.this , ConfirmDialog.class);
			intent.putExtra(ConfirmDialog.DETAIL_MESSAGE, "确认删除？");
			startActivity(intent);
		}
	}
	
	private void dealWithSimpleClick(){
		if(currentClickView == null){
			return;
		}
		if(currentClickView instanceof ImageView){
			View view = (View)currentClickView.getParent();
			CheckBox chooseStateBox = (CheckBox)view.findViewById(R.id.choose_state_checkbox);
			if(chooseStateBox != null){
				if(chooseStateBox.isChecked()){
					chooseStateBox.setChecked(false);
					shareClothList.remove((String)currentClickView.getTag());
				}else{
					chooseStateBox.setChecked(true);
					shareClothList.add((String)currentClickView.getTag());
				}
			}
		}
	}
	
	private void dealWithDoubleClick(){
		if(currentClickView == null){
			return;
		}
		if(currentClickView instanceof ImageView){
			zoomImageFromThumb(currentClickView , (String)(currentClickView.getTag()));
		}
	}
}
