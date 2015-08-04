package com.chuangmeng.fashiondiy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

public class DisplayDesignClothActivity extends Activity {
	private ListView showPictureListView;
	private ImageView expandImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_design_cloth);
		
		bindEvent();
	}
	
	private void bindEvent(){
		showPictureListView = (ListView)findViewById(R.id.show_design_cloth);
		expandImageView = (ImageView)findViewById(R.id.expanded_image);
	}
}
