package com.chuangmeng.fashiondiy.design;

import com.chuangmeng.fashiondiy.R;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.chuangmeng.fashiondiy.view.DesignDetailView;

/**
 * 衣服正面,如果是设计情侣页面默认是男士正面
 * 
 * @Title：FashionDIY
 * @Description：
 * @date 2014-10-31 下午1:28:56
 * @author chengang
 * @version 1.0
 */
public class PositiveDesignFragment extends Fragment {

	private DesignDetailView clothPositiveView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 衣服正面
		View positiveView = inflater.inflate(R.layout.fragment_positive_design, null);
		clothPositiveView = (DesignDetailView) positiveView.findViewById(R.id.design_cloth_detail_positive);
//		clothPositiveView.setDesignViewBackgroundDrawable(R.drawable.design_main_background);

		setDefaultImage();

		return positiveView;
	}

	public void setDefaultImage() {
		String clothStyle = getActivity().getIntent().getStringExtra(Constant.STYLE);
		int imageDrawable = 0;
		if (!StringUtil.isEmpty(clothStyle)) {
			if (clothStyle.equals(Constant.MALE_STYLE)) {
				imageDrawable = R.drawable.design_source_cloth_white_positive;
			} else if (clothStyle.equals(Constant.FEMALE_STYLE)) {
				imageDrawable = R.drawable.design_source_female_ts_cloth_white_positive;
			} else {
				imageDrawable = R.drawable.design_source_cloth_white_positive;
			}
			clothPositiveView.setDefaultClothStyle(imageDrawable);
		}
	}

	/**
	 * 在衣服上添加文字
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:48:48
	 * @param text
	 * @param size
	 * @param color
	 */
	public void addTextOnCloth(String text) {
		clothPositiveView.addTextOnCloth(text);
	}

	/**
	 * setTextSize:设置文字大小
	 * 
	 * @param @param size 设定文件
	 * @return void DOM对象
	 * @throws
	 * @author hch
	 * @since CodingExample　Ver 1.1
	 */
	public void setTextSize(float size) {
//		clothPositiveView.setTextSize(size);
	}

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
		clothPositiveView.setTextColor(color);
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
		clothPositiveView.hideTextDesignLayout();
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
		clothPositiveView.showTextDesignLayout();
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
		clothPositiveView.setTextStyle(fontName);
	}

	/**
	 * 在衣服上添加图片
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:49:03
	 * @param sourceBitmap
	 */
	public void addIconOnCloth(Bitmap sourceBitmap) {
		clothPositiveView.addClothIcon(sourceBitmap);
	}

	/**
	 * setDesignClothBorderBackground:设置绘画区域背景
	 * 
	 * @param @param borderBackground 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public void addClothDesignPicture(Uri uri) {
		clothPositiveView.setDesignClothBorderBackground(uri);
	}
	
	public void addClothDesignPicture(String photoPath) {
		clothPositiveView.setDesignClothBorderBackground(photoPath);
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
		clothPositiveView.setDesignTemplateBackground(thisSVG);
	}
	
	/**
	 * 更改衣服的款式或者颜色
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:49:15
	 */
	public void setClothStyleOrColor(int drawable) {
		clothPositiveView.setDesignClothStyle(drawable);
	}

	public Bitmap getBitmapPositiveView() {
		if (clothPositiveView != null && clothPositiveView.getChildCount() > 0) {
			return BitmapUtil.getBitmapFromView(clothPositiveView);
		}
		return null;
	}
	
	public void hideControlView(){
		if(clothPositiveView != null){
			clothPositiveView.hideControlView();
		}
	}
}
