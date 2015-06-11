package com.chuangmeng.fashiondiy.design;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.util.BitmapUtil;
import com.chuangmeng.fashiondiy.util.MLog;
import com.chuangmeng.fashiondiy.view.DesignDetailView;

/**
 * @Title：FashionDIY
 * @Description：
 * @date 2014-12-17 下午2:44:34
 * @author Administrator
 * @version 1.0
 */

public class FemaleNegativeDesignFragment extends Fragment {
	private DesignDetailView clothNegativeView;
	private int clothMode = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fragment_negative_design);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 衣服反面
		View negativeView = inflater.inflate(R.layout.fragment_negative_design,
				null);
		clothNegativeView = (DesignDetailView) negativeView
				.findViewById(R.id.design_cloth_detail_negative);
		// clothNegativeView.setDesignViewBackgroundDrawable(R.drawable.design_main_background);

		setDefaultImage();
		negativeView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {

				// TODO Auto-generated method stub
				System.out.println("arg1::" + arg1);
			}
		});
		return negativeView;
	}

	public void setDefaultImage() {
		// String clothStyle =
		// getActivity().getIntent().getStringExtra(HomeActivity_.STYLE);
		int imageDrawable = R.drawable.design_source_female_ts_cloth_white_negative;
		// if (!StringUtil.isEmpty(clothStyle)) {
		// if (clothStyle.equals(DesignActivity_.MALE_STYLE)) {
		// imageDrawable = R.drawable.design_source_cloth_white_negative;
		// } else if (clothStyle.equals(DesignActivity_.FEMALE_STYLE)) {
		// imageDrawable =
		// R.drawable.design_source_female_ts_cloth_white_negative;
		// } else {
		//
		// }
		clothNegativeView.setDefaultClothStyle(imageDrawable);
		// }
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
		clothNegativeView.addTextOnCloth(text);
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
		// clothNegativeView.setTextSize(size);
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
		clothNegativeView.setTextColor(color);
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
		clothNegativeView.setTextStyle(fontName);
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
		clothNegativeView.hideTextDesignLayout();
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
		clothNegativeView.showTextDesignLayout();
	}

	/**
	 * 在衣服上添加图案
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:49:03
	 * @param sourceBitmap
	 */
	public void addIconOnCloth(Bitmap sourceBitmap) {
		clothNegativeView.addClothIcon(sourceBitmap);
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
		clothNegativeView.setDesignClothBorderBackground(uri);
		// BitmapUtil.loadUriImageView(getActivity().getBaseContext(),
		// clothNegativeView, uri);
	}

	public void addClothDesignPicture(String photoPath) {
		clothNegativeView.setDesignClothBorderBackground(photoPath);
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
		clothNegativeView.setDesignTemplateBackground(thisSVG);
	}

	/**
	* @Title: initPaintingRegion
	* @Description: TODO 初始化绘画区域
	* @author hechuang 
	* @date 2015-6-10
	* @param @param boyOrGirl    设定文件
	* @return void    返回类型
	*/ 
	public void initPaintingRegion() {
		MLog.d("clothMode:"+clothMode);
		clothNegativeView.setPaintingRegion(clothMode, false);
	}

	/**
	 * 更改衣服的款式或者颜色
	 * 
	 * @author chengang
	 * @date 2014-11-6 下午3:49:15
	 */
	public void setClothStyleOrColor(int drawable, int selectPos) {
		if (selectPos <= 5) {
			clothMode = 1;
			clothNegativeView.setPaintingRegion(clothMode, false);
		}
		if (selectPos > 5 && selectPos <= 11) {
			clothMode = 2;
			clothNegativeView.setPaintingRegion(clothMode, false);
		} else {
			clothMode = 3;
			clothNegativeView.setPaintingRegion(clothMode, false);
		}

		clothNegativeView.setDesignClothStyle(drawable);
		clothNegativeView.setDesignTemplateBackground(R.raw.design_model_flag);
	}

	public Bitmap getBitmapNegativeView() {
		if (clothNegativeView != null && clothNegativeView.getChildCount() > 0) {
			return BitmapUtil.getBitmapFromView(clothNegativeView);
		}
		return null;
	}

	public void hideControlView() {
		if (clothNegativeView != null) {
			clothNegativeView.hideControlView();
		}
	}
}
