package com.chuangmeng.fashiondiy.util;

import java.util.List;

/**
 * @Title：SAFEYE@
 * @Description：数组不为空的判断
 * @date 2014-7-31 上午11:28:59
 * @author chengang
 * @version 1.0
 */
public class IsListNotNull {
	public static <T> boolean isListNotNull(List<T> listData){
		if(listData != null && listData.size() != 0){
			return true;
		}
		return false;
	}
}
