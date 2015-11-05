package com.chuangmeng.fashiondiy;

import com.chuangmeng.fashiondiy.util.StringUtil;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmDialog extends Activity {

	private TextView showDetailText;
	private Button confirmBtn;
	private Button quitBtn;
	
	public final static String DETAIL_MESSAGE = "message";
	private String detailMesage = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_delete_cloth);
		
		//detailMesage = getIntent().getStringExtra(DETAIL_MESSAGE);
		detailMesage = "确认删除？";
		bindEvent();
		
		setDataToView(detailMesage);
	}
	
	public void bindEvent(){
		showDetailText = (TextView)findViewById(R.id.show_detail_text);
		confirmBtn = (Button)findViewById(R.id.confrim_delete);
		quitBtn = (Button)findViewById(R.id.quit_delete);
		
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EventBus.getDefault().post(new String("delete"));
				finish();
			}
		});
		
		quitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	public void setDataToView(String detailText){
		if(StringUtil.isEmpty(detailText)){
			return;
		}
		showDetailText.setText(detailText);
	}
}
