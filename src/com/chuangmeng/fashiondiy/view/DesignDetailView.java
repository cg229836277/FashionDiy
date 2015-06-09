package com.chuangmeng.fashiondiy.view;

import org.androidannotations.annotations.EView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.chuangmeng.fashiondiy.view.svg.SvgImageView;

/**
 * @Title：FashionDIY
 * @Description：设计页面的详细设计部分
 * @date 2014-10-30 下午2:23:52
 * @author chengang
 * @version 1.0
 */
public class DesignDetailView extends RelativeLayout {

	// @ViewById
	RelativeLayout design_detail_cloth_background_rl;

	// @ViewById
	ImageView design_detail_cloth_style_iv;

	SvgImageView design_detail_design_template_cssiv;

	// @ViewById
	RelativeLayout design_detail_cloth_border_rl;

	// @ViewById
	ImageView design_bottom_operate_text_style_hide_iv;

	// @ViewById
	Button design_model_operate_text_for_left_bt;

	Button design_model_operate_text_for_center_bt;

	Button design_model_operate_text_for_right_bt;
	
	Button design_model_operate_add_text_bt;

	// @ViewById
	HorizontalListView design_model_operate_text_style_listview;

	HorizontalListView design_model_operate_text_color_listview;

	LinearLayout design_bottom_operate_font_ll;

	private int borderWidth = 0;
	private int borderHeight = 0;

	private AutoResizeTextView clickButton;
	private final String Tag = "DesignDetailView";
	public MyToastDialog toastDialog;

	private SingleFingerView currentView = null;// 记录每一次点击的图案

	/**
	 * @param context
	 * @param attrs
	 */

	public DesignDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context);
	}

	public void initView(Context context) {
		View detailView = View.inflate(context, R.layout.activity_design_detail, this);
		design_detail_cloth_background_rl = (RelativeLayout) detailView.findViewById(R.id.design_detail_cloth_background_rl);
		design_detail_cloth_style_iv = (ImageView) detailView.findViewById(R.id.design_detail_cloth_style_iv);
		design_detail_design_template_cssiv = (SvgImageView) detailView.findViewById(R.id.design_detail_design_template_cssiv);
		design_detail_cloth_border_rl = (RelativeLayout) detailView.findViewById(R.id.design_detail_cloth_border_rl);
		design_bottom_operate_font_ll = (LinearLayout) detailView.findViewById(R.id.design_bottom_operate_font_ll);
		design_bottom_operate_text_style_hide_iv = (ImageView) detailView.findViewById(R.id.design_bottom_operate_text_style_hide_iv);
		design_model_operate_text_for_left_bt = (Button) detailView.findViewById(R.id.design_model_operate_text_for_left_bt);
		design_model_operate_text_for_center_bt = (Button) detailView.findViewById(R.id.design_model_operate_text_for_center_bt);
		design_model_operate_text_for_right_bt = (Button) detailView.findViewById(R.id.design_model_operate_text_for_right_bt);
		design_model_operate_add_text_bt = (Button) detailView.findViewById(R.id.design_model_operate_add_text_bt);
		design_model_operate_text_style_listview = (HorizontalListView) detailView.findViewById(R.id.design_model_operate_text_style_listview);
		design_model_operate_text_color_listview = (HorizontalListView) detailView.findViewById(R.id.design_model_operate_text_color_listview);

		initListener();
		
		toastDialog = new MyToastDialog(context);

		DisplayMetrics screenSize = FashionDiyApplication.getApplicationInstance().getScreenSize();

		ViewGroup.LayoutParams designDetailRl = design_detail_cloth_border_rl.getLayoutParams();
		// 添加图案和文字的边界长度是屏幕的三分之一
		int clothBorderHeight = (int) (screenSize.heightPixels / 3);
		// 添加图案和文字的边界宽度是屏幕的五分之二
		int clothBorderWidth = (int) (screenSize.widthPixels * 2 / 5);

		// RelativeLayout.LayoutParams params = new
		// RelativeLayout.LayoutParams(clothBorderWidth, clothBorderHeight);
		// design_detail_design_template_cssiv.setLayoutParams(params);

		borderWidth = clothBorderWidth;
		borderHeight = clothBorderHeight;
		// borderWidth = screenSize.widthPixels;
		// borderHeight = screenSize.heightPixels;

		designDetailRl.height = clothBorderHeight;
		designDetailRl.width = clothBorderWidth;
		design_detail_cloth_border_rl.setLayoutParams(designDetailRl);
	}

	/**
	 * initListener: 初始化监听事件
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	private void initListener() {
		
		design_model_operate_text_style_listview.setAdapter(new TextStyleAdapter(getContext(), Constant.designTextStyleExplain));
		design_model_operate_text_color_listview.setAdapter(new TextColorAdapter(getContext(), Constant.designTextColorExplain));

		design_bottom_operate_text_style_hide_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				design_bottom_operate_font_ll.setVisibility(View.GONE);
			}
		});
		
		design_model_operate_text_for_left_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		design_model_operate_text_for_center_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		design_model_operate_text_for_right_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		design_model_operate_add_text_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				addTextOnCloth("全民DIY");
				
			}
		});
		design_model_operate_text_style_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				setTextStyle(Constant.designTextStyle[position]);
			}
		});
		design_model_operate_text_color_listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				setTextColor(Constant.designTextColor[position]);
			}
		});
	}

	/**
	 * 设置设计界面的背景颜色
	 * 
	 * @author chengang
	 * @date 2014-10-30 下午2:49:04
	 */
	public void setDesignViewBackgroundColor(int colorValue) {
		design_detail_cloth_background_rl.setBackgroundColor(colorValue);
	}

	/**
	 * 设置背景图片
	 */
	public void setDesignViewBackgroundDrawable(int pictureDrawable) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			design_detail_cloth_background_rl.setBackgroundDrawable(getResources().getDrawable(pictureDrawable));
		} else {
			design_detail_cloth_background_rl.setBackgroundDrawable(getResources().getDrawable(pictureDrawable));
		}
	}

	/**
	 * setDesignTemplateBackground:设置绘画区域模板
	 * 
	 * @param @param thisSVG 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void setDesignTemplateBackground(int thisSVG) {
		if(design_detail_design_template_cssiv.getVisibility()==View.GONE){
			
			toastDialog.show("请先选择图片再选择图片模板！！！");
		}else{
			design_detail_design_template_cssiv.sharedConstructor(thisSVG);
		}
	}

	/**
	 * setDesignClothBorderBackground:设置绘画区域背景
	 * 
	 * @param @param borderBackground 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void setDesignClothBorderBackground(Uri uri) {
		// design_detail_design_template_cssiv.setImageResource(R.drawable.p1);
		design_detail_design_template_cssiv.setVisibility(View.VISIBLE);
//		design_detail_design_template_cssiv.setImageDrawable(borderBackground);
		BitmapUtil.loadUriImageView(getContext(), design_detail_design_template_cssiv, uri);
	}
	
	public void setDesignClothBorderBackground(String photoPath) {
		design_detail_design_template_cssiv.setVisibility(View.VISIBLE);
		BitmapUtil.loadLocalImage(getContext(), design_detail_design_template_cssiv, photoPath);
	}

	/**
	 * 设置衣服的款式
	 * 
	 * @author chengang
	 * @date 2014-10-30 下午2:57:44
	 * @param drawableSource
	 */
	public void setDesignClothStyle(int drawableSource) {
		design_detail_cloth_style_iv.setImageResource(drawableSource);
	}

	/**
	 * 设置默认的衣服样式
	 * 
	 * @param pictureResource
	 */
	public void setDefaultClothStyle(int pictureResource) {
		design_detail_cloth_style_iv.setImageResource(pictureResource);
	}

	/**
	 * 设置衣服上面的图标,还需要压缩图片大小
	 * 
	 * @author chengang
	 * @date 2014-10-30 下午3:02:38
	 * @param drawablePath
	 */
	public void addClothIcon(String iconPath) {
		if (StringUtil.isEmpty(iconPath)) {
			return;
		}
		ImageView clothIconImage = new ImageView(getContext());
		clothIconImage.setScaleType(ScaleType.FIT_CENTER);
		clothIconImage.setImageBitmap(BitmapFactory.decodeFile(iconPath));
		// RelativeLayout.LayoutParams params = new
		// RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);
		// params.addRule(RelativeLayout.CENTER_IN_PARENT);
		design_detail_cloth_border_rl.addView(clothIconImage);
	}

	/**
	 * 设置衣服上面的图标，还需要压缩图片大小
	 * 
	 * @author chengang
	 * @date 2014-10-30 下午3:02:38
	 * @param drawablePath
	 */
	public void addClothIcon(Bitmap iconBitmap) {
		if (iconBitmap == null) {
			return;
		}

		final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		final SingleFingerView clothIconImage = new SingleFingerView(getContext(), iconBitmap);
		design_detail_cloth_border_rl.addView(clothIconImage, params);
	}

	/**
	 * 设置图片操作（删除旋转操作）的显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-20 下午3:50:03
	 * @param imageView
	 */
	public void setIconImageState(SingleFingerView imageView) {
		if (imageView.getTag() instanceof Boolean) {
			boolean flag = (Boolean) imageView.getTag();
			if (flag) {
				// imageView.hideImageViewBackgroundColor();
				imageView.setTag(false);
			} else {
				// imageView.setImageViewBackgroundColor();
				imageView.setTag(true);
			}
		}
	}

	/**
	 * 添加衣服上面的文字
	 * 
	 * @param clothText
	 * @param textSize
	 * @param textColor
	 */
	public void addTextOnCloth(String clothText) {
		if (StringUtil.isEmpty(clothText)) {
			return;
		}
		MatrixTextView clothWord = new MatrixTextView(getContext(),new DesignDetailTextTouchInterface() {
			
			@Override
			public void touchListener(AutoResizeTextView touchView) {
				clickButton = (AutoResizeTextView)touchView;
			}

			@Override
			public void twoTouch() {
				
			}
		});
		clickButton = clothWord.getTextView();		
		clothWord.setTextString(clothText);
		clothWord.setTextColor(getResources().getColor(R.color.color_333333));
		clothWord.setBackgroundColor(Color.TRANSPARENT);
		clothWord.setTextAlign(Gravity.CENTER_VERTICAL);
		design_detail_cloth_border_rl.addView(clothWord);
//		clothWord.setMViewClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				hideTextDesignLayout();
//				clickButton = (AutoResizeTextView)view;
//				design_bottom_operate_font_ll.setVisibility(View.VISIBLE);
//				textDesignLayoutDialog(clickButton.getText().toString()).show();
//			}
//		});
	}

//	private Dialog textDesignLayoutDialog(String editText) {
//		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//		View dialogView = layoutInflater.inflate(R.layout.dialog_text_edit_dialog, null);
//		final Dialog alertDialog = new Dialog(getContext(), R.style.CustomDialog);
//		alertDialog.setContentView(dialogView);
//		alertDialog.show();
//		final EditText dialogEdit = (EditText) dialogView.findViewById(R.id.dialog_design_layout_edit_et);
//		dialogEdit.setText(editText);
//		dialogView.findViewById(R.id.dialog_design_layout_delete_bt).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				alertDialog.dismiss();
//			}
//		});
//		dialogView.findViewById(R.id.dialog_design_layout_ok_bt).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				alertDialog.dismiss();
//				clickButton.setText(dialogEdit.getText().toString());
//			}
//		});
//		return alertDialog;
//	}

	/**
	 * setTextColor:设置文字颜色
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @author hch
	 * @since CodingExample　Ver 1.1
	 */
	public void setTextColor(String color) {
		if (clickButton != null) {
			clickButton.setTextColor(Color.parseColor(color));
		}
	}

	/**
	 * setTextStyle:设置文字样式
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @author hch
	 * @since CodingExample　Ver 1.1
	 */
	public void setTextStyle(String fontName) {
		if (clickButton != null) {
			Typeface textFontFace = Typeface.createFromAsset(getContext().getAssets(), fontName);
			if (textFontFace != null) {
				clickButton.setTypeface(textFontFace);
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
		design_bottom_operate_font_ll.setVisibility(View.GONE);
	}
	/**
	 * hideTextDesignLayout:显示文字编辑框
	 * 
	 * @param 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void showTextDesignLayout() {
		design_bottom_operate_font_ll.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置设计边界线的显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-21 下午2:24:49
	 */
	public void setBorderLineVisiable(boolean isShow) {
		Log.e(Tag, "is show or not");
		if (isShow) {
			design_detail_cloth_border_rl.setBackgroundResource(R.drawable.design_border_line);
		} else {
			design_detail_cloth_border_rl.setBackgroundResource(0);
		}
	}
	
	/**
	 * ClassName:TextStyleAdapter 
	 * Function: 字体样式adapter
	 * 
	 * @author hch
	 * @version DesignActivity
	 * @since Ver 1.1
	 * @Date 2014 2014年11月24日 下午8:36:32
	 * 
	 */
	public class TextStyleAdapter extends BaseAdapter {

		private Context mContext;
		private Integer[] mTextes;

		public TextStyleAdapter(Context c, Integer[] textes) {
			mContext = c;
			this.mTextes = textes;
		}

		@Override
		public int getCount() {
			return mTextes.length;

		}

		@Override
		public Object getItem(int position) {
			return mTextes[position];

		}

		@Override
		public long getItemId(int position) {
			return position;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = new ViewHolder();
//			 if (convertView == null) {
//				 convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text_type_item,null);
//				 holder.dedsign_text_style_tv = (TextView) convertView.findViewById(R.id.dedsign_text_style_tv);  
//				 convertView.setTag(holder);  
//		        } else {  
//		        	holder = (ViewHolder) convertView.getTag(); 
//	            }
////			 holder.dedsign_text_style_tv.setText(mTextes[position]);
//			 holder.dedsign_text_style_tv.setText("ni妈蛋");
			
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setPadding(0, 5, 10, 5);
			imageView.setBackgroundResource(mTextes[position]);
			return imageView;

		}
	}

	/**
	 * ClassName:TextColorAdapter 
	 * Function: 设置字体颜色adapter
	 * 
	 * @author hch
	 * @version DesignActivity
	 * @since Ver 1.1
	 * @Date 2014 2014年11月24日 下午8:42:24
	 */
	public class TextColorAdapter extends BaseAdapter {

		private Context mContext;
		private Integer[] textColors;

		public TextColorAdapter(Context context, Integer[] colors) {
			this.mContext = context;
			this.textColors = colors;
		}

		@Override
		public int getCount() {

			// TODO Auto-generated method stub
			return textColors.length;

		}

		@Override
		public Object getItem(int position) {

			// TODO Auto-generated method stub
			return textColors[position];

		}

		@Override
		public long getItemId(int position) {

			// TODO Auto-generated method stub
			return position;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setPadding(0, 5, 10, 5);
			imageView.setBackgroundResource(textColors[position]);
			return imageView;
		}
	}
	
	/**
	 * 隐藏控制按钮（删除，旋转）
	 * author:陈刚
	 * 2014-12-1
	 * descript:
	 */
	public void hideControlView(){
		if(design_detail_cloth_border_rl != null){
			int childCount = design_detail_cloth_border_rl.getChildCount();
			if(childCount > 0){
				for(int i = 0 ; i < childCount ; i++){
					View currentView = design_detail_cloth_border_rl.getChildAt(i);
					if(currentView != null){
						if(currentView instanceof MatrixTextView){
							MatrixTextView view = (MatrixTextView)currentView;
							view.hideControlView();
						}
						if(currentView instanceof SingleFingerView){
							SingleFingerView view = (SingleFingerView)currentView;
							view.hideControlView();
						}
					}
				}
			}
		}
	}
}
