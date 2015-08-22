package com.chuangmeng.fashiondiy;

import java.util.Timer;
import java.util.TimerTask;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import android.content.Intent;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.guide.GuideActivity;
import com.chuangmeng.fashiondiy.util.SimpleSharedPreferences;

/**
 * 程序启动之后的介绍及过渡页面
 * 
 * @Title：FashionDIY
 * @Description：
 * @date 2014-10-29 上午9:38:33
 * @author chengang
 * @version 1.0
 */

@EActivity(R.layout.activity_main)
public class IndexActivity extends BaseFragmentActivity {

	private static final long DELAY = 2000;
	private boolean scheduled = false;
	private Timer splashTimer;

	@AfterViews
	void initData() {
		
		boolean isFirstIn = SimpleSharedPreferences.getBoolean("bisFirstIn", IndexActivity.this);
		if(!isFirstIn){//默认返回的是true
			goGuide();
			return;
		}

		splashTimer = new Timer();
		splashTimer.schedule(new TimerTask() {
			@Override
			public void run() {				
				Intent intent = new Intent(IndexActivity.this,HomeActivity_.class);
				startActivity(intent);
				overridePendingTransition(R.anim.view_in_top,R.anim.view_out_bottom);	
				
				IndexActivity.this.finish();
			}
		}, DELAY);
		scheduled = true;
	}
	
	public void goGuide(){		
		Intent intent = new Intent(this , GuideActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(splashTimer == null){
			return;
		}
		if (scheduled){
			splashTimer.cancel();
		}
		splashTimer.purge();			
	}
}
