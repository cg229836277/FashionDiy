package com.chuangmeng.fashiondiy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.CollectionUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.squareup.picasso.Picasso;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayDesignClothActivity extends Activity {
	private ListView showPictureListView;
	private ImageView expandImageView;
	
	private Button dsignClothBtn;
	private Button tryWearClothBtn;
	
	private ShowClothAdapter adapter;
	
	private List<String> designClothList;
	private List<String> tryWearClothList;
	
	private int screenWidth = 0;
	
	private Animator mCurrentAnimator;
	// 加载放大之后视图的控件
	private ImageView expandedImageView;
	private int mShortAnimationDuration;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_design_cloth);
		
		bindEvent();
		findLocalCloth();
	}
	
	private void bindEvent(){
		showPictureListView = (ListView)findViewById(R.id.show_design_cloth);
		expandImageView = (ImageView)findViewById(R.id.expanded_image);
				
		dsignClothBtn = (Button)findViewById(R.id.show_design_cloth_btn);
		tryWearClothBtn = (Button)findViewById(R.id.show_trywear_cloth_btn);
		
		expandedImageView = (ImageView)findViewById(R.id.expanded_image);
		
		dsignClothBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateShowList(true);
			}
		});
		tryWearClothBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateShowList(false);
			}
		});
		
		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
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
		
		File[] designClothFiles = new File(Constant.DIY_CLOTH_PICTURE_PATH).listFiles();
		File[] tryWearFiles = new File(Constant.DIY_TRYWARE_PICTURE_PATH).listFiles();
		if(designClothFiles.length > 0 ){
			designClothList = new ArrayList<String>();
			for(File tempFile : designClothFiles){
				designClothList.add(tempFile.getAbsolutePath());
			}
		}
		if(tryWearFiles.length > 0 ){
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
		LinearLayout.LayoutParams params;
		private int destWidth = screenWidth / 2;
		
		@SuppressLint("NewApi") 
		public ShowClothAdapter(){
			inflater = LayoutInflater.from(DisplayDesignClothActivity.this);
			params = new LayoutParams(new LayoutParams(destWidth, destWidth));
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
				showFirstImage.setTag(mShowList.get(arg0 * 2));
				Picasso.with(DisplayDesignClothActivity.this).load(new File(mShowList.get(arg0 * 2))).resize(destWidth, destWidth).into(showFirstImage);
			}else{
				firstImageLayout.setVisibility(View.INVISIBLE);
			}
			if(arg0 * 2 + 1 < mShowList.size()){
				showSecondImage.setTag(mShowList.get(arg0 * 2 + 1));
				Picasso.with(DisplayDesignClothActivity.this).load(new File(mShowList.get(arg0 * 2 + 1))).resize(destWidth, destWidth).into(showSecondImage);			
			}else{
				secondImageLayout.setVisibility(View.INVISIBLE);
			}
			
			showFirstImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					zoomImageFromThumb(showFirstImage , (String)(arg0.getTag()));
				}
			});			
			showSecondImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					zoomImageFromThumb(showSecondImage , (String)(arg0.getTag()));
				}
			});
			
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
}
