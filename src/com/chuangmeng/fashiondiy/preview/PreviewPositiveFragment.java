package com.chuangmeng.fashiondiy.preview;

import java.util.ArrayList;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.util.CollectionUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PreviewPositiveFragment extends Fragment {
	
	private ImageView previewPositiveIv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_negative_design);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//衣服正面
		View positiveView = inflater.inflate(R.layout.activity_preview_detail, null);
		previewPositiveIv = (ImageView)positiveView.findViewById(R.id.preview_detail_iv);
		setPositivePreview();
		return positiveView;
	}
	
	public void setPositivePreview(){
		FashionDiyApplication appInstace = FashionDiyApplication.getApplicationInstance();
		if(!CollectionUtil.isArrayListNull(appInstace.getBitmaps())){
			ArrayList<Bitmap> tempList = appInstace.getBitmaps();
			if(previewPositiveIv != null && tempList.size() >= 2){
				previewPositiveIv.setImageBitmap(tempList.get(0));
			}
		}
	}
}
