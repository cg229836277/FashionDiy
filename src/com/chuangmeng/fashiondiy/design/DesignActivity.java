package com.chuangmeng.fashiondiy.design;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.bean.ClothImageViewSizeBean;
import com.chuangmeng.fashiondiy.preview.PreViewActivity_;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.chuangmeng.fashiondiy.view.DesignDetailPagerAdapter;
import com.chuangmeng.fashiondiy.view.HorizontalListView;
import com.chuangmeng.fashiondiy.view.NOScrollViewPager;
import com.chuangmeng.fashiondiy.view.RecyclingImageView;

/**
 * ClassName:DesignActivity Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author hch
 * @version
 * @since Ver 1.1
 * @Date 2014年10月23日 下午9:34:36
 */
@EActivity(R.layout.activity_design_main)
public class DesignActivity extends BaseFragmentActivity implements OnItemClickListener {
	private DisplayMetrics screenSize; // 获取设备尺寸
	private String takePhotoPath; // 拍摄照片的路径 
	private boolean isBack = false; // 是否是衣服的反面 ，默认不是
	private String operateFlag = null;// 操作标识符

	private Integer[] chooseClothPositiveStyle;// 用户选择衣服的类别之后初始化,正面
	private Integer[] chooseClothNegativeStyle;// 用户选择衣服的类别之后初始化,反面
	private Integer[] chooseClothModeStyle;// 用户选择衣服背景模板

	private NegativeDesignFragment negativeFragment;
	private PositiveDesignFragment positiveFragment;
	
	private FemaleNegativeDesignFragment femaleNegativeFragment;
	private FemalePositiveDesignFragment femalePositiveFragment;

	private List<Integer> iconDrawableIdList = new ArrayList<Integer>();
	private List<Integer> modelDrawableIdList = new ArrayList<Integer>();
	private List<Integer> styleDrawableIdList = new ArrayList<Integer>();
	
	private String DESIGN_STYLE = null;
	
	private boolean isChooseFemale = false; //是否是选择了情侣的女装，默认不是

	@ViewById
	LinearLayout design_bottom_detail_ll;// 页面底部五个功能按钮视图父类

	LinearLayout design_title_include; // 页面顶部
	
	@ViewById
	LinearLayout design_cloth_child_title;
	
	@ViewById
	LinearLayout design_title_couple_include;//情侣页面顶部

	@ViewById
	NOScrollViewPager design_cloth_detail_viewpager; // 设计区域

	@ViewById
	ImageView design_bottom_model_iv; // 模型

	@ViewById
	ImageView design_bottom_camera_iv; // 照相

	@ViewById
	ImageView design_bottom_word_iv; // 文字

	@ViewById
	ImageView design_bottom_pattern_iv; // 图案

	ImageView design_bottom_background_iv;// 背景

	@ViewById
	ImageView design_title_preview_iv;// 预览

	ImageView design_title_forward_iv;// 衣服前面

	ImageView design_title_backward_iv; // 衣服后面

	GridView design_model_operate_gridview; // 操作按钮点击之后弹出的操作框

	RelativeLayout design_choose_operate_rl; // 选择操作框的视图父类

	@ViewById
	ImageView design_bottom_operate_hide_iv; // 操作框的显示隐藏控制按钮

	@ViewById
	LinearLayout design_take_select_photo_rl; // 相片选择方式的弹出框的视图父类

	ImageView design_take_photo_iv; // 拍照

	ImageView design_select_local_photo_iv; // 本地选取照片

	RelativeLayout design_bottom_operate_gridview_rl;// 设计元素展示框

	HorizontalListView design_model_operate_text_style_listview;// 字体样式展示list

	@ViewById
	HorizontalListView design_model_operate_text_color_listview;// 字体颜色展示list
	
	@ViewById
	ImageView design_couple_title_back_iv;
	
	ImageView design_title_back_iv;

	ImageView design_couple_title_preview_iv;
	
	@ViewById
	ImageView design_couple_title_male_front_iv;
	
	@ViewById
	ImageView design_couple_title_male_back_iv;
	
	@ViewById
	ImageView design_couple_title_female_front_iv;
	
	@ViewById
	ImageView design_couple_title_female_back_iv;

	private int textSize = 20;
	
	BitmapUtil loadUtils;

	@AfterViews
	void initData() {				
		screenSize = FashionDiyApplication.getApplicationInstance().getScreenSize();
		
		DESIGN_STYLE = getIntent().getStringExtra(Constant.STYLE);
		
		bindEvent();

		// 初始化两个控件的状态
		exchangeChooseState(isBack);
		
		setCoupleTitleSexBtn(isChooseFemale);
		
		initFragmentData();
		setOperateViewHeight();

		initClothStyle();

		String designFlag = null;
		if(isDesignCoupleCloth()){
			designFlag = DESIGN_STYLE;
		}
		
		design_cloth_detail_viewpager.setOffscreenPageLimit(4);
		design_cloth_detail_viewpager.setAdapter(new DesignDetailPagerAdapter(getSupportFragmentManager() , designFlag));

		design_model_operate_gridview.setOnItemClickListener(this);
		design_cloth_detail_viewpager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				hideTextDesignLayout();

				// TODO Auto-generated method stub
				design_choose_operate_rl.setVisibility(View.GONE);
				design_take_select_photo_rl.setVisibility(View.GONE);
				return false;

			}
		});
		design_choose_operate_rl.setVisibility(View.GONE);
		design_take_select_photo_rl.setVisibility(View.GONE);
	}
	
	public void bindEvent(){
		design_title_backward_iv = (ImageView)findViewById(R.id.design_title_backward_iv);
		design_title_include = (LinearLayout)findViewById(R.id.design_title_include);
		design_title_back_iv = (ImageView)findViewById(R.id.design_title_back_iv);
		design_select_local_photo_iv = (ImageView)findViewById(R.id.design_select_local_photo_iv);
		design_model_operate_gridview = (GridView)findViewById(R.id.design_model_operate_gridview);
		design_take_photo_iv = (ImageView)findViewById(R.id.design_take_photo_iv);
		design_model_operate_text_style_listview = (HorizontalListView)findViewById(R.id.design_model_operate_text_style_listview);
		design_bottom_background_iv = (ImageView)findViewById(R.id.design_bottom_background_iv);
		design_choose_operate_rl = (RelativeLayout)findViewById(R.id.design_choose_operate_rl);
		design_title_forward_iv = (ImageView)findViewById(R.id.design_title_forward_iv);
		design_bottom_operate_gridview_rl = (RelativeLayout)findViewById(R.id.design_bottom_operate_gridview_rl);
		design_couple_title_preview_iv  = (ImageView)findViewById(R.id.design_couple_title_preview_iv);
	}

	/**
	 * 进入之后初始化用户选择的衣服的样式
	 * 
	 * @author chengang
	 * @date 2014-11-13 上午11:46:23
	 */
	public void initClothStyle() {
		if(StringUtil.isEmpty(DESIGN_STYLE)){
			return;
		}
		
		chooseClothModeStyle = Constant.designModelRaw;
		if (!StringUtil.isEmpty(DESIGN_STYLE)) {
			if (DESIGN_STYLE.equals(Constant.MALE_STYLE)) {// 选择男装
				chooseClothPositiveStyle = Constant.maleAllClothPositive;
				chooseClothNegativeStyle = Constant.maleAllClothNegative;
			} else if (DESIGN_STYLE.equals(Constant.FEMALE_STYLE)) {// 选择女装
				chooseClothPositiveStyle = Constant.femaleAllClothPositive;
				chooseClothNegativeStyle = Constant.femaleAllClothNegative;
			} else {//选择情侣装
				chooseClothPositiveStyle = Constant.allClothPositive;
				chooseClothNegativeStyle = Constant.allClothNegative;
			}

			initDrawableSource(DESIGN_STYLE);
			
			if(DESIGN_STYLE.equals(Constant.COUPLE_STYLE)){
				design_title_couple_include.setVisibility(View.VISIBLE);
				design_title_include.setVisibility(View.GONE);
			}else{
				design_title_couple_include.setVisibility(View.GONE);
				design_title_include.setVisibility(View.VISIBLE);
				
				LinearLayout.LayoutParams paras = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				paras.width = screenMetric.widthPixels / 2;
				paras.height = 50 * (int)screenMetric.density;
				paras.topMargin = 8 * (int)screenMetric.density;
				paras.bottomMargin = 8 * (int)screenMetric.density;
				paras.gravity = Gravity.CENTER;
				design_cloth_child_title.setLayoutParams(paras);
			}
		}
	}

	/**
	 * 初始化图片资源
	 * 
	 * @author chengang
	 * @date 2014-11-21 上午10:28:18
	 */
	public void initDrawableSource(String flag) {
		for (int i = 0; i < Constant.designPatternDrawable.length; i++) {
			iconDrawableIdList.add(Constant.designPatternDrawable[i]);
		}

		if (flag.equals(Constant.FEMALE_STYLE)) {
			for (int i = 0; i < Constant.femaleAllClothPositive.length; i++) {
				styleDrawableIdList.add(Constant.femaleAllClothPositive[i]);
			}
		} else if(flag.equals(Constant.MALE_STYLE)){
			for (int i = 0; i < Constant.maleAllClothPositive.length; i++) {
				styleDrawableIdList.add(Constant.maleAllClothPositive[i]);
			}
		}else{
			if(chooseClothPositiveStyle != null){
				for (int i = 0; i < chooseClothPositiveStyle.length; i++) {
					styleDrawableIdList.add(chooseClothPositiveStyle[i]);
				}
			}
		}

		for (int i = 0; i < Constant.designModelDrawable.length; i++) {
			modelDrawableIdList.add(Constant.designModelDrawable[i]);
		}
	}

	/**
	 * 模型
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:23:18
	 */
	@Click
	public void design_bottom_model_iv() {		
		operateFlag = Constant.ADD_MODEL_ON_CLOTH;
		controlOperateView(modelDrawableIdList);
	}

	/**
	 * 照相
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:23:08
	 */
	@Click
	public void design_bottom_camera_iv() {
		operateFlag = Constant.ADD_PICTURE_ON_CLOTH;
		setGetPhotoViewShowOrHide();
	}

	/**
	 * 文字
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:23:00
	 */
	@Click
	public void design_bottom_word_iv() {
		showTextOnCloth();
		operateFlag = Constant.ADD_TEXT_ON_CLOTH;
		setOperateViewShowOrHide();
		design_choose_operate_rl.setVisibility(View.VISIBLE);
		design_bottom_operate_gridview_rl.setVisibility(View.GONE);
	}

	/**
	 * 图案
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:22:34
	 */
	@Click
	public void design_bottom_pattern_iv() {
		operateFlag = Constant.ADD_ICON_ON_CLOTH;
		controlOperateView(iconDrawableIdList);
	}

	/**
	 * 背景
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:22:51
	 */
	@Click
	public void design_bottom_background_iv() {		
		operateFlag = Constant.CHANGE_CLOTH_STYLE;
		controlOperateView(styleDrawableIdList);
	}
	
	public void controlOperateView(List<Integer> sourceList){
		setOperateViewShowOrHide();
		design_choose_operate_rl.setVisibility(View.VISIBLE);
		design_bottom_operate_gridview_rl.setVisibility(View.VISIBLE);
		design_model_operate_gridview.setAdapter(new ImageAdapter(getApplicationContext(), sourceList));
	}

	/**
	 * 衣服前面
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:22:15
	 */
	@Click
	public void design_title_forward_iv() {
		clickedForwardBtn();
	}

	/**
	 * 衣服后面
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:21:42
	 */
	@Click
	public void design_title_backward_iv() {
		clickedBackWardBtn();
	}
	
	public void clickedForwardBtn(){
		isBack = false;
		exchangeChooseState(false);
		setCurrentDesignView(false);
	}
	
	public void clickedBackWardBtn(){
		isBack = true;
		exchangeChooseState(true);
		setCurrentDesignView(true);
	}

	/**
	 * 操作框的显示或者隐藏按钮
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:23:45
	 */
	@Click
	public void design_bottom_operate_hide_iv() {
		setOperateViewShowOrHide();
	}

	/**
	 * 拍照获取照片
	 * 
	 * @author chengang
	 * @date 2014-11-5 下午2:42:30
	 */
	@Click
	public void design_take_photo_iv() {
		File dir = new File(Constant.TAKE_PICTURE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 定义照片名字
		String takePhotoName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".png";
		File file = new File(dir, takePhotoName);
		takePhotoPath = file.getAbsolutePath();// 获取照片路径
		Uri imageUri = Uri.fromFile(file);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, Constant.GET_PHOTO_BY_CAMERA);
	}

	/**
	 * 选取本地照片
	 * 
	 * @author chengang
	 * @date 2014-11-5 下午2:42:43
	 */
	@Click
	public void design_select_local_photo_iv() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, Constant.GET_PHOTO_BY_LOCAL);
	}
	
	/**
	 * 情侣页的男士按钮点击
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午4:21:00
	 */
	@Click
	public void design_couple_title_male_front_iv(){
		isBack = false;
		setCoupleTitleSexBtn(false);		
		setCurrentDesignView(false);
	}
	
	/**
	 * 情侣页的女士点击
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午4:21:39
	 */
	@Click
	public void design_couple_title_male_back_iv(){
		isBack = true;
		setCoupleTitleSexBtn(false);		
		setCurrentDesignView(true);
	}
	
	/**
	 * 设置情侣页面男女按钮的状态
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午5:03:12
	 */
	public void setCoupleTitleSexBtn(boolean isFemale){
		
		if(StringUtil.isEmpty(DESIGN_STYLE)){
			return;
		}
		
		if(isFemale){
			if(isBack){
				design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_pressed);
				design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			}else{
				design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_pressed);
				design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);
			}			
			design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_normal);
			design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			isChooseFemale = true;
		}else{
			if(isBack){
				design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_pressed);
				design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_normal);
			}else{
				design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_pressed);
				design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			}			
			design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);
			isChooseFemale = false;
		}
	}
	
	/**
	 * 情侣页的前按钮点击
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午4:23:40
	 */
	@Click
	public void design_couple_title_female_front_iv(){
		isBack = false;
		setCoupleTitleSexBtn(true);		
		setCurrentDesignView(false);
	}
	
	/**
	 * 情侣页的后按钮点击
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午4:24:36
	 */
	@Click
	public void design_couple_title_female_back_iv(){
		isBack = true;
		setCoupleTitleSexBtn(true);		
		setCurrentDesignView(true);
	}
	
	
	@Click
	public void design_title_back_iv(){
		backImageClicked();
	}
	
	@Click
	public void design_couple_title_back_iv(){
		backImageClicked();
	}

	/**
	 * 设置功能键对应的操作框的显示与隐藏
	 */
	public void setOperateViewShowOrHide() {
		if (design_take_select_photo_rl.getVisibility() == View.VISIBLE) {
			design_take_select_photo_rl.setVisibility(View.GONE);
		}
		if (design_choose_operate_rl.getVisibility() != View.VISIBLE) {
			design_choose_operate_rl.setVisibility(View.VISIBLE);
		} else {
			design_choose_operate_rl.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置照片按钮点击之后的操作框显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-5 下午2:17:24
	 */
	public void setGetPhotoViewShowOrHide() {
		if (design_choose_operate_rl.getVisibility() == View.VISIBLE) {
			design_choose_operate_rl.setVisibility(View.GONE);
		}
		if (design_take_select_photo_rl.getVisibility() != View.VISIBLE) {
			design_take_select_photo_rl.setVisibility(View.VISIBLE);
		} else {
			design_take_select_photo_rl.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置字体按钮点击之后的操作框显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-5 下午2:17:24
	 */
	public void setGetFontViewShowOrHide() {
		if (design_take_select_photo_rl.getVisibility() == View.VISIBLE) {
			design_take_select_photo_rl.setVisibility(View.GONE);
		}

		if (design_choose_operate_rl.getVisibility() != View.VISIBLE) {
			design_choose_operate_rl.setVisibility(View.VISIBLE);
		} else {
			design_choose_operate_rl.setVisibility(View.GONE);
		}
	}

	/**
	 * 预览
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午2:21:28
	 */
	@Click
	public void design_title_preview_iv() {
		previewBtnClicked();		
	}
	
	@Click
	public void design_couple_title_preview_iv(){
		previewBtnClicked();		
	}
	
	public void previewBtnClicked(){
		if(negativeFragment == null || positiveFragment == null){
			initFragmentData();
		}
		
		hideTextDesignLayout();
		hideControlView();
		Bitmap positiveBitmap = positiveFragment.getBitmapPositiveView();
		if (positiveBitmap != null) {
			FashionDiyApplication.getApplicationInstance().setPositiveViewBitmap(positiveBitmap);
		}
		Bitmap negativeBitmap = negativeFragment.getBitmapNegativeView();
		if (negativeBitmap != null) {
			FashionDiyApplication.getApplicationInstance().setNegativeViewBitmap(negativeBitmap);
		}
		
		if (isDesignCoupleCloth()) {
			Bitmap femalePositiveBitmap = femalePositiveFragment.getBitmapPositiveView();
			if (femalePositiveBitmap != null) {
				FashionDiyApplication.getApplicationInstance().setFemalePositiveBitmap(femalePositiveBitmap);
			}
			Bitmap femaleNegativeBitmap = femaleNegativeFragment.getBitmapNegativeView();
			if (femaleNegativeBitmap != null) {
				FashionDiyApplication.getApplicationInstance().setFemaleNegativeBitmap(femaleNegativeBitmap);
			}
		}
		
		Intent intent = new Intent(getApplicationContext(), PreViewActivity_.class);
		
		intent.putExtra(PreViewActivity_.PREVIEW_COUPLE, DESIGN_STYLE);
		
		startActivity(intent);
	}

	/**
	 * 对应变换前后按钮的状态 
	 * 
	 * @author chengang
	 * @date 2014-10-31 下午2:04:09
	 */
	public void exchangeChooseState(boolean isBack) {
		if(isDesignCoupleCloth()){
			design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);		
			design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_pressed);
			design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			return;
		}
		if (isBack) {
			design_title_forward_iv.setImageResource(R.drawable.design_choose_forward_normal);
			design_title_backward_iv.setImageResource(R.drawable.design_choose_back_pressed);
		}else{
			design_title_forward_iv.setImageResource(R.drawable.design_choose_forward_pressed);
			design_title_backward_iv.setImageResource(R.drawable.design_choose_back_normal);
		}
	}

	/**
	 * 点击前后按钮的时候切换相应的页面
	 * 
	 * @author chengang
	 * @date 2014-10-31 下午2:12:28
	 * @param isBack
	 */
	public void setCurrentDesignView(boolean isBack) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if(isDesignCoupleCloth()){
			if(isChooseFemale){
				if(isBack){
					design_cloth_detail_viewpager.setCurrentItem(3);
				}else{
					design_cloth_detail_viewpager.setCurrentItem(2);
				}
				
			}else{
				if(isBack){
					design_cloth_detail_viewpager.setCurrentItem(1);
				}else{
					design_cloth_detail_viewpager.setCurrentItem(0);
				}			
			}
		}else{
			if(isBack){
				design_cloth_detail_viewpager.setCurrentItem(1);
			}else{
				design_cloth_detail_viewpager.setCurrentItem(0);
			}
		}
		ft.addToBackStack(null);
		ft.commit();
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private List<Integer> mSourceDrawable;		

		public ImageAdapter(Context c, List<Integer> sourceDrawable) {
			mContext = c;
			this.mSourceDrawable = sourceDrawable;			
		}

		public int getCount() {
			return mSourceDrawable.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			RecyclingImageView imageView;

			int imageWidth = screenSize.widthPixels / 5;
			int imageHeight = imageWidth;

			if (convertView == null) {
				imageView = new RecyclingImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageHeight));
				imageView.setPadding(8, 8, 8, 8);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			} else {
				imageView = (RecyclingImageView) convertView;
			}

			BitmapUtil.loadResourceImage(DesignActivity.this , imageView,mSourceDrawable.get(position),imageWidth, imageHeight);

			return imageView;
		}

	}

	/**
	 * 设置功能对应弹出框的高度，针对每个item的高度 X 2
	 */
	public void setOperateViewHeight() {
		ViewGroup.LayoutParams operateDetailRl = design_choose_operate_rl.getLayoutParams();
		operateDetailRl.height = screenSize.widthPixels / 6 * 2 + (int) (48 * (screenSize.density));
		design_choose_operate_rl.setLayoutParams(operateDetailRl);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constant.GET_PHOTO_BY_CAMERA:// 拍照获取照片
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					return;
				}
				if (!StringUtil.isEmpty(takePhotoPath)) {
					File file = new File(takePhotoPath);
					if (file != null && file.exists()) {
						setDesignPictureOnCloth(null , takePhotoPath);
					}
				}
			}
			break;
		case Constant.GET_PHOTO_BY_LOCAL:// 从本地选取图片
			if (data == null) {
				return;
			}
			Uri uri = data.getData();
			if (uri == null) {
				return;
			}
			//Bitmap localPhotoBitmap = loadUtils.getBitmap(getContentResolver().openInputStream(uri), getMatrixSize().width, getMatrixSize().height);
			setDesignPictureOnCloth(uri , null);
			break;
		}
	}

	public ClothImageViewSizeBean getMatrixSize() {
		if (positiveFragment == null || negativeFragment == null) {
			initFragmentData();
		}

		if (isBack) {
			return negativeFragment.getBitmapSize();
		} else {
			return positiveFragment.getBitmapSize();
		}
	}

	/**
	 * 初始化获取设计的fragment对象,每次要使用fragment对象的时候必须调用这个对象， 重新获取fragment对象，不然会出现空指针
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:44:09
	 */
	public void initFragmentData() {
		positiveFragment = (PositiveDesignFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.design_cloth_detail_viewpager + ":0");
		negativeFragment = (NegativeDesignFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.design_cloth_detail_viewpager + ":1");
		if(isDesignCoupleCloth()){
			femalePositiveFragment = (FemalePositiveDesignFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.design_cloth_detail_viewpager + ":2");
			femaleNegativeFragment = (FemaleNegativeDesignFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.design_cloth_detail_viewpager + ":3");
		}
	}

	/**
	 * 往衣服上添加图片
	 * 
	 * @author chengang
	 * @date 2014-11-5 下午3:36:52
	 * @param sourceBitmap
	 */
	public void setPictureOnCloth(Bitmap sourceBitmap) {
		initFragmentData();
		if (isBack) {
			if (negativeFragment != null) {
				negativeFragment.addIconOnCloth(sourceBitmap);
			}
		} else {
			if (positiveFragment != null) {
				positiveFragment.addIconOnCloth(sourceBitmap);
			}
		}
	}

	/**
	 * setDesignPictureOnCloth:给衣服的绘画区域添加背景
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void setDesignPictureOnCloth(Uri uri , String photoPath) {
		initFragmentData();
		if(isDesignCoupleCloth()){
			if(isChooseFemale){
				if(isBack){
					if(!StringUtil.isEmpty(photoPath)){
						femaleNegativeFragment.addClothDesignPicture(photoPath);
					}else{
						femaleNegativeFragment.addClothDesignPicture(uri);
					}
				}else{
					if(!StringUtil.isEmpty(photoPath)){
						femalePositiveFragment.addClothDesignPicture(photoPath);
					}else{
						femalePositiveFragment.addClothDesignPicture(uri);
					}
				}
			}else{
				addPictureOnMaleCloth(uri , photoPath);
			}
		}else{
				addPictureOnMaleCloth(uri , photoPath);
		}
	}
	
	/**
	 * 在男士衣服上面添加图片
	 * 
	 * @author Administrator
	 * @date 2014-12-22 上午11:49:51
	 * @param drawable
	 */
	public void addPictureOnMaleCloth(Uri uri , String photoPath){
		if (isBack) {
			if (negativeFragment != null) {
				if(StringUtil.isEmpty(photoPath)){
					negativeFragment.addClothDesignPicture(uri);
				}else{
					negativeFragment.addClothDesignPicture(photoPath);
				}
			}
		} else {
			if (positiveFragment != null) {
				if(StringUtil.isEmpty(photoPath)){
					positiveFragment.addClothDesignPicture(uri);
				}else{
					positiveFragment.addClothDesignPicture(photoPath);
				}
			}
		}
	}

	/**
	 * setTextOnCloth: 在衣服上添加文字
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void showTextOnCloth() {
		initFragmentData();
		
		if(isDesignCoupleCloth()){
			if(isChooseFemale){
				if(isBack){
					femaleNegativeFragment.showTextDesignLayout();
				}else{
					femalePositiveFragment.showTextDesignLayout();
				}
			}else{
				addTextOnMaleCloth();
			}
		}else{
			addTextOnMaleCloth();
		}
	}
	
	public void addTextOnMaleCloth(){
		if (isBack) {
			if (negativeFragment != null) {
				negativeFragment.showTextDesignLayout();
			}
		} else {
			if (positiveFragment != null) {
				positiveFragment.showTextDesignLayout();
			}
		}
	}

	/**
	 * hideTextDesignLayout:隐藏文字编辑框
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void hideTextDesignLayout() {
		if (negativeFragment != null) {
			negativeFragment.hideTextDesignLayout();
		}
		if (positiveFragment != null) {
			positiveFragment.hideTextDesignLayout();
		}
		if(femaleNegativeFragment != null){
			femaleNegativeFragment.hideTextDesignLayout();
		}		
		if(femalePositiveFragment != null){
			femalePositiveFragment.hideTextDesignLayout();
		}
	}
	
	/**
	 * 隐藏控制和删除按钮
	 * author:陈刚
	 * 2014-12-2
	 * descript:
	 */
	public void hideControlView(){
		if (negativeFragment != null) {
			negativeFragment.hideControlView();
		}
		if (positiveFragment != null) {
			positiveFragment.hideControlView();
		}
		if(femaleNegativeFragment != null){
			femaleNegativeFragment.hideControlView();
		}		
		if(femalePositiveFragment != null){
			femalePositiveFragment.hideControlView();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (positiveFragment == null || negativeFragment == null || femaleNegativeFragment == null || femalePositiveFragment == null) {
			initFragmentData();
		}

		if (!StringUtil.isEmpty(operateFlag)) {
			if (operateFlag.equals(Constant.CHANGE_CLOTH_STYLE)) {
				if(isDesignCoupleCloth()){
					if(isChooseFemale){
						femalePositiveFragment.setClothStyleOrColor(chooseClothPositiveStyle[arg2]);
						femaleNegativeFragment.setClothStyleOrColor(chooseClothNegativeStyle[arg2]);
					}else{					
						setMaleClothStyle(chooseClothPositiveStyle[arg2], chooseClothNegativeStyle[arg2]);
					}
				}else{
					setMaleClothStyle(chooseClothPositiveStyle[arg2], chooseClothNegativeStyle[arg2]);
				}
			} else if (operateFlag.equals(Constant.ADD_ICON_ON_CLOTH)) {
				if(isDesignCoupleCloth()){
					if(isChooseFemale){
						if(isBack){
							femaleNegativeFragment.addIconOnCloth(BitmapFactory.decodeResource(getResources(), Constant.designPatternDrawable[arg2]));
						}else{
							femalePositiveFragment.addIconOnCloth(BitmapFactory.decodeResource(getResources(), Constant.designPatternDrawable[arg2]));
						}
					}else{						
						setMaleClothIcon(Constant.designPatternDrawable[arg2]);
					}
				}else{
					setMaleClothIcon(Constant.designPatternDrawable[arg2]);
				}
			} else if (operateFlag.equals(Constant.ADD_MODEL_ON_CLOTH)) {
				if(isDesignCoupleCloth()){
					if(isChooseFemale){
						if (isBack) {
							femaleNegativeFragment.setDesignTemplateBackground(chooseClothModeStyle[arg2]);
						} else {
							femalePositiveFragment.setDesignTemplateBackground(chooseClothModeStyle[arg2]);
						}
					}else{
						setMaleModelStyle(chooseClothModeStyle[arg2]);
					}
				}else{
					setMaleModelStyle(chooseClothModeStyle[arg2]);
				}
			}
		}
	}
	
	/**
	 * 设置男士衣服的样式
	 * 
	 * @author Administrator
	 * @date 2014-12-19 下午4:24:01
	 */
	public void setMaleClothStyle(int positiveStyleId , int negativeStyleId){
		positiveFragment.setClothStyleOrColor(positiveStyleId);
		negativeFragment.setClothStyleOrColor(negativeStyleId);
	}
	
	/**
	 * 在男士衣服上面添加图案
	 * 
	 * @author Administrator
	 * @date 2014-12-19 下午4:35:58
	 * @param iconSourceId
	 */
	public void setMaleClothIcon(int iconSourceId){
		if (isBack) {
			negativeFragment.addIconOnCloth(BitmapFactory.decodeResource(getResources(), iconSourceId));
		} else {
			positiveFragment.addIconOnCloth(BitmapFactory.decodeResource(getResources(), iconSourceId));
		}
	}
	
	/**
	 * 设置男士衣服照片的模型
	 * 
	 * @author Administrator
	 * @date 2014-12-19 下午4:37:07
	 * @param modelStyleId
	 */
	public void setMaleModelStyle(int modelStyleId){
		if (isBack) {
			negativeFragment.setDesignTemplateBackground(modelStyleId);
		} else {
			positiveFragment.setDesignTemplateBackground(modelStyleId);
		}
	}
	
	/**
	 * 是否是在设置情侣装
	 * 
	 * @author Administrator
	 * @date 2014-12-22 上午10:36:41
	 * @return
	 */
	private boolean isDesignCoupleCloth(){
		if(!StringUtil.isEmpty(DESIGN_STYLE) && DESIGN_STYLE.equals(Constant.COUPLE_STYLE)){
			return true;
		}
		return false;
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(chooseClothPositiveStyle != null){
			chooseClothPositiveStyle = null;
		}
		
		if(chooseClothNegativeStyle != null){
			chooseClothNegativeStyle = null;
		}
	}
}
