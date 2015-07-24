package com.chuangmeng.fashiondiy.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CollectionUtil {
	/**
	 * 
	 * 如果List为空的话返回true，否则返回false
	 * @author admin
	 * @date 2015-4-22 上午9:59:46
	 * @param listData
	 * @return
	 */
	public static <T> boolean isListNull(List<T> listData){
		if(listData != null && listData.size() != 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 如果ArrayList为空的话返回true，否则返回false
	 * @author admin
	 * @date 2015-4-22 上午9:59:46
	 * @param listData
	 * @return
	 */
	public static <T> boolean isArrayListNull(ArrayList<T> listData){
		if(listData != null && listData.size() != 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 如果Map为空的话返回true，否则返回false
	 * @author admin
	 * @date 2015-4-22 上午9:59:46
	 * @param mapData
	 * @return
	 */
	public static <K , V> boolean isMapNull(Map<K , V> mapData){
		if(mapData != null && mapData.size() != 0){
			return false;
		}
		return true;
	}
	
	/**
	 * map转化为list
	 * 
	 * @author admin
	 * @param <K> map键
	 * @param <T> map值
	 * @date 2015-5-9 下午2:42:27
	 * @param mapData
	 * @return
	 */
	public static <K, T> List<T> map2List(Map<K , T> mapData){
		List<T> list = null;
		if(!isMapNull(mapData)){
			Set<Entry<K,T>> keySet= mapData.entrySet();
			Iterator<Entry<K,T>> iter = keySet.iterator();
			
			list = new ArrayList<T>();		
			while(iter.hasNext()){
				Map.Entry<K,T> value = iter.next();
				list.add(value.getValue());
			}
		}
		return list;
	}
}
