package com.chuangmeng.fashiondiy.preview;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PreviewNegativeFragment extends Fragment {
	
	private ImageView previewNegativeIv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_negative_design);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//衣服反面
		View negativeView = inflater.inflate(R.layout.activity_preview_detail, null);
		previewNegativeIv = (ImageView)negativeView.findViewById(R.id.preview_detail_iv);
		setNegativePreview();
		return negativeView;
	}
	
	public void setNegativePreview(){
		Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getNegativeViewBitmap();
		if(previewNegativeIv != null && currentBitmap != null){
			previewNegativeIv.setImageBitmap(currentBitmap);
		}
	}
}
