/**
 * HomeActivity.java
 * com.chuangmeng.fashiondiy.home
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-11-16 		hch
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.chuangmeng.fashiondiy.home;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.chuangmeng.fashiondiy.HomeActivity_;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.forum.ForumActivity_;
import com.chuangmeng.fashiondiy.my.UserActivity_;
import com.chuangmeng.fashiondiy.setting.SettingActivity_;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

/**
 * ClassName:HomeActivity
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   hch
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-11-16		下午8:17:13
 *
 */

@EActivity(R.layout.activity_entry)
public class EntryActivity extends Activity {

	@ViewById
	ImageView home_item_desgin_iv;
	
	@ViewById
	ImageView home_item_forum_iv;
	
	@ViewById
	ImageView home_item_my_iv;
	
	@ViewById
	ImageView home_item_setting_iv;
	
	@AfterViews
	void init(){
		
	}
	
	@Click
	void home_item_desgin_iv(){
		startActivity(new Intent(EntryActivity.this, HomeActivity_.class));
	}
	@Click
	void home_item_forum_iv(){
		startActivity(new Intent(EntryActivity.this, ForumActivity_.class));
	}
	@Click
	void home_item_my_iv(){
		startActivity(new Intent(EntryActivity.this, UserActivity_.class));
	}
	@Click
	void home_item_setting_iv(){
		startActivity(new Intent(EntryActivity.this, SettingActivity_.class));
	}
}

