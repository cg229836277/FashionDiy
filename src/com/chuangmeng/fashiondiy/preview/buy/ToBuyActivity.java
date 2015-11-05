package com.chuangmeng.fashiondiy.preview.buy;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TextView;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.view.viewflow.CircleFlowIndicator;
import com.chuangmeng.fashiondiy.view.viewflow.ImageAdapter;
import com.chuangmeng.fashiondiy.view.viewflow.ViewFlow;

@EActivity(R.layout.activity_preview_buy_show)
public class ToBuyActivity extends BaseFragmentActivity {
	
	@ViewById
	ViewFlow design_preview_buy_show_vf;
	
	@ViewById
	CircleFlowIndicator design_preview_buy_show_cfi;
	
	@ViewById
	Button design_buy_cloth_no_s_bt;
	
	@ViewById
	Button design_buy_cloth_no_m_bt;
	
	@ViewById
	Button design_buy_cloth_no_l_bt;
	
	@ViewById
	Button design_buy_cloth_no_xl_bt;
	
	@ViewById
	Button design_buy_cloth_no_xxl_bt;
	
	@ViewById
	Button design_buy_cloth_no_xxxl_bt;
	
	@ViewById
	Button design_buy_cloth_num_sub_bt;
	
	@ViewById
	TextView design_buy_cloth_num_tv;
	
	@ViewById
	Button design_buy_cloth_num_add_bt;
	
	@ViewById
	TextView design_buy_cloth_money_tv;
	
	@ViewById
	Button design_buy_cloth_ok_bt;
	
	
	
	@AfterViews
	void initData(){
		Boolean isLovers = getIntent().getBooleanExtra("isLovers",false);
		ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
		bitmapArray.add(FashionDiyApplication.getInstance()
				.getPositiveViewBitmap());
		bitmapArray.add(FashionDiyApplication.getInstance()
				.getNegativeViewBitmap());
		if (isLovers) {
			bitmapArray.add(FashionDiyApplication.getInstance()
					.getFemalePositiveBitmap());
			bitmapArray.add(FashionDiyApplication.getInstance()
					.getFemaleNegativeBitmap());
		}
		design_preview_buy_show_vf.setAdapter(new ImageAdapter(this , bitmapArray), 0);	
		design_preview_buy_show_vf.setFlowIndicator(design_preview_buy_show_cfi);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		design_preview_buy_show_vf.onConfigurationChanged(newConfig);
	}

}
