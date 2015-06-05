package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.preview.PreViewActivity;
import com.chuangmeng.fashiondiy.preview.PreviewFemaleNegativeFragment;
import com.chuangmeng.fashiondiy.preview.PreviewFemalePositiveFragment;
import com.chuangmeng.fashiondiy.preview.PreviewNegativeFragment;
import com.chuangmeng.fashiondiy.preview.PreviewPositiveFragment;
import com.chuangmeng.fashiondiy.util.StringUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PreviewDetailPagerAdapter extends FragmentPagerAdapter {
	
	private static boolean designCouple = false;

	public PreviewDetailPagerAdapter(FragmentManager fm , String designStyle) {
		super(fm);
		if(!StringUtil.isEmpty(designStyle) && designStyle.equals(PreViewActivity.PREVIEW_COUPLE)){
			designCouple = true;
		}
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			return new PreviewPositiveFragment();
		case 1:
			return new PreviewNegativeFragment();
		case 2:
			if(designCouple){
				return new PreviewFemalePositiveFragment();
			}
		case 3:
			if(designCouple){
				return new PreviewFemaleNegativeFragment();
			}
		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		if(designCouple){
			return 4;
		}
		return 2;
	}

}
