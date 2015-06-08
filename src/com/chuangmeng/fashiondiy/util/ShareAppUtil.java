/**
 * ShareAppUtil.java
 * com.chuangmeng.fashiondiy.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年10月30日 		hch
 *
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.chuangmeng.fashiondiy.util;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * ClassName:ShareAppUtil Function: 应用分享工具 Reason: TODO ADD REASON
 * 
 * @author hch
 * @version
 * @since Ver 1.1
 * @Date 2014年10月30日 上午12:27:01
 * 
 */
public class ShareAppUtil {

	private Context _context;

	public ShareAppUtil(Context context) {
		this._context = context;
	}

	/**
	* @Title: shareAppForText
	* @Description: TODO 分享文字
	* @author hechuang 
	* @date 2015-6-8
	* @param @param shareText    要分享的内容
	* @return void    返回类型
	*/ 
	public void shareAppForText(String shareText) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.setType("text/plain");

		// 设置分享列表的标题，并且每次都显示分享列表
		_context.startActivity(Intent.createChooser(shareIntent, "分享到"));
	}

	/**
	 * @Title: shareApp
	 * @Description: TODO 分享一张图片
	 * @author hechuang
	 * @date 2015-6-8
	 * @param @param activityTitle
	 * @param @param imgPath 图片的路径
	 * @return void 返回类型
	 */
	public void shareAppForOneImage(String imgPath) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			Toast.makeText(_context, "没有选择图片！！！", 1).show();
			return;
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(Intent.createChooser(intent, "分享到"));
	}

	/**
	 * @Title: shareAppForMany
	 * @Description: TODO 分享多张图片
	 * @author hechuang
	 * @date 2015-6-8
	 * @param @param activityTitle
	 * @param @param imgPaths 设定文件
	 * @return void 返回类型
	 */
	public void shareAppForManyImage(ArrayList<String> imgPaths) {
		ArrayList<Uri> uriList = new ArrayList<Uri>();
		for (int i = 0; i < imgPaths.size(); i++) {
			uriList.add(Uri.fromFile(new File(imgPaths.get(i))));
		}
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
		shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
		shareIntent.setType("image/*");
		_context.startActivity(Intent.createChooser(shareIntent, "分享到"));
	}
}
