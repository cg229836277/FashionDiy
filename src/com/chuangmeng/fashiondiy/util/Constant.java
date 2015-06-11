package com.chuangmeng.fashiondiy.util;

import java.io.File;

import com.chuangmeng.fashiondiy.R;

import android.os.Environment;

/**
 * 系统常量类
 * 
 * @author admini
 * 
 */
public class Constant {
	
	public static final String STYLE = "style";
	public final static String MALE_STYLE = "male"; // 首页选择男装
	public final static String FEMALE_STYLE = "female";// 首页选择女装
	public final static String COUPLE_STYLE = "couple";// 首页选择情侣装

	public static String imei = "";
	
	/**
	* @Fields debug : log开关
	*/ 
	public static boolean debug = true;

	/**
	 * QQ平台appid
	 */
	public static String QQ_APPID = "1103601454";

	/**
	 * QQ平台appkey
	 */
	public static String QQ_APPKEY = "dkZChaFpGMt9X7PD";

	/**
	 * 微信平台appid
	 */
	public static String WX_APPID = "wx4a3a2cef31eb0cae";

	/**
	 * 微信平台appkey
	 */
	public static String WX_APPKEY = "45686f7eb49446dd777a74bfc70d9e8b";
	
	/**
	* 分享内容文案
	*/ 
	public static String shareContent = "快看，这是我在全民diy上设计的衣服，好看吧！^_^ !! 你也来设计一下你梦想中的T恤吧！";
	
	/**
	* 点击分享跳转网址
	*/ 
	public static String shareClickUrl = "http://www.baidu.com/";
	
	/**
	* 分享内容title
	*/ 
	public static String shareTitle = "全民diy";

	/**
	 * 相机获取照片
	 */
	public static final int GET_PHOTO_BY_CAMERA = 1;

	/**
	 * 本地选取照片
	 */
	public static final int GET_PHOTO_BY_LOCAL = 2;

	/**
	 * 照片存放路径
	 */
	public static String TAKE_PICTURE_PATH = Environment.getExternalStorageDirectory() + "/fashion/picture/";
	
	public static String APP_CRASH_LOG_PATH = Environment.getExternalStorageDirectory() + "/fashion/crash_log/";

	/**
	 * 完成diy之后作品存放路径
	 */
	public static String DIY_CLOTH_PICTURE_PATH = Environment.getExternalStorageDirectory() + File.separator + "fashion/cloth/";

	public final static String ADD_PICTURE_ON_CLOTH = "1"; // 在衣服上添加图片(拍照和本地获取照片)
	public final static String ADD_ICON_ON_CLOTH = "2"; // 在衣服上添加图案
	public final static String CHANGE_CLOTH_STYLE = "3"; // 改变衣服的款式
	public final static String ADD_MODEL_ON_CLOTH = "4"; // 改变衣服的款式
	public final static String ADD_TEXT_ON_CLOTH = "5"; // 改变衣服的款式

	/**
	 * 模板的背景展示图片
	 */
	public static Integer[] designModelDrawable = { R.drawable.design_model_wifi, R.drawable.design_model_butterfly, R.drawable.design_model_circle, R.drawable.design_model_circle_water,
			R.drawable.design_model_five_triangle, R.drawable.design_model_flag, R.drawable.design_model_flower_1, R.drawable.design_model_flower_2, R.drawable.design_model_flower_3,
			R.drawable.design_model_heart_ballon, R.drawable.design_model_jigsaw, R.drawable.design_model_leaves_1, R.drawable.design_model_love, R.drawable.design_model_maple_leaf,
			R.drawable.design_model_mickey, R.drawable.design_model_mirror, R.drawable.design_model_paint, R.drawable.design_model_shape_1, R.drawable.design_model_shape_2,
			R.drawable.design_model_shape_3, R.drawable.design_model_sign, R.drawable.design_model_six_triangle, R.drawable.design_model_snail, R.drawable.design_model_snow_flower,
			R.drawable.design_model_spider, R.drawable.design_model_spill_ink, R.drawable.design_model_swing, R.drawable.design_model_text_1, R.drawable.design_model_text_2,
			R.drawable.design_model_text_3, R.drawable.design_model_tie, R.drawable.design_model_tree, R.drawable.design_model_urban_shadow_1, R.drawable.design_model_urban_shadow_2 };
	/**
	 * 模板的背景剪裁图片
	 */
	public static Integer[] designModelRaw = { R.raw.design_model_wifi, R.raw.design_model_butterfly, R.raw.design_model_circle, R.raw.design_model_circle_water, R.raw.design_model_five_triangle,
			R.raw.design_model_flag, R.raw.design_model_flower_1, R.raw.design_model_flower_2, R.raw.design_model_flower_3, R.raw.design_model_heart_ballon, R.raw.design_model_jigsaw,
			R.raw.design_model_leaves_1, R.raw.design_model_love, R.raw.design_model_maple_leaf, R.raw.design_model_mickey, R.raw.design_model_mirror, R.raw.design_model_paint,
			R.raw.design_model_shape_1, R.raw.design_model_shape_2, R.raw.design_model_shape_3, R.raw.design_model_sign, R.raw.design_model_six_triangle, R.raw.design_model_snail,
			R.raw.design_model_snow_flower, R.raw.design_model_spider, R.raw.design_model_spill_ink, R.raw.design_model_swing, R.raw.design_model_text_1, R.raw.design_model_text_2,
			R.raw.design_model_text_3, R.raw.design_model_tie, R.raw.design_model_tree, R.raw.design_model_urban_shadow_1, R.raw.design_model_urban_shadow_2 };

	/**
	 * 衣服上的小图标
	 */
	public static Integer[] designPatternDrawable = { R.drawable.design_cloth_icon_0, R.drawable.design_cloth_icon_1, R.drawable.design_cloth_icon_2, R.drawable.design_cloth_icon_3,
			R.drawable.design_cloth_icon_4, R.drawable.design_cloth_icon_5, R.drawable.design_cloth_icon_6, R.drawable.design_cloth_icon_7, R.drawable.design_cloth_icon_8,
			R.drawable.design_cloth_icon_9, R.drawable.design_cloth_icon_10, R.drawable.design_cloth_icon_11, R.drawable.design_cloth_icon_12, R.drawable.design_cloth_icon_13,
			R.drawable.design_cloth_icon_14, R.drawable.design_cloth_icon_15, R.drawable.design_cloth_icon_16, R.drawable.design_cloth_icon_17, R.drawable.design_cloth_icon_18,
			R.drawable.design_cloth_icon_19, R.drawable.design_cloth_icon_20, R.drawable.design_cloth_icon_21, R.drawable.design_cloth_icon_22, R.drawable.design_cloth_icon_23,
			R.drawable.design_cloth_icon_24, R.drawable.design_cloth_icon_25, R.drawable.design_cloth_icon_26, R.drawable.design_cloth_icon_27, R.drawable.design_cloth_icon_28,
			R.drawable.design_cloth_icon_29, R.drawable.design_cloth_icon_30, R.drawable.design_cloth_icon_31, R.drawable.design_cloth_icon_32, R.drawable.design_cloth_icon_33,
			R.drawable.design_cloth_icon_34, R.drawable.design_cloth_icon_35, R.drawable.design_cloth_icon_36, R.drawable.design_cloth_icon_37, R.drawable.design_cloth_icon_38,
			R.drawable.design_cloth_icon_39, R.drawable.design_cloth_icon_40, R.drawable.design_cloth_icon_41, R.drawable.design_cloth_icon_42, R.drawable.design_cloth_icon_43,
			R.drawable.design_cloth_icon_44, R.drawable.design_cloth_icon_45, R.drawable.design_cloth_icon_46, R.drawable.design_cloth_icon_47, R.drawable.design_cloth_icon_48,
			R.drawable.design_cloth_icon_49, R.drawable.design_cloth_icon_50, R.drawable.design_cloth_icon_51, R.drawable.design_cloth_icon_52, R.drawable.design_cloth_icon_53,
			R.drawable.design_cloth_icon_54, R.drawable.design_cloth_icon_55, R.drawable.design_cloth_icon_56, R.drawable.design_cloth_icon_57, R.drawable.design_cloth_icon_58,
			R.drawable.design_cloth_icon_59, R.drawable.design_cloth_icon_60, R.drawable.design_cloth_icon_61, R.drawable.design_cloth_icon_62, R.drawable.design_cloth_icon_63,
			R.drawable.design_cloth_icon_64, R.drawable.design_cloth_icon_65, R.drawable.design_cloth_icon_66, R.drawable.design_cloth_icon_67, R.drawable.design_cloth_icon_68,
			R.drawable.design_cloth_icon_69, R.drawable.design_cloth_icon_70, R.drawable.design_cloth_icon_71, R.drawable.design_cloth_icon_72, R.drawable.design_cloth_icon_73,
			R.drawable.design_cloth_icon_74, R.drawable.design_cloth_icon_75, R.drawable.design_cloth_icon_76, R.drawable.design_cloth_icon_77, R.drawable.design_cloth_icon_78 };

	/**
	 * 男士所有衣服的正面
	 */
	public static Integer[] maleAllClothPositive = {
			// 短袖正面
			R.drawable.design_source_cloth_white_positive, R.drawable.design_source_cloth_black_positive, R.drawable.design_source_cloth_red_positive, 
			R.drawable.design_source_cloth_green_positive,R.drawable.design_source_cloth_blue_positive,R.drawable.design_source_cloth_yellow_positive,
			// 长袖的正面
			R.drawable.design_source_ls_cloth_white_positive, R.drawable.design_source_ls_cloth_black_positive, R.drawable.design_source_ls_cloth_red_positive,
			R.drawable.design_source_ls_cloth_green_positive, R.drawable.design_source_ls_cloth_blue_positive, R.drawable.design_source_ls_cloth_yellow_positive,
			// 卫衣的正面
			R.drawable.design_source_ss_cloth_white_positive, R.drawable.design_source_ss_cloth_black_positive, R.drawable.design_source_ss_cloth_red_positive,
			R.drawable.design_source_ss_cloth_green_positive, R.drawable.design_source_ss_cloth_blue_positive, R.drawable.design_source_ss_cloth_yellow_positive };

	/**
	 * 男士所有衣服的背面
	 */
	public static Integer[] maleAllClothNegative = {
			// 短袖背面
			R.drawable.design_source_cloth_white_negative, R.drawable.design_source_cloth_black_negative, R.drawable.design_source_cloth_red_negative, 
			R.drawable.design_source_cloth_green_negative,R.drawable.design_source_cloth_blue_negative,R.drawable.design_source_cloth_yellow_negative,
			// 长袖的背面
			R.drawable.design_source_ls_cloth_white_negative, R.drawable.design_source_ls_cloth_black_negative, R.drawable.design_source_ls_cloth_red_negative,
			R.drawable.design_source_ls_cloth_green_negative, R.drawable.design_source_ls_cloth_blue_negative, R.drawable.design_source_ls_cloth_yellow_negative,
			// 卫衣的背面
			R.drawable.design_source_ss_cloth_white_negative, R.drawable.design_source_ss_cloth_black_negative, R.drawable.design_source_ss_cloth_red_negative,
			R.drawable.design_source_ss_cloth_green_negative, R.drawable.design_source_ss_cloth_blue_negative, R.drawable.design_source_ss_cloth_yellow_negative };

	/**
	 * 女士所有衣服的正面
	 */
	public static Integer[] femaleAllClothPositive = {
			// 短袖正面
			R.drawable.design_source_female_ts_cloth_white_positive, R.drawable.design_source_female_ts_cloth_black_positive, R.drawable.design_source_female_ts_cloth_red_positive,
			R.drawable.design_source_female_ts_cloth_green_positive, R.drawable.design_source_female_ts_cloth_blue_positive, R.drawable.design_source_female_ts_cloth_yellow_positive,
			// 长袖的正面
			R.drawable.design_source_female_ls_cloth_white_positive, R.drawable.design_source_female_ls_cloth_black_positive, R.drawable.design_source_female_ls_cloth_red_positive,
			R.drawable.design_source_female_ls_cloth_green_positive, R.drawable.design_source_female_ls_cloth_blue_positive, R.drawable.design_source_female_ls_cloth_yellow_positive };

	/**
	 * 女士所有衣服的背面
	 */
	public static Integer[] femaleAllClothNegative = {
			// 短袖背面
			R.drawable.design_source_female_ts_cloth_white_negative, R.drawable.design_source_female_ts_cloth_black_negative, R.drawable.design_source_female_ts_cloth_red_negative,
			R.drawable.design_source_female_ts_cloth_green_negative, R.drawable.design_source_female_ts_cloth_blue_negative, R.drawable.design_source_female_ts_cloth_yellow_negative,
			// 长袖的背面
			R.drawable.design_source_female_ls_cloth_white_negative, R.drawable.design_source_female_ls_cloth_black_negative, R.drawable.design_source_female_ls_cloth_red_negative,
			R.drawable.design_source_female_ls_cloth_green_negative, R.drawable.design_source_female_ls_cloth_blue_negative, R.drawable.design_source_female_ls_cloth_yellow_negative };
	
	/**
	 * 所有衣服的正面
	 * 
	 * @author Administrator
	 * @date 2014-12-18 下午4:30:52
	 * @return
	 */
	public static Integer[] allClothPositive = {
		// 短袖正面
		R.drawable.design_source_cloth_white_positive, R.drawable.design_source_cloth_black_positive, R.drawable.design_source_cloth_red_positive, 
		R.drawable.design_source_cloth_green_positive,R.drawable.design_source_cloth_blue_positive,R.drawable.design_source_cloth_yellow_positive,
		// 长袖的正面
		R.drawable.design_source_ls_cloth_white_positive, R.drawable.design_source_ls_cloth_black_positive, R.drawable.design_source_ls_cloth_red_positive,
		R.drawable.design_source_ls_cloth_green_positive, R.drawable.design_source_ls_cloth_blue_positive, R.drawable.design_source_ls_cloth_yellow_positive,
		// 卫衣的正面
		R.drawable.design_source_ss_cloth_white_positive, R.drawable.design_source_ss_cloth_black_positive, R.drawable.design_source_ss_cloth_red_positive,
		R.drawable.design_source_ss_cloth_green_positive, R.drawable.design_source_ss_cloth_blue_positive, R.drawable.design_source_ss_cloth_yellow_positive,
		// 短袖正面
		R.drawable.design_source_female_ts_cloth_white_positive, R.drawable.design_source_female_ts_cloth_black_positive, R.drawable.design_source_female_ts_cloth_red_positive,
		R.drawable.design_source_female_ts_cloth_green_positive, R.drawable.design_source_female_ts_cloth_blue_positive, R.drawable.design_source_female_ts_cloth_yellow_positive,
		// 长袖的正面
		R.drawable.design_source_female_ls_cloth_white_positive, R.drawable.design_source_female_ls_cloth_black_positive, R.drawable.design_source_female_ls_cloth_red_positive,
		R.drawable.design_source_female_ls_cloth_green_positive, R.drawable.design_source_female_ls_cloth_blue_positive, R.drawable.design_source_female_ls_cloth_yellow_positive					
	};
	
	/**
	 * 所有衣服的反面
	 */
	public static Integer[] allClothNegative = {
		// 短袖背面
		R.drawable.design_source_cloth_white_negative, R.drawable.design_source_cloth_black_negative, R.drawable.design_source_cloth_red_negative, 
		R.drawable.design_source_cloth_green_negative,R.drawable.design_source_cloth_blue_negative,R.drawable.design_source_cloth_yellow_negative,
		// 长袖的背面
		R.drawable.design_source_ls_cloth_white_negative, R.drawable.design_source_ls_cloth_black_negative, R.drawable.design_source_ls_cloth_red_negative,
		R.drawable.design_source_ls_cloth_green_negative, R.drawable.design_source_ls_cloth_blue_negative, R.drawable.design_source_ls_cloth_yellow_negative,
		// 卫衣的背面
		R.drawable.design_source_ss_cloth_white_negative, R.drawable.design_source_ss_cloth_black_negative, R.drawable.design_source_ss_cloth_red_negative,
		R.drawable.design_source_ss_cloth_green_negative, R.drawable.design_source_ss_cloth_blue_negative, R.drawable.design_source_ss_cloth_yellow_negative,
		// 短袖背面
		R.drawable.design_source_female_ts_cloth_white_negative, R.drawable.design_source_female_ts_cloth_black_negative, R.drawable.design_source_female_ts_cloth_red_negative,
		R.drawable.design_source_female_ts_cloth_green_negative, R.drawable.design_source_female_ts_cloth_blue_negative, R.drawable.design_source_female_ts_cloth_yellow_negative,
		// 长袖的背面
		R.drawable.design_source_female_ls_cloth_white_negative, R.drawable.design_source_female_ls_cloth_black_negative, R.drawable.design_source_female_ls_cloth_red_negative,
		R.drawable.design_source_female_ls_cloth_green_negative, R.drawable.design_source_female_ls_cloth_blue_negative, R.drawable.design_source_female_ls_cloth_yellow_negative
	};

	/**
	 * 设计文字字体说明
	 */
	public static String[] designTextStyle = { "design_text_style1.TTF", "design_text_style2.TTF", "design_text_style3.ttf", "design_text_style4.ttf", "design_text_style5.TTF",
			"design_text_style6.ttf", "design_text_style7.ttf", "design_text_style8.ttf", "design_text_style9.ttf", "design_text_style10.ttf", "design_text_style11.TTF", "design_text_style12.TTF",
			"design_text_style13.TTF", "design_text_style14.TTF", "design_text_style15.ttf", "design_text_style16.TTF", "design_text_style17.ttf", "design_text_style18.TTF",
			"design_text_style19.TTF", "design_text_style20.TTF", "design_text_style21.ttf", "design_text_style22.ttf", "design_text_style23.ttf", "design_text_style24.ttf",
			"design_text_style25.TTF", "design_text_style26.TTF" };
	/**
	 * 设计文字字体说明
	 */
	public static Integer[] designTextStyleExplain = { R.drawable.design_text_style1, R.drawable.design_text_style2, R.drawable.design_text_style3, R.drawable.design_text_style4,
			R.drawable.design_text_style5, R.drawable.design_text_style6, R.drawable.design_text_style7, R.drawable.design_text_style8, R.drawable.design_text_style9, R.drawable.design_text_style10,
			R.drawable.design_text_style11, R.drawable.design_text_style12, R.drawable.design_text_style13, R.drawable.design_text_style14, R.drawable.design_text_style15,
			R.drawable.design_text_style16, R.drawable.design_text_style17, R.drawable.design_text_style18, R.drawable.design_text_style19, R.drawable.design_text_style20,
			R.drawable.design_text_style21, R.drawable.design_text_style22, R.drawable.design_text_style23, R.drawable.design_text_style24, R.drawable.design_text_style25,
			R.drawable.design_text_style26 };
	// /**
	// * 设计文字颜色展示
	// */
	// public static Integer[] designTextColorExplain = {
	// R.drawable.design_model_operate_textcolor1,
	// R.drawable.design_model_operate_textcolor2,
	// R.drawable.design_model_operate_textcolor3,
	// R.drawable.design_model_operate_textcolor4,
	// R.drawable.design_model_operate_textcolor5,
	// R.drawable.design_model_operate_textcolor6,
	// R.drawable.design_model_operate_textcolor7,
	// R.drawable.design_model_operate_textcolor8,
	// R.drawable.design_model_operate_textcolor9,
	// R.drawable.design_model_operate_textcolor10 };
	// public static String[] designTextColor = { "#FFFFFF", "#000000",
	// "#696E71", "#FDF577", "#FEFCBC", "#D63238", "#91DAED", "#AAD23C",
	// "#AAD23C", "#AAD23C" };
	// }

	/**
	 * 设计文字颜色展示
	 */
	public static Integer[] designTextColorExplain = { R.drawable.design_text_color_ffffff, R.drawable.design_text_color_fffbb3, R.drawable.design_text_color_fff56f,
			R.drawable.design_text_color_ffdb00, R.drawable.design_text_color_ffd656, R.drawable.design_text_color_ffb72d, R.drawable.design_text_color_f6821f, R.drawable.design_text_color_f15a21,
			R.drawable.design_text_color_ff6541, R.drawable.design_text_color_a55c67, R.drawable.design_text_color_c1877b, R.drawable.design_text_color_d19698, R.drawable.design_text_color_ffb3b3,
			R.drawable.design_text_color_d36b5e, R.drawable.design_text_color_ff7e7e, R.drawable.design_text_color_e14f80, R.drawable.design_text_color_d43377, R.drawable.design_text_color_ca3069,
			R.drawable.design_text_color_ba3933, R.drawable.design_text_color_cd1c39, R.drawable.design_text_color_da333a, R.drawable.design_text_color_ee5b6d, R.drawable.design_text_color_c1d7ee,
			R.drawable.design_text_color_afb4da, R.drawable.design_text_color_9b99cb, R.drawable.design_text_color_817da9, R.drawable.design_text_color_7c5886, R.drawable.design_text_color_764c96,
			R.drawable.design_text_color_af71b0, R.drawable.design_text_color_da54de, R.drawable.design_text_color_ed83e8, R.drawable.design_text_color_f7a3f0, R.drawable.design_text_color_c3ff9e,
			R.drawable.design_text_color_b3ffbc, R.drawable.design_text_color_a4ec08, R.drawable.design_text_color_b2d135, R.drawable.design_text_color_00ffc1, R.drawable.design_text_color_4efe7e,
			R.drawable.design_text_color_02b340, R.drawable.design_text_color_00a563, R.drawable.design_text_color_77914a, R.drawable.design_text_color_a8d1b5, R.drawable.design_text_color_90d7ed,
			R.drawable.design_text_color_84c2d7, R.drawable.design_text_color_56a9d3, R.drawable.design_text_color_00b9ce, R.drawable.design_text_color_01afd2, R.drawable.design_text_color_027298,
			R.drawable.design_text_color_0062b0, R.drawable.design_text_color_2368a3, R.drawable.design_text_color_433894, R.drawable.design_text_color_d4bc8c, R.drawable.design_text_color_be972e,
			R.drawable.design_text_color_bc9316, R.drawable.design_text_color_a56000, R.drawable.design_text_color_7d141b, R.drawable.design_text_color_7c2339, R.drawable.design_text_color_54211e,
			R.drawable.design_text_color_5b2935, R.drawable.design_text_color_45224a, R.drawable.design_text_color_72777a, R.drawable.design_text_color_1a315d, R.drawable.design_text_color_000000 };
	/**
	 * 设计文字对应颜色
	 */
	public static String[] designTextColor = { "#ffffff", "#fffbb3", "#fff56f", "#ffdb00", "#ffd656", "#ffb72d", "#f6821f", "#f15a21", "#ff6541", "#a55c67", "#c1877b", "#d19698", "#ffb3b3",
			"#d36b5e", "#ff7e7e", "#e14f80", "#d43377", "#ca3069", "#ba3933", "#cd1c39", "#da333a", "#ee5b6d", "#c1d7ee", "#afb4da", "#9b99cb", "#817da9", "#7c5886", "#764c96", "#af71b0", "#da54de",
			"#ed83e8", "#f7a3f0", "#c3ff9e", "#b3ffbc", "#a4ec08", "#b2d135", "#00ffc1", "#4efe7e", "#02b340", "#00a563", "#77914a", "#a8d1b5", "#90d7ed", "#84c2d7", "#56a9d3", "#00b9ce", "#01afd2",
			"#027298", "#0062b0", "#2368a3", "#433894", "#d4bc8c", "#be972e", "#bc9316", "#a56000", "#7d141b", "#7c2339", "#54211e", "#5b2935", "#45224a", "#72777a", "#1a315d", "#000000" };
}
