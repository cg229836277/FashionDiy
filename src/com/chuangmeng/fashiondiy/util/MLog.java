package com.chuangmeng.fashiondiy.util;

import android.util.Log;

/**
* @ClassName: MLog
* @Description: TODO 自定义log类
* @author hechuang
* @date 2015-6-11 上午9:31:52
*
*/             
public class MLog {
	public static void d(String msg) {
		if (Constant.debug)
			Log.d("TAG", msg);
	}
	public static void d(String tag, String msg) {
		if (Constant.debug)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (Constant.debug)
			Log.i(tag, msg);
	}
	
	public static void w(String tag, String msg) {
		if (Constant.debug)
			Log.w(tag, msg);
	}
	
	public static void e(String tag, String msg) {
		if (Constant.debug)
			Log.e(tag, msg);
	}
}
