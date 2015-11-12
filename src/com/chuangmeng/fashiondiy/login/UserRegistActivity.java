package com.chuangmeng.fashiondiy.login;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.forum.ForumActivity;
import com.chuangmeng.fashiondiy.util.MLog;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @ClassName: UserRegistActivity
 * @Description:用户注册
 * @author hechuang
 * @date 2015-11-10 下午5:26:26
 * 
 */
@EActivity(R.layout.activity_user_regist)
public class UserRegistActivity extends Activity {

	@ViewById
	EditText user_regist_username_et;

	@ViewById
	EditText user_regist_password_et;

	@ViewById
	EditText user_regist_phonenum_et;

	@ViewById
	EditText user_regist_validatedcode_et;

	@ViewById
	Button user_regist_get_validatedcode_bt;

	@ViewById
	Button user_regist_regist_bt;

	private AVUser user;

	private String userName = "";
	private String passWord = "";
	private String phoneNum = "";

	@AfterViews
	void initView() {
		user = new AVUser();
		//user_regist_validatedcode_et.setEnabled(false);
	}

	@Click
	void user_regist_get_validatedcode_bt() {
		userName = user_regist_username_et.getText().toString();
		passWord = user_regist_password_et.getText().toString();
		phoneNum = user_regist_phonenum_et.getText().toString();
		if (userName.length() == 0) {
			showToast("用户名不能为空!");
		}
		Pattern etUsernameRegex = Pattern
				.compile("^[\u4e00-\u9fffa-zA-Z0-9 _]+$");

		Matcher etUsernameMatcher = etUsernameRegex.matcher(userName);
		boolean etUsernameMatched = etUsernameMatcher.matches();
		if (!etUsernameMatched) {
			showToast("用户名格式不对!");
			return;
		}
		try {
			if (userName.getBytes("utf-8").length < 4
					|| userName.getBytes("utf-8").length > 20) {
				showToast("用户名请输入4-20个字符");
				return;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Pattern etPasswordRegex = Pattern.compile("[^?!@#$%\\^ &*()',=]+");
		Matcher etPasswordMatcher = etPasswordRegex.matcher(passWord);
		boolean etPasswordMatched = etPasswordMatcher.matches();
		if (!etPasswordMatched) {
			showToast("密码终不能有特殊字符或空格");
			return;
		} else if (passWord.equals("")) {
			showToast("请先输入密码！");
		} else if (passWord.length() <= 6) {
			showToast("密码长度大于6位");
		}
		Pattern emailRegex = Pattern.compile("^((1[3-8]))\\d{9}$");
		Matcher emailMatcher = emailRegex.matcher(phoneNum);
		boolean isEmailMatched = emailMatcher.matches();

		if (!isEmailMatched) {
			showToast("请填入真实的手机号码！");
			return;
		}
		user.setUsername(userName);
		user.setPassword(passWord);
		user.setMobilePhoneNumber(phoneNum);
		user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(AVException e) {
				if (filterException(e)) {
					showToast("验证码已发送 请注意查收！");
					//user_regist_validatedcode_et.setEnabled(true);
				} else {
					showToast("获取验证码失败！");
				}
			}
		});
	}

	@Click
	void user_regist_regist_bt() {
		String code = user_regist_validatedcode_et.getText().toString();
		AVUser.verifyMobilePhoneInBackground(code,
				new AVMobilePhoneVerifyCallback() {

					@Override
					public void done(AVException e) {
						if (filterException(e)) {
							showToast("注册成功！");
							Intent intent = new Intent(UserRegistActivity.this , ForumActivity.class);
							startActivity(intent);
							finish();
						} else {
							showToast("注册失败！");
						}
					}
				});
	}

	private void showToast(String message) {
		Toast.makeText(UserRegistActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	protected boolean filterException(Exception e) {
		if (e == null) {
			return true;
		} else {
			MLog.d(e.getMessage());
			return false;
		}
	}
}
