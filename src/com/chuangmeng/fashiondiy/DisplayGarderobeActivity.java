package com.chuangmeng.fashiondiy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.preview.trywear.WaterCameraActivity;
import com.chuangmeng.fashiondiy.util.CollectionUtil;
import com.chuangmeng.fashiondiy.util.IsListNotNull;
import com.chuangmeng.fashiondiy.util.ShareAppUtil;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.chuangmeng.fashiondiy.view.flip.FlipViewController;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

/**
 * 展示衣柜的衣服
 * 
 * @Title：FashionDIY
 * @Description：
 * @date 2014-11-26 上午9:53:59
 * @author chengang
 * @version 1.0
 */

public class DisplayGarderobeActivity extends BaseFragmentActivity {
	private DisplayClothImageAdapter clothAdapter;
	private FlipViewController flipView;
	private int mShortAnimationDuration;
	private Animator mCurrentAnimator;
	//private List<File> files = new ArrayList<File>();
	private RelativeLayout clothContainerView;// 衣柜衣服的展示
	private RelativeLayout activity_garderobe_edit_rl;// 衣服选择编辑布局
	private CheckBox actiity_garderobe_select_all_cb;// 衣服选择全选按钮
	private LinearLayout titleLayout;// 标题
	private Button titlebackImageView;
	private ImageView titleShareImageView;
	private ImageView tryWearImageView;
	
	private TextView displayPageCountText;
	private boolean mAllCheck = false;
	
	private boolean isTryWear = false;
	
	final int bitmapOffset = (int)(30 * screenMetric.density);
	final int resWidth = (screenMetric.widthPixels / 2) - bitmapOffset;
	final int resHeight = (screenMetric.heightPixels / 3) - bitmapOffset;
	//private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
	private ArrayList<String> allClothPathArray = new ArrayList<String>();
	private ArrayList<String> choosedClothPathArray = new ArrayList<String>();
	private ShareAppUtil shareAppUtil;
	
	private final String fileDir = Environment.getExternalStorageDirectory() + File.separator + "fashion" + File.separator + "cloth";

	private String currentDeleteCloth = null;
	
	private int currentPage = 0;
	
	public final static String DELETE_CLOTH = "delete";
	public final static String PAGE_FORWARD = "forward";
	public final static String PAGE_BACKWARD = "back";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_garderobe);
		
		EventBus.getDefault().register(this);
		
		initView();
		bindEventForView();
		shareAppUtil = new ShareAppUtil(DisplayGarderobeActivity.this);

		File filesDir = new File(fileDir);
		if (filesDir != null) {
			File[] currentFiles = filesDir.listFiles();
			if (currentFiles != null && currentFiles.length > 0) {
				for (int i = 0; i < currentFiles.length; i++) {
					allClothPathArray.add(currentFiles[i].getAbsolutePath());
				}
			}
		}

		if (allClothPathArray.size() <= 0) {
			Toast.makeText(this, "您暂时还没有衣服，赶紧去DIY吧！" , Toast.LENGTH_LONG).show();
			return;
		}

		clothAdapter = new DisplayClothImageAdapter();

		initGarderobeCloth();
		
		setPageCount(0);
	}
	
	public void initClothData(){
		
	}

	private void initView() {
		clothContainerView = (RelativeLayout) findViewById(R.id.activity_garderobe_container);
		titlebackImageView = (Button) findViewById(R.id.design_couple_back_iv);
		titleShareImageView = (ImageView) findViewById(R.id.activity_garderobe_title_share_iv);
		tryWearImageView = (ImageView)findViewById(R.id.display_try_wear_view);
		displayPageCountText = (TextView)findViewById(R.id.diaplay_page_count);
		activity_garderobe_edit_rl = (RelativeLayout) findViewById(R.id.activity_garderobe_edit_rl);
		actiity_garderobe_select_all_cb = (CheckBox) findViewById(R.id.actiity_garderobe_select_all_cb);
	}
	
	private void setPageCount(int tempPage){
		if(displayPageCountText != null){			
			int pageSize = 0;
			if(IsListNotNull.isListNotNull(allClothPathArray)){
				if(allClothPathArray.size() % 4 != 0){
					pageSize = allClothPathArray.size() / 4 + 1;
				}else{
					pageSize = allClothPathArray.size() / 4;
				}
				currentPage = tempPage;
				String detail = "第" + (tempPage + 1) + "页" + "(" + "共" + pageSize + "页" + ")";
				displayPageCountText.setText(detail);
			}else{
				Toast.makeText(this, "您暂时没有衣服，赶紧去DIY吧！" , Toast.LENGTH_LONG).show();
				return;
			}
		}
	}

	public void initGarderobeCloth() {		
		titleLayout = (LinearLayout) findViewById(R.id.ll_title);

		titleLayout.measure(0, 0);
		int titleLayoutHeight = titleLayout.getMeasuredHeight();

		clothAdapter = new DisplayClothImageAdapter();
		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		flipView.setAdapter(clothAdapter);
		clothContainerView.addView(flipView);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.width = (int) (screenMetric.widthPixels);
		params.height = (int) (screenMetric.heightPixels - titleLayoutHeight);
		params.bottomMargin = 48 * (int)(screenMetric.density);
		// 设置衣服所在视图的大小，避免出现每一个页面展示的大小不一致
		flipView.setLayoutParams(params);

		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
	}

	public void bindEventForView() {

		titlebackImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		titleShareImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(choosedClothPathArray.size() <= 0){
					Toast.makeText(DisplayGarderobeActivity.this,"请先选择图片！",Toast.LENGTH_LONG).show();
				}else{
					shareAppUtil.shareAppForManyImage(choosedClothPathArray);
				}
			}
		});

		/**
		 * 试穿按钮
		 */
		tryWearImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if(choosedClothPathArray.size() == 0){
					Toast.makeText(DisplayGarderobeActivity.this,"先选择衣服然后再试穿",Toast.LENGTH_LONG).show();
					return;
				}				

				isTryWear = true;
				Intent intent = new Intent(DisplayGarderobeActivity.this, WaterCameraActivity.class);			
				intent.putStringArrayListExtra(WaterCameraActivity.CHOOSED_CLOTH_LIST, choosedClothPathArray);
				startActivity(intent);
			}
		});

		/*
		 * 试衣全选功能
		 */
		actiity_garderobe_select_all_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean check) {
				if (check) {
					mAllCheck = true;
					clothAdapter.setAllCheck(true);
					selectedAllCloth();
				} else {		
					mAllCheck = false;
					clothAdapter.setAllCheck(false);										
					if(IsListNotNull.isListNotNull(choosedClothPathArray)){
						choosedClothPathArray.clear();
					}
				}	
				clothAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/**
	 * 处理全选衣服
	 * 
	 * @author Administrator
	 * @date 2015-1-8 下午4:49:00
	 */
	public void selectedAllCloth(){
		if(IsListNotNull.isListNotNull(allClothPathArray)){
			if(choosedClothPathArray.size() > 0){
				choosedClothPathArray.clear();
			}
			choosedClothPathArray.addAll(allClothPathArray);
		}			
	}

	public class DisplayClothImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		private int repeatCount = 1;				

		private List<ImageView> imageViews = new ArrayList<ImageView>();
		private List<LinearLayout> parenntViews = new ArrayList<LinearLayout>();
		private List<CheckBox> checkBoxs = new ArrayList<CheckBox>();
		private List<Button> buttons = new ArrayList<Button>();

		public DisplayClothImageAdapter() {
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public int getCount() {
			if (!CollectionUtil.isArrayListNull(allClothPathArray)) {
				if (allClothPathArray.size() % 4 != 0) {
					return allClothPathArray.size() / 4 + 1;
				} else {
					return allClothPathArray.size() / 4;
				}
			}
			return 0;
		}

		public int getRepeatCount() {
			return repeatCount;
		}

		public void setRepeatCount(int repeatCount) {
			this.repeatCount = repeatCount;
		}
		
		public void setAllCheck(boolean allCheck) {
			mAllCheck = allCheck;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View layout = convertView;
			int flag = position * 4;

			if (convertView == null) {
				layout = inflater.inflate(R.layout.activity_display_garderobe_item, null);
			}
			
			clearViews();
		
			parenntViews.add((LinearLayout)layout.findViewById(R.id.first_cloth_image));
			parenntViews.add((LinearLayout)layout.findViewById(R.id.second_cloth_image));
			parenntViews.add((LinearLayout)layout.findViewById(R.id.third_cloth_image));
			parenntViews.add((LinearLayout)layout.findViewById(R.id.fourth_cloth_image));
			
			for(LinearLayout currentLayout : parenntViews){
				imageViews.add((ImageView) currentLayout.findViewById(R.id.display_cloth_image));
				CheckBox currentCheckBox = (CheckBox)currentLayout.findViewById(R.id.choose_state_checkbox);
				checkBoxs.add(currentCheckBox);
				Button deleteClothBtn = (Button)currentLayout.findViewById(R.id.delete_cloth_checkbox);
				buttons.add(deleteClothBtn);
				if(mAllCheck){
					currentCheckBox.setChecked(true);
				}else{
					currentCheckBox.setChecked(false);
				}
			}			

			for (int i = flag; i < (flag + 4); i++) {
				final ImageView currentImageView = imageViews.get(i % 4);
				final CheckBox currentCheckBox = checkBoxs.get(i % 4);
				final Button currentButton = buttons.get(i % 4);
				if (i < allClothPathArray.size()) {
					String currentFilePath = allClothPathArray.get(i);
					if (!StringUtil.isEmpty(currentFilePath)) {
						
						View parentView = (View)currentImageView.getParent();
						parentView.setVisibility(View.VISIBLE);
						
						Picasso.with(getApplicationContext()).load(new File(currentFilePath)).resize(resWidth, resHeight).into(currentImageView);
//						BitmapUtil.loadLocalImage(DisplayGarderobeActivity.this, currentImageView, filePath, resWidth, resHeight);
						currentImageView.setTag(currentFilePath);

						currentImageView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								zoomImageFromThumb(currentImageView, (String) (currentImageView.getTag()));
							}
						});
												
						currentCheckBox.setTag(currentFilePath);
						currentCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton button, boolean isCheck) {
								if(mAllCheck || isTryWear){
									return;
								}
								if (isCheck) {
									choosedClothPathArray.add((String)currentCheckBox.getTag());
								} else {
									choosedClothPathArray.remove((String)currentCheckBox.getTag());
								}
							}
						});
						currentButton.setTag(currentFilePath);
						currentButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								String clothPath = (String)currentButton.getTag();
								if(!StringUtil.isEmpty(clothPath)){
									currentDeleteCloth = clothPath;	
									
									Intent intent = new Intent(DisplayGarderobeActivity.this , ConfirmDialog.class);
									intent.putExtra(ConfirmDialog.DETAIL_MESSAGE, "确认删除？");
									startActivity(intent);
								}
							}
						});
					}
				}else{
					View parentView = (View)currentImageView.getParent();
					parentView.setVisibility(View.GONE);
				}
			}
			return layout;
		}
		
		/**
		 * 每次加载衣服之前先清空视图容器
		 * 
		 * @author Administrator
		 * @date 2015-1-8 下午1:44:40
		 */
		public void clearViews(){
			if (IsListNotNull.isListNotNull(imageViews)) {
				imageViews.clear();
			}
			
			if (IsListNotNull.isListNotNull(parenntViews)) {
				parenntViews.clear();
			}
			
			if (IsListNotNull.isListNotNull(checkBoxs)) {
				checkBoxs.clear();
			}
			
			if (IsListNotNull.isListNotNull(buttons)) {
				buttons.clear();
			}
		}
	}

	/**
	 * 放大一个略缩图到一个正常分辨率大小的图片，并且让这张图片以动画的方式放大到适合屏幕的大小的区域，放置放大的图片的控件最初是隐藏的，
	 * 计算开始略缩图的开始边界和放大视图的结束边界，在四个位置（X, Y, SCALE_X,SCALE_Y）同时运行动画，即从开始边界到结束边界，
	 * 并且能够以相反的方式缩小 参考：http://developer.android.com/training/animation/zoom.html
	 * 
	 * @author chengang
	 * @date 2014-11-14 下午3:01:32
	 * @param thumbView
	 * @param imageResPath
	 */
	@SuppressLint("NewApi") 
	private void zoomImageFromThumb(final View thumbView, String imageResPath) {
		// 如果当前有动画正在运行，就迅速结束掉，运行现在这个动画
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		View parentView = null;
		if (thumbView != null) {

		}

		parentView = (View) thumbView.getParent().getParent().getParent().getParent();

		// 加载放大之后视图的控件
		final ImageView expandedImageView = (ImageView) parentView.findViewById(R.id.expanded_image);
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
		parentView.findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
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

	public void clearBitmapArray(){
		if(IsListNotNull.isListNotNull(choosedClothPathArray)){
			choosedClothPathArray.clear();
			choosedClothPathArray = null;
		}
		
		if(IsListNotNull.isListNotNull(allClothPathArray)){
			allClothPathArray.clear();
			allClothPathArray = null;
		}
	}
	
	@Override
	protected void onPause() {
		isTryWear = false;
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		
		if(actiity_garderobe_select_all_cb.isChecked()){
			actiity_garderobe_select_all_cb.performClick();
		}
		
		if (clothAdapter != null) {
			clothAdapter.notifyDataSetChanged();
		}
	}
	
	public void onEventMainThread(String tag){
		if(!StringUtil.isEmpty(tag)){					
			if(tag.equals(DELETE_CLOTH)){
				dealwithDeleteCloth();
				return;
			}
						
			dealwithPageCount("" + Integer.valueOf(tag), false);
		}
	}
	
	private void dealwithPageCount(String pageCount , boolean isDelete){
		if(!CollectionUtil.isArrayListNull(allClothPathArray)){
			int pageSize = allClothPathArray.size();
			if(pageSize > 1 && currentPage >= 0){
				
				if(isDelete){
					setPageCount(currentPage);
					return;
				}			
				setPageCount(Integer.parseInt(pageCount));
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(flipView != null && currentPage != 0){
				flipView.setSelection(0);
				setPageCount(currentPage - 1);
				return true;
			}else{
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void dealwithDeleteCloth(){
		if(!StringUtil.isEmpty(currentDeleteCloth)){
			File file = new File(currentDeleteCloth);
			if(file.exists()){
				boolean result = file.delete();
				if(result){
					Log.e("Display", "删除成功！");
					
					choosedClothPathArray.remove(currentDeleteCloth);
					allClothPathArray.remove(currentDeleteCloth);
					
					clothAdapter.notifyDataSetChanged();					
				}
			}
		}						
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		clearBitmapArray();
		super.onDestroy();
	}
}
