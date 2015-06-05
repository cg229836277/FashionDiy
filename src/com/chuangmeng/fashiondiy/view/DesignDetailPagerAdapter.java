package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.design.FemaleNegativeDesignFragment;
import com.chuangmeng.fashiondiy.design.FemalePositiveDesignFragment;
import com.chuangmeng.fashiondiy.design.NegativeDesignFragment;
import com.chuangmeng.fashiondiy.design.PositiveDesignFragment;
import com.chuangmeng.fashiondiy.util.StringUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


 /**
 * @Title：FashionDIY
 * @Description：设计页面viewpager的适配器
 * @date 2014-10-31 下午1:20:44
 * @author chengang
 * @version 1.0
 */

public class DesignDetailPagerAdapter extends FragmentPagerAdapter {
	private static String mDesignStyle = null;//设计类型
	
	/**
	 * 
	 * @param fm
	 */
	public DesignDetailPagerAdapter(FragmentManager fm , String designStyle) {
		super(fm);
		mDesignStyle = designStyle;
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			return new PositiveDesignFragment();
		case 1:
			return new NegativeDesignFragment();
		case 2:
			if(!StringUtil.isEmpty(mDesignStyle)){
				return new FemalePositiveDesignFragment();
			}		
		case 3:
			if(!StringUtil.isEmpty(mDesignStyle)){
				return new FemaleNegativeDesignFragment();
			}
		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		if(!StringUtil.isEmpty(mDesignStyle)){
			return 4;
		}
		return 2;
	}
}
