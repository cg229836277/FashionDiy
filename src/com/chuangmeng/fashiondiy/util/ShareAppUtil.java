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

import android.app.Activity;
import android.content.Context;

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

	public void ShareAppUtil(Context context) {
		this._context = context;
	}

	/**
	 * 
	 * @author hch
	 * @date 2014年10月30日 上午12:37:25
	 * @param shareContent  分享文案
	 * @param shareClickUrl 分享点击跳转url
	 * @param shareImageUrl 分享图片url
	 * @param shareMessage  分享title提示信息
	 */

	private void shareApp(String shareContent, String shareClickUrl, String shareImageUrl, String shareMessage) {

//		mController.setShareContent(shareContent);
//
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(_context, Constant.QQ_APPID, Constant.QQ_APPKEY);
//		qqSsoHandler.addToSocialSDK();
//		QQShareContent qqShareContent = new QQShareContent();
//
//		qqShareContent.setShareContent(shareContent);
//
//		qqShareContent.setTitle("好消息！！！");
//
//		qqShareContent.setShareImage(new UMImage(_context, shareImageUrl));
//
//		qqShareContent.setTargetUrl(shareClickUrl);
//
//		mController.setShareMedia(qqShareContent);
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(_context, Constant.QQ_APPID, Constant.QQ_APPKEY);
//		qZoneSsoHandler.addToSocialSDK();
//
//		QZoneShareContent qZoneShareContent = new QZoneShareContent();
//
//		qZoneShareContent.setShareContent(shareContent);
//
//		qZoneShareContent.setTitle("好消息！！！");
//
//		qZoneShareContent.setTargetUrl(shareClickUrl);
//
//		mController.setShareMedia(qZoneShareContent);
//
//		// ---------------------QQ------------
//
//		// ---------------------微信------------
//
//		UMImage urlImage = new UMImage(_context, shareImageUrl);
//		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//
//		// 微信图文分享必须设置一个url
//		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
//		UMWXHandler wxHandler = new UMWXHandler(_context, Constant.WX_APPID, Constant.WX_APPKEY);
//		wxHandler.addToSocialSDK();
//		WeiXinShareContent weixinContent = new WeiXinShareContent();
//		weixinContent.setShareContent(shareContent);
//		weixinContent.setTitle("好消息！！！");
//		weixinContent.setTargetUrl(shareClickUrl);
//		weixinContent.setShareImage(urlImage);
//		mController.setShareMedia(weixinContent);
//		// 支持微信朋友圈
//		// 设置朋友圈分享的内容
//		// mController.getConfig().supportWXCirclePlatform(_context, WX_APPID,
//		// messages.get("url"));
//		UMWXHandler wxCircleHandler = new UMWXHandler(_context, Constant.WX_APPID, Constant.WX_APPKEY);
//		wxCircleHandler.setToCircle(true);
//		wxCircleHandler.addToSocialSDK();
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(shareContent);
//		circleMedia.setTitle("好消息！！！");
//		circleMedia.setShareImage(urlImage);
//		mController.setShareMedia(circleMedia);
//
//		// ---------------------微信------------
//		SmsHandler smsHandler = new SmsHandler();
//		smsHandler.addToSocialSDK();
//		SmsShareContent smsShareContent = new SmsShareContent();
//		smsShareContent.setShareContent(shareContent + shareClickUrl);
//		mController.setShareMedia(smsShareContent);
//		mController.getConfig().setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SMS, SHARE_MEDIA.TENCENT);
//		mController.openShare(_context, false);
	}
}
