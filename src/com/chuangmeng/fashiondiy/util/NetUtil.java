package com.chuangmeng.fashiondiy.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetUtil {
	public static boolean isNetWorkAvailable(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm != null) {   
        	if(cm.getActiveNetworkInfo().isAvailable()){
        		return true;
        	}
        }   
        return false;  
	}
}
