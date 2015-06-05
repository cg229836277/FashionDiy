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


 /**
 * @Title：FashionDIY
 * @Description：
 * @date 2014-12-22 上午11:06:39
 * @author Administrator
 * @version 1.0
 */

public class PreviewFemaleNegativeFragment extends Fragment{

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
		Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getFemaleNegativeBitmap();
		if(previewPositiveIv != null && currentBitmap != null){
			previewPositiveIv.setImageBitmap(currentBitmap);
		}
	}
}
