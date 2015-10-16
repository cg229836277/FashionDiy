/**
 * PreViewActivity.java
 * com.chuangmeng.fashiondiy.preview
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年10月30日 		hch
 *
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.chuangmeng.fashiondiy.preview;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.chuangmeng.fashiondiy.DisplayGarderobeActivity;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.preview.trywear.WaterCameraActivity;
import com.chuangmeng.fashiondiy.service.SaveTrywearClothService;
import com.chuangmeng.fashiondiy.util.Constant;
import com.chuangmeng.fashiondiy.util.StringUtil;
import com.chuangmeng.fashiondiy.view.PreviewDetailPagerAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
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
 * ClassName:PreViewActivity Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author hch
 * @version 
 * @since Ver 1.1
 * @Date 2014年10月30日 下午9:33:11
 * 
 */ 
@EActivity(R.layout.activity_preview)
public class PreViewActivity extends BaseFragmentActivity implements OnPageChangeListener{

	@ViewById
	LinearLayout design_cloth_direction_rl;
	 
	@ViewById
	LinearLayout design_couple_cloth_direction_rl;

	@ViewById
	LinearLayout preview_bottom_detail_ll;
	
	@ViewById
	Button preview_title_back_iv;

	//普通预览前
	@ViewById
	Button design_title_forward_iv;

	//普通预览后
	@ViewById
	Button design_title_backward_iv;
	
	//情侣男预览前
	@ViewById
	Button design_couple_title_male_front_iv;
	
	//情侣男预览后
	@ViewById
	Button design_couple_title_male_back_iv;
		
	//情侣女预览前
	@ViewById
	Button design_couple_title_female_front_iv;
	
	//情侣女预览后
	@ViewById
	Button design_couple_title_female_back_iv;

	@ViewById
	Button preview_to_clothset;

	@ViewById
	ImageView preview_bottom_save_iv;

	@ViewById
	ImageView preview_bottom_try_iv;

	@ViewById
	ImageView preview_bottom_buy_iv;
	
	public final static String PREVIEW_COUPLE = "couple";
	
	private String designStyle = null;
	
	private boolean isChooseFemale = false; 
	
	private boolean isSaved = false;
	
	@ViewById
	ViewPager preview_cloth_detail_viewpager;

	private UMSocialService mController;
	
	@AfterViews
	void initData() {
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		
		designStyle = getIntent().getStringExtra(PREVIEW_COUPLE);
		
		if(isPreviewCoupleCloth()){
			design_cloth_direction_rl.setVisibility(View.GONE);
			design_couple_cloth_direction_rl.setVisibility(View.VISIBLE);
		}else{
			design_cloth_direction_rl.setVisibility(View.VISIBLE);
			design_couple_cloth_direction_rl.setVisibility(View.GONE);
		}

		initFragmentAdapter();		
	}

//	@Click
//	void preview_title_share_iv() {
//		//shareApp("test", "test", "test", "test");
//		String shareImageUrl = "http://d.hiphotos.baidu.com/image/pic/item/b3fb43166d224f4ae33532120bf790529822d107.jpg";
////		shareApp(Constant.shareContent, Constant.shareClickUrl, shareImageUrl, Constant.shareTitle);
//	}
	@Click
	public void preview_to_clothset() {
		Intent intent = new Intent(this,DisplayGarderobeActivity.class);
		startActivity(intent);
	}
	public boolean isPreviewCoupleCloth(){
		if(!StringUtil.isEmpty(designStyle) && designStyle.equals(PREVIEW_COUPLE)){
			return true;
		}
		return false;
	}

	/**
	 * 保存本地操作
	 * 循环两次，第一次保存正面的图片，第二次保存反面的图片
	 * @author hch
	 * @date 2014年11月1日 下午1:37:53
	 */
	@Click
	void preview_bottom_save_iv() {
		
		if(isSaved){
			Toast.makeText(this, "衣服已经保存！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent(this , SaveTrywearClothService.class);
		intent.putExtra(SaveTrywearClothService.SAVE_PICTURE_TYPE, "preview");
		startService(intent);
		
		Toast.makeText(this, "点击右上角到衣柜查看设计的衣服", Toast.LENGTH_SHORT).show();
		
		isSaved = true;
	}

	/**
	 * 试穿按钮操作
	 * 
	 * @author hch
	 * @date 2014年11月1日 下午1:37:39
	 */
	@Click
	void preview_bottom_try_iv() {
		Intent intent = new Intent(this , WaterCameraActivity.class);
		intent.putExtra(WaterCameraActivity.CHOOSED_DESIGN_CLOTH_LIST, "design");
		startActivity(intent);
	}

	/**
	 * 购买按钮操作
	 * 
	 * @author hch
	 * @date 2014年11月1日 下午1:37:20
	 */
	@Click
	void preview_bottom_buy_iv() {		
		Toast.makeText(this, "功能开发中，敬请期待！", Toast.LENGTH_SHORT).show();
		
//		Intent intent = new Intent();
//		intent.putExtra("isLovers", isPreviewCoupleCloth());
//		intent.setClass(PreViewActivity.this,ToBuyActivity_.class);
//		startActivity(intent);
	}
	
	@Click
	public void preview_title_back_iv(){
		finish();
	}

	public void initFragmentAdapter() {
		if(isPreviewCoupleCloth()){
			preview_cloth_detail_viewpager.setOffscreenPageLimit(4);
		}else{
			preview_cloth_detail_viewpager.setOffscreenPageLimit(2);
		}
		
		PreviewDetailPagerAdapter dapter = new PreviewDetailPagerAdapter(getSupportFragmentManager() , designStyle);
		preview_cloth_detail_viewpager.setAdapter(dapter);
		preview_cloth_detail_viewpager.setOnPageChangeListener(this);
	}

	@Click
	void design_title_forward_iv() {
		setCurrentDesignView(false);
	}

	@Click
	void design_title_backward_iv() {
		setCurrentDesignView(true);
	}
	
	@Click
	void design_couple_title_male_front_iv(){
		isChooseFemale = false;
		setCoupleTitleSexBtn(false);
		setCurrentDesignView(false);
	}
	
	@Click
	void design_couple_title_male_back_iv(){
		isChooseFemale = false;
		setCoupleTitleSexBtn(true);
		setCurrentDesignView(true);
	}
	
	@Click
	void design_couple_title_female_front_iv(){
		isChooseFemale = true;
		setCoupleTitleSexBtn(false);
		setCurrentDesignView(false);
	}
	
	@Click
	void design_couple_title_female_back_iv(){
		isChooseFemale = true;
		setCoupleTitleSexBtn(true);
		setCurrentDesignView(true);
	}

	
	public void setCoupleTitleSexBtn(boolean isBack){		
		if(StringUtil.isEmpty(designStyle)){
			return;
		}
	}

	/**
	 * 点击前后按钮的时候切换相应的页面
	 * 
	 * @author chengang
	 * @date 2014-10-31 下午2:12:28
	 * @param isBack
	 */
	@SuppressLint("NewApi") 
	public void setCurrentDesignView(boolean isBack) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if(isPreviewCoupleCloth()){
			if(isChooseFemale){
				if(isBack){
					preview_cloth_detail_viewpager.setCurrentItem(3);
				}else{
					preview_cloth_detail_viewpager.setCurrentItem(2);
				}
				
			}else{
				if(isBack){
					preview_cloth_detail_viewpager.setCurrentItem(1);
				}else{
					preview_cloth_detail_viewpager.setCurrentItem(0);
				}			
			}
		}else{
			if(isBack){
				preview_cloth_detail_viewpager.setCurrentItem(1);
			}else{
				preview_cloth_detail_viewpager.setCurrentItem(0);
			}
		}
		ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * 
	 * @author hch
	 * @date 2014年10月30日 上午12:37:25
	 * @param shareContent
	 *            分享文案
	 * @param shareClickUrl
	 *            分享点击跳转url
	 * @param shareImageUrl
	 *            分享图片url
	 * @param shareMessage
	 *            分享title提示信息
	 */

//	private void shareApp(String shareContent, String shareClickUrl, String shareImageUrl, String shareMessage) {
//
//		 mController.setShareContent(shareContent);
//
//		 UMQQSsoHandler qqSsoHandler = new
//		 UMQQSsoHandler(PreViewActivity.this, Constant.QQ_APPID,
//		 Constant.QQ_APPKEY);
//		 qqSsoHandler.addToSocialSDK();
//		 QQShareContent qqShareContent = new QQShareContent();
//		
//		 qqShareContent.setShareContent(shareContent);
////		
//		 qqShareContent.setTitle(shareMessage);
//		
//		 qqShareContent.setShareImage(new UMImage(PreViewActivity.this,
//		 shareImageUrl));
//		
//		 qqShareContent.setTargetUrl(shareClickUrl);
//		
//		 mController.setShareMedia(qqShareContent);
//		
//		 QZoneSsoHandler qZoneSsoHandler = new
//		 QZoneSsoHandler(PreViewActivity.this, Constant.QQ_APPID,
//		 Constant.QQ_APPKEY);
//		 qZoneSsoHandler.addToSocialSDK();
//		
//		 QZoneShareContent qZoneShareContent = new QZoneShareContent();
//		
//		 qZoneShareContent.setShareContent(shareContent);
//		
//		 qZoneShareContent.setTitle(shareMessage);
//		
//		 qZoneShareContent.setTargetUrl(shareClickUrl);
//		
//		 mController.setShareMedia(qZoneShareContent);
//
//		// ---------------------QQ------------
//
//		// ---------------------微信------------
//
//		 UMImage urlImage = new UMImage(PreViewActivity.this, shareImageUrl);
//		 // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//		
//		 // 微信图文分享必须设置一个url
//		 // 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
//		 UMWXHandler wxHandler = new UMWXHandler(PreViewActivity.this,
//		 Constant.WX_APPID, Constant.WX_APPKEY);
//		 wxHandler.addToSocialSDK();
//		 WeiXinShareContent weixinContent = new WeiXinShareContent();
//		 weixinContent.setShareContent(shareContent);
//		 weixinContent.setTitle(shareMessage);
//		 weixinContent.setTargetUrl(shareClickUrl);
//		 weixinContent.setShareImage(urlImage);
//		 mController.setShareMedia(weixinContent);
//		 // 支持微信朋友圈
//		 // 设置朋友圈分享的内容
//		 //
////		 mController.getConfig().supportWXCirclePlatform(PreViewActivity.this,
////		 WX_APPID,
//		 // messages.get("url"));
//		 UMWXHandler wxCircleHandler = new UMWXHandler(PreViewActivity.this,
//		 Constant.WX_APPID, Constant.WX_APPKEY);
//		 wxCircleHandler.setToCircle(true);
//		 wxCircleHandler.addToSocialSDK();
//		 CircleShareContent circleMedia = new CircleShareContent();
//		 circleMedia.setShareContent(shareContent);
//		 circleMedia.setTitle(shareMessage);
//		 circleMedia.setShareImage(urlImage);
//		 mController.setShareMedia(circleMedia);
//		
//		 // ---------------------微信  end------------
//		 //短信 
//		 SmsHandler smsHandler = new SmsHandler();
//		 smsHandler.addToSocialSDK();
//		 SmsShareContent smsShareContent = new SmsShareContent();
//		 smsShareContent.setShareContent(shareContent + shareClickUrl);
//		 mController.setShareMedia(smsShareContent);
//		mController.getConfig().setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.YIXIN_CIRCLE,
//				SHARE_MEDIA.RENREN);
//		mController.openShare(PreViewActivity.this, false);
//	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		isSaved = false;
		FashionDiyApplication.getApplicationInstance().clearBitmapArray();
	}
}
