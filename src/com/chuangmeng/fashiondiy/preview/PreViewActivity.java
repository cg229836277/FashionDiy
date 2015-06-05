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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.base.BaseFragmentActivity;
import com.chuangmeng.fashiondiy.base.FashionDiyApplication;
import com.chuangmeng.fashiondiy.preview.buy.ToBuyActivity_;
import com.chuangmeng.fashiondiy.preview.trywear.WaterCameraActivity_;
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
	ImageView preview_title_back_iv;

	//普通预览前
	@ViewById
	ImageView design_title_forward_iv;

	//普通预览后
	@ViewById
	ImageView design_title_backward_iv;
	
	//情侣男预览前
	@ViewById
	ImageView design_couple_title_male_front_iv;
	
	//情侣男预览后
	@ViewById
	ImageView design_couple_title_male_back_iv;
		
	//情侣女预览前
	@ViewById
	ImageView design_couple_title_female_front_iv;
	
	//情侣女预览后
	@ViewById
	ImageView design_couple_title_female_back_iv;

	@ViewById
	ImageView preview_title_share_iv;

	@ViewById
	ImageView preview_bottom_save_iv;

	@ViewById
	ImageView preview_bottom_try_iv;

	@ViewById
	ImageView preview_bottom_buy_iv;
	
//	@ViewById
//	ImageView design_title_back_iv;
	
	public final static String PREVIEW_COUPLE = "couple";
	
	private String designStyle = null;
	
	private boolean isChooseFemale = false; 
	
	@ViewById
	ViewPager preview_cloth_detail_viewpager;

	private UMSocialService mController;
	
	private PreviewPositiveFragment previewPositiveFragment;
	
	private PreviewNegativeFragment previewNegativeFragment;
	
	private PreviewFemalePositiveFragment previewFemalePositiveFragment;
	
	private PreviewFemaleNegativeFragment previewFemaleNegativeFragment;
	
	private File saveFileDir = new File(Constant.DIY_CLOTH_PICTURE_PATH);
	
	Integer[] resourceNormal = {R.drawable.design_couple_title_male_front_normal , R.drawable.design_couple_title_male_back_normal,
			R.drawable.design_couple_title_female_front_normal , R.drawable.design_couple_title_female_back_normal};
	Integer[] resourcePressed = {R.drawable.design_couple_title_male_front_pressed , R.drawable.design_couple_title_male_back_pressed,
			R.drawable.design_couple_title_female_front_pressed , R.drawable.design_couple_title_female_back_pressed};

	@AfterViews
	void initData() {
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		
		designStyle = getIntent().getStringExtra(PREVIEW_COUPLE);
		
		if(isPreviewCoupleCloth()){
			design_cloth_direction_rl.setVisibility(View.GONE);
			design_couple_cloth_direction_rl.setVisibility(View.VISIBLE);
		}else{
			design_cloth_direction_rl.setVisibility(View.VISIBLE);
			design_couple_cloth_direction_rl.setVisibility(View.GONE);
			
			LinearLayout.LayoutParams paras = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			paras.width = screenMetric.widthPixels / 2;
			paras.height = 50 * (int)screenMetric.density;
			paras.topMargin = 8 * (int)screenMetric.density;
			paras.bottomMargin = 8 * (int)screenMetric.density;
			paras.gravity = Gravity.CENTER;
			design_cloth_direction_rl.setLayoutParams(paras);
		}

		initFragmentAdapter();
		// adjustViewForScreen();
		// 初始化两个控件的状态
		exchangeChooseState(false);

		setPreviewBitmap();				
	}

	@Click
	void preview_title_share_iv() {
		//shareApp("test", "test", "test", "test");
		String shareImageUrl = "http://d.hiphotos.baidu.com/image/pic/item/b3fb43166d224f4ae33532120bf790529822d107.jpg";
		shareApp(Constant.shareContent, Constant.shareClickUrl, shareImageUrl, Constant.shareTitle);
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
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
				progressDialog.show();
			}
			
			@Override
			protected Void doInBackground(Void... params) {
							
				if(!saveFileDir.exists()){
					saveFileDir.mkdirs();
				}
				
				if(isPreviewCoupleCloth()){
					saveCoupleCloth();
				}
				
				for(int i = 0 ; i < 2 ; i++){
					String fileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".png";
					try{
						File diyClothImage = new File(saveFileDir + "/" + fileName);
						if(diyClothImage.exists()){
							diyClothImage.delete();
						}
						FileOutputStream out = new FileOutputStream(diyClothImage);
						if(i == 0){
							Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getPositiveViewBitmap();
							if(currentBitmap != null){
								currentBitmap.compress(CompressFormat.PNG, 100, out);
							}
						}else{
							Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getNegativeViewBitmap();
							if(currentBitmap != null){
								currentBitmap.compress(CompressFormat.PNG, 100, out);
							}
						}	
						out.flush();
						out.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				progressDialog.dismiss();
				
				toastDialog.show("保存成功！");
			}
		}.execute();
	}
	
	/**
	 * 保存设计的情侣的衣服
	 * 
	 * @author Administrator
	 * @date 2014-12-22 上午11:26:26
	 */
	public void saveCoupleCloth(){
		for(int i = 0 ; i < 2 ; i++){
			String fileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".png";
			try{
				File diyClothImage = new File(saveFileDir + "/" + fileName);
				if(diyClothImage.exists()){
					diyClothImage.delete();
				}
				FileOutputStream out = new FileOutputStream(diyClothImage);
				if(i == 0){
					Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getFemalePositiveBitmap();
					if(currentBitmap != null){
						currentBitmap.compress(CompressFormat.PNG, 100, out);
					}
				}else{
					Bitmap currentBitmap = FashionDiyApplication.getApplicationInstance().getFemaleNegativeBitmap();
					if(currentBitmap != null){
						currentBitmap.compress(CompressFormat.PNG, 100, out);
					}
				}	
				out.flush();
				out.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 试穿按钮操作
	 * 
	 * @author hch
	 * @date 2014年11月1日 下午1:37:39
	 */
	@Click
	void preview_bottom_try_iv() {
		ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
		bitmapArray.add(FashionDiyApplication.getApplicationInstance().getPositiveViewBitmap());
		bitmapArray.add(FashionDiyApplication.getApplicationInstance().getNegativeViewBitmap());
		if(isPreviewCoupleCloth()){
			bitmapArray.add(FashionDiyApplication.getApplicationInstance().getFemalePositiveBitmap());
			bitmapArray.add(FashionDiyApplication.getApplicationInstance().getFemaleNegativeBitmap());
		}
		FashionDiyApplication.getApplicationInstance().setBitmaps(bitmapArray);						
		startActivity(new Intent(PreViewActivity.this, WaterCameraActivity_.class));
	}

	/**
	 * 购买按钮操作
	 * 
	 * @author hch
	 * @date 2014年11月1日 下午1:37:20
	 */
	@Click
	void preview_bottom_buy_iv() {
		Intent intent = new Intent();
		intent.putExtra("isLovers", isPreviewCoupleCloth());
		intent.setClass(PreViewActivity.this,ToBuyActivity_.class);
		startActivity(intent);
	}
	
	@Click
	public void preview_title_back_iv(){
		backImageClicked();
	}

	public void initFragmentAdapter() {
		preview_cloth_detail_viewpager.setOffscreenPageLimit(4);
		preview_cloth_detail_viewpager.setAdapter(new PreviewDetailPagerAdapter(getSupportFragmentManager() , designStyle));
		preview_cloth_detail_viewpager.setOnPageChangeListener(this);
	}
	
	/**
	 * 处理情侣页面title按钮的状态
	 * 
	 * @author Administrator
	 * @date 2014-12-31 上午11:41:26
	 * @param position
	 */
	public void dealPageScrolled(int position){		
		ImageView[] coupleImageView = {design_couple_title_male_front_iv , design_couple_title_male_back_iv ,
				design_couple_title_female_front_iv , design_couple_title_female_back_iv};
		
		for(int i = 0 ; i < coupleImageView.length ; i++){
			if(position == i){
				coupleImageView[i].setImageResource(resourcePressed[i]);
			}else{
				coupleImageView[i].setImageResource(resourceNormal[i]);
			}
		}
	}
	
	/**
	 * 设置预览的view
	 * 
	 * @author chengang
	 * @date 2014-11-13 上午9:06:10
	 */
	public void setPreviewBitmap(){
		
	}
	
//	@Click
//	public void design_title_back_iv(){
//		backImageClicked();
//	}

	@Click
	void design_title_forward_iv() {
		exchangeChooseState(false);
		setCurrentDesignView(false);
	}

	@Click
	void design_title_backward_iv() {
		exchangeChooseState(true);
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

	/**
	 * 对应变换前后按钮的状态
	 * 
	 * @author chengang
	 * @date 2014-10-31 下午2:04:09
	 */
	public void exchangeChooseState(boolean isBack) {
		if(isPreviewCoupleCloth()){
			design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);		
			design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_pressed);
			design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			return;
		}
		
		if (isBack) {
			design_title_forward_iv.setImageResource(R.drawable.design_choose_forward_normal);
			design_title_backward_iv.setImageResource(R.drawable.design_choose_back_pressed);
		} else {
			design_title_forward_iv.setImageResource(R.drawable.design_choose_forward_pressed);
			design_title_backward_iv.setImageResource(R.drawable.design_choose_back_normal);
		}
	}
	
	public void setCoupleTitleSexBtn(boolean isBack){		
		if(StringUtil.isEmpty(designStyle)){
			return;
		}
		
		if(isChooseFemale){
			if(isBack){
				design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_pressed);
				design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			}else{
				design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_pressed);
				design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);
			}			
			design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_normal);
			design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			isChooseFemale = true;
		}else{
			if(isBack){
				design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_pressed);
				design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_normal);
			}else{
				design_couple_title_male_front_iv.setImageResource(R.drawable.design_couple_title_male_front_pressed);
				design_couple_title_male_back_iv.setImageResource(R.drawable.design_couple_title_male_back_normal);
			}			
			design_couple_title_female_front_iv.setImageResource(R.drawable.design_couple_title_female_front_normal);
			design_couple_title_female_back_iv.setImageResource(R.drawable.design_couple_title_female_back_normal);
			isChooseFemale = false;
		}
	}

	/**
	 * 点击前后按钮的时候切换相应的页面
	 * 
	 * @author chengang
	 * @date 2014-10-31 下午2:12:28
	 * @param isBack
	 */
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

	private void shareApp(String shareContent, String shareClickUrl, String shareImageUrl, String shareMessage) {

		 mController.setShareContent(shareContent);

		 UMQQSsoHandler qqSsoHandler = new
		 UMQQSsoHandler(PreViewActivity.this, Constant.QQ_APPID,
		 Constant.QQ_APPKEY);
		 qqSsoHandler.addToSocialSDK();
		 QQShareContent qqShareContent = new QQShareContent();
		
		 qqShareContent.setShareContent(shareContent);
//		
		 qqShareContent.setTitle(shareMessage);
		
		 qqShareContent.setShareImage(new UMImage(PreViewActivity.this,
		 shareImageUrl));
		
		 qqShareContent.setTargetUrl(shareClickUrl);
		
		 mController.setShareMedia(qqShareContent);
		
		 QZoneSsoHandler qZoneSsoHandler = new
		 QZoneSsoHandler(PreViewActivity.this, Constant.QQ_APPID,
		 Constant.QQ_APPKEY);
		 qZoneSsoHandler.addToSocialSDK();
		
		 QZoneShareContent qZoneShareContent = new QZoneShareContent();
		
		 qZoneShareContent.setShareContent(shareContent);
		
		 qZoneShareContent.setTitle(shareMessage);
		
		 qZoneShareContent.setTargetUrl(shareClickUrl);
		
		 mController.setShareMedia(qZoneShareContent);

		// ---------------------QQ------------

		// ---------------------微信------------

		 UMImage urlImage = new UMImage(PreViewActivity.this, shareImageUrl);
		 // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		
		 // 微信图文分享必须设置一个url
		 // 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		 UMWXHandler wxHandler = new UMWXHandler(PreViewActivity.this,
		 Constant.WX_APPID, Constant.WX_APPKEY);
		 wxHandler.addToSocialSDK();
		 WeiXinShareContent weixinContent = new WeiXinShareContent();
		 weixinContent.setShareContent(shareContent);
		 weixinContent.setTitle(shareMessage);
		 weixinContent.setTargetUrl(shareClickUrl);
		 weixinContent.setShareImage(urlImage);
		 mController.setShareMedia(weixinContent);
		 // 支持微信朋友圈
		 // 设置朋友圈分享的内容
		 //
//		 mController.getConfig().supportWXCirclePlatform(PreViewActivity.this,
//		 WX_APPID,
		 // messages.get("url"));
		 UMWXHandler wxCircleHandler = new UMWXHandler(PreViewActivity.this,
		 Constant.WX_APPID, Constant.WX_APPKEY);
		 wxCircleHandler.setToCircle(true);
		 wxCircleHandler.addToSocialSDK();
		 CircleShareContent circleMedia = new CircleShareContent();
		 circleMedia.setShareContent(shareContent);
		 circleMedia.setTitle(shareMessage);
		 circleMedia.setShareImage(urlImage);
		 mController.setShareMedia(circleMedia);
		
		 // ---------------------微信  end------------
		 //短信
		 SmsHandler smsHandler = new SmsHandler();
		 smsHandler.addToSocialSDK();
		 SmsShareContent smsShareContent = new SmsShareContent();
		 smsShareContent.setShareContent(shareContent + shareClickUrl);
		 mController.setShareMedia(smsShareContent);
		mController.getConfig().setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.YIXIN_CIRCLE,
				SHARE_MEDIA.RENREN);
		mController.openShare(PreViewActivity.this, false);
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		
		if(isPreviewCoupleCloth()){
			dealPageScrolled(arg0);
			return;
		}
		
		if (arg0 == 0) {
			exchangeChooseState(false);
		} else {
			exchangeChooseState(true);
		}
	}
	
	public void getFragmentObject(){
		if(isPreviewCoupleCloth()){
			previewFemalePositiveFragment = (PreviewFemalePositiveFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.preview_cloth_detail_viewpager + ":2");
			previewFemaleNegativeFragment = (PreviewFemaleNegativeFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.preview_cloth_detail_viewpager + ":3");
		}
		previewPositiveFragment = (PreviewPositiveFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.preview_cloth_detail_viewpager + ":0");
		previewNegativeFragment = (PreviewNegativeFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.preview_cloth_detail_viewpager + ":1");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(resourcePressed != null){
			resourcePressed = null;
		}
		
		if(resourceNormal != null){
			resourceNormal = null;
		}		
	}
}
