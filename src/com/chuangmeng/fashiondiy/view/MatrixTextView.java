package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.R;
import com.chuangmeng.fashiondiy.util.StringUtil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义TextView,用于在衣服上面放置文字
 * 
 * @Title：FashionDIY
 * @Description：
 * @date 2014-11-24 下午4:37:27
 * @author chengang
 * @version 1.0
 */
public class MatrixTextView extends FrameLayout {
	private FrameLayout design_matrix_text_layout_fl;
	private AutoResizeTextView mView;
	private ImageView mPushView;
	private ImageView mDeleteView;
	private float _1dp;
	private boolean mCenterInParent = true;
	private float mTextViewHeight, mTextViewWidth, mPushImageHeight, mPushImageWidth, mDeleteImageHeight, mDeleteImageWidth;
	private int mLeft = 0, mTop = 0;
	private int count = 0;
	private long firClick ,secClick;
	
	PushBtnTouchListener pushListener = null;

	public final static int TEXT_ALIGN_LEFT = Gravity.LEFT;
	public final static int TEXT_ALIGN_RIGHT = Gravity.RIGHT;
	public final static int TEXT_ALIGN_CENTER = Gravity.CENTER;
	private DesignDetailTextTouchInterface _designDetailTextTouchInterface;

	public MatrixTextView(Context context, DesignDetailTextTouchInterface designDetailTextTouchInterface) {
		this(context, null, 0);
		this._designDetailTextTouchInterface = designDetailTextTouchInterface;
	}

	public MatrixTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MatrixTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this._1dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
		View mRoot = View.inflate(context, R.layout.design_self_define_textview, null);
		addView(mRoot);
		design_matrix_text_layout_fl = (FrameLayout)mRoot.findViewById(R.id.design_matrix_text_layout_fl);
		mPushView = (ImageView) mRoot.findViewById(R.id.push_view);
		mView = (AutoResizeTextView) mRoot.findViewById(R.id.view);
		mView.setTag(false);
		mDeleteView = (ImageView) mRoot.findViewById(R.id.delete_view);
		pushListener = new PushBtnTouchListener(mView, mDeleteView);
		mPushView.setOnTouchListener(pushListener);
		mView.setOnTouchListener(new ViewOnTouchListener(mPushView, mDeleteView, mView, new DesignDetailTextTouchInterface() {

			@Override
			public void touchListener(AutoResizeTextView touchView) {
				_designDetailTextTouchInterface.touchListener(touchView);
			}

			@Override
			public void twoTouch() {
				
				textDesignLayoutDialog(mView.getText().toString()).show();
			}
		}));
//		mView.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View arg0) {
//				textDesignLayoutDialog(mView.getText().toString()).show();
//				return false;
//
//			}
//		});
//		mView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				textDesignLayoutDialog(mView.getText().toString()).show();
//			}
//		});
//		mView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				if(MotionEvent.ACTION_DOWN == event.getAction()){  
//		            count++;  
//		            if(count == 1){  
//		                firClick = System.currentTimeMillis();  
//		                  
//		            } else if (count == 2){  
//		                secClick = System.currentTimeMillis();  
//		                if(secClick - firClick < 1000){  
//		                    //双击事件  
//		                	
//		                }  
//		                count = 0;  
//		                firClick = 0;  
//		                secClick = 0;  
//		                  
//		            }  
//		        }  
//		        return true; 
//				
//			}
//		});
		parseAttr(context, attrs);

		mDeleteView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MatrixTextView.this.setVisibility(View.GONE);
			}
		});
	}

	private Dialog textDesignLayoutDialog(String editText) {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View dialogView = layoutInflater.inflate(R.layout.dialog_text_edit_dialog, null);
		final Dialog alertDialog = new Dialog(getContext(), R.style.CustomDialog);
		alertDialog.setContentView(dialogView);
		alertDialog.show();
		final EditText dialogEdit = (EditText) dialogView.findViewById(R.id.dialog_design_layout_edit_et);
		dialogEdit.setText(editText);
		dialogView.findViewById(R.id.dialog_design_layout_delete_bt).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		dialogView.findViewById(R.id.dialog_design_layout_ok_bt).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				setTextString(dialogEdit.getText().toString());
			}
		});
		return alertDialog;
	}

	public AutoResizeTextView getTextView() {
		return mView;
	}

	// public void setMViewClickListener(final View.OnClickListener
	// viewOnClickListener){
	// mView.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// viewOnClickListener.onClick(v);
	// }
	// });
	// }

	private void parseAttr(Context context, AttributeSet attrs) {
		// if (null == attrs) return;
		this.mCenterInParent = true;
		Bitmap pushImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.design_cloth_icon_control_normal);
		this.mPushImageHeight = pushImageBitmap.getHeight();
		this.mPushImageWidth = pushImageBitmap.getWidth();
		Bitmap deleteImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.design_cloth_icon_delete_normal);
		this.mDeleteImageHeight = deleteImageBitmap.getHeight();
		this.mDeleteImageWidth = deleteImageBitmap.getWidth();
	}

	private void setViewToAttr(int pWidth, int pHeight) {

		View parentView = (View) this.getParent();
		RelativeLayout.LayoutParams parentLP = (RelativeLayout.LayoutParams) parentView.getLayoutParams();

		this.mView.measure(0, 0);
		this.mTextViewHeight = this.mView.getMeasuredHeight();
		this.mTextViewWidth = this.mView.getMeasuredWidth();

		FrameLayout.LayoutParams viewLP = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
		viewLP.width = (int) mTextViewWidth;
		viewLP.height = (int) mTextViewHeight;
		int left = 0, top = 0;
		if (mCenterInParent) {
			left = parentLP.width / 2 - viewLP.width / 2;
			top = parentLP.height / 2 - viewLP.height / 2;
		} else {
			if (mLeft > 0)
				left = mLeft;
			if (mTop > 0)
				top = mTop;
		}

		if (left < 0) {
			left *= (-1);
		}
		if (top < 0) {
			top *= (-1);
		}

		viewLP.leftMargin = left;
		viewLP.topMargin = top;
		this.mView.setLayoutParams(viewLP);

		FrameLayout.LayoutParams pushViewLP = (FrameLayout.LayoutParams) mPushView.getLayoutParams();
		pushViewLP.width = (int) mPushImageWidth;
		pushViewLP.height = (int) mPushImageHeight;
		pushViewLP.leftMargin = (int) (viewLP.leftMargin + mTextViewWidth - mPushImageWidth / 2);
		pushViewLP.topMargin = (int) (viewLP.topMargin + mTextViewHeight - mPushImageHeight / 2);
		mPushView.setLayoutParams(pushViewLP);

		FrameLayout.LayoutParams deleteViewLP = (FrameLayout.LayoutParams) mDeleteView.getLayoutParams();
		deleteViewLP.width = (int) mDeleteImageWidth;
		deleteViewLP.height = (int) mDeleteImageHeight;
		deleteViewLP.leftMargin = (int) (viewLP.leftMargin - mDeleteImageWidth / 2);
		deleteViewLP.topMargin = (int) (viewLP.topMargin - mDeleteImageHeight / 2);
		mDeleteView.setLayoutParams(deleteViewLP);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setParamsForView(widthMeasureSpec, heightMeasureSpec);
	}

	private boolean hasSetParamsForView = false;

	private void setParamsForView(int widthMeasureSpec, int heightMeasureSpec) {
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		if (null != layoutParams && !hasSetParamsForView) {
			hasSetParamsForView = true;
			int width;
			if ((getLayoutParams().width == LayoutParams.MATCH_PARENT)) {
				width = MeasureSpec.getSize(widthMeasureSpec);
			} else {
				width = getLayoutParams().width;
			}
			int height;
			if ((getLayoutParams().height == LayoutParams.MATCH_PARENT)) {
				height = MeasureSpec.getSize(heightMeasureSpec);
			} else {
				height = getLayoutParams().height;
			}
			setViewToAttr(width, height);
		}
	}

	/**
	 * 设置文字
	 * 
	 * @author chengang
	 * @date 2014-11-24 下午4:28:50
	 * @param text
	 */
	public void setTextString(String text) {
		if (text == null && pushListener != null) {
			return;
		}

		pushListener.setText(text);
	}

	public String getTextString() {
		if (mView != null && !StringUtil.isEmpty(mView.getText().toString())) {
			return mView.getText().toString();
		} else {
			return null;
		}

	}

	/**
	 * 设置文字大小
	 * 
	 * @author chengang
	 * @date 2014-11-24 下午4:08:11
	 * @param color
	 */
	public void setTextSize(int size) {
		if (this.mView == null) {
			return;
		}
		this.mView.setTextSize(size);
	}

	/**
	 * 设置文字颜色
	 * 
	 * @author chengang
	 * @date 2014-11-24 下午4:08:11
	 * @param color
	 */
	public void setTextColor(int color) {
		if (this.mView == null) {
			return;
		}
		this.mView.setTextColor(color);
	}

	/**
	 * 设置字体样式
	 * 
	 * @author chengang
	 * @date 2014-11-24 下午4:09:42
	 */
	public void setTextStyle(String fontName) {
		if (this.mView == null || fontName == null) {
			return;
		}
		// 添加设置字体样式的代码
		Typeface textFontFace = Typeface.createFromAsset(getContext().getAssets(), fontName);
		if (textFontFace != null) {
			this.mView.setTypeface(textFontFace);
		}
	}

	/**
	 * 设置字体的对齐方式，参数在这个函数中获取 TEXT_ALIGN_LEFT = Gravity.LEFT;左对齐 TEXT_ALIGN_RIGHT
	 * = Gravity.RIGHT;右对齐 TEXT_ALIGN_CENTER = Gravity.CENTER;中间对齐
	 * 
	 * @author chengang
	 * @date 2014-11-24 下午4:23:04
	 * @param alignValue
	 */
	public void setTextAlign(int alignValue) {
		if (this.mView == null) {
			return;
		}

		this.mView.setGravity(alignValue);
	}

	/**
	 * 隐藏相关控制控件以便于预览 author:陈刚 2014-12-2 descript:
	 */
	public void hideControlView() {
		if (mDeleteView != null && mPushView != null && mView != null) {
			mDeleteView.setVisibility(View.GONE);
			mPushView.setVisibility(View.GONE);
			mView.setBackgroundResource(0);
		}
	}
}
