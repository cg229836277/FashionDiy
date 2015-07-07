package com.chuangmeng.fashiondiy.guide;

import com.chuangmeng.fashiondiy.HomeActivity_;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.util.SimpleSharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideActivity extends BaseFragmentActivity implements OnPageChangeListener {

	private ViewPager vp;
	private GuidePageAdapter vpAdapter;
	private Integer[] guideViewArray = {
			R.layout.activity_what_new_one,
			R.layout.activity_what_new_two,
			R.layout.activity_what_new_three,
			R.layout.activity_what_new_four,
			R.layout.activity_what_new_five
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_design_guide);
		initViews();
	}

	private void initViews() {
		vpAdapter = new GuidePageAdapter();
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {

	}
	
	private class GuidePageAdapter extends PagerAdapter{
		
		private LayoutInflater inflater;

		public GuidePageAdapter(){
			inflater = LayoutInflater.from(GuideActivity.this);
		}
		
		@Override
		public int getCount() {
			if (guideViewArray != null) {
				return guideViewArray.length;
			}
			return 0;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			System.out.println("" + position);
			((ViewPager)container).removeView(inflater.inflate(guideViewArray[position], null));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View childView = inflater.inflate(guideViewArray[position], null);
			container.addView(childView);	
			
			if(position == guideViewArray.length - 1){
				ImageView childImageView = (ImageView)childView.findViewById(R.id.show_guide_last);
				childImageView.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						setGuided();
						goHome();
						return true;
					}
				});
			}
			return childView;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;  
		}
	}
	

	private void setGuided() {
		SimpleSharedPreferences.setBoolean("bisFirstIn", true, this); 
	}

	private void goHome() {
		startActivity(new Intent(this, HomeActivity_.class));
		finish();
	}
}
