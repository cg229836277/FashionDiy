package com.chuangmeng.fashiondiy.login;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.forum.ForumActivity;
import com.chuangmeng.fashiondiy.util.NetUtil;
import com.chuangmeng.fashiondiy.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@EActivity(R.layout.activity_user_login)
public class UserLoginActivity extends Activity {
	@ViewById
	EditText login_user_name_input;
	
	@ViewById
	EditText login_user_passwd_input;
	
	@ViewById
	Button login_start_login_btn;
	
	@ViewById
	Button login_meet_error;
	
	@ViewById
	Button login_regist_first;
	
	private String userNameInput = null;
	private String userPasswdInput = null;
	
	@AfterViews
	void isNetworkAvailable(){
		if(!NetUtil.isNetWorkAvailable(this)){
			Toast.makeText(this, "世界上最遥远的距离就是没网", Toast.LENGTH_SHORT).show();
			login_start_login_btn.setEnabled(false);
		}else{
			login_start_login_btn.setEnabled(true);
		}
	}
	
	@Click
	void login_start_login_btn(){
		userNameInput = login_user_name_input.getText().toString().trim();
		userPasswdInput = login_user_passwd_input.getText().toString().trim();
		if(!StringUtil.isEmpty(userPasswdInput) && !StringUtil.isEmpty(userNameInput)){
			startLogin();
		}else{
			Toast.makeText(this, "用户名或者密码不能为空！", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Click
	void login_meet_error(){
		
	}
	
	@Click
	void login_regist_first(){
		Intent intent = new Intent(this , UserRegistActivity_.class);
		startActivity(intent);
		finish();
	}
	
	private void startLogin(){
		AVUser.logInInBackground(userNameInput, userPasswdInput, new LogInCallback<AVUser>() {
			@Override
			public void done(AVUser arg0, AVException arg1) {
				if(arg0 != null){
					Toast.makeText(UserLoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(UserLoginActivity.this , ForumActivity.class);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(UserLoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
