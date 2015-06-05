package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 自定义ImageView ， 用于在衣服上面放置icon
 * @Title：FashionDIY
 * @Description：
 * @date 2014-11-24 下午4:37:54
 * @author chengang
 * @version 1.0
 */
public class SingleFingerView extends FrameLayout {
    private ImageView mView;
    private ImageView mPushView;
    private ImageView mDeleteView;
    private float _1dp;
    private boolean mCenterInParent = true;
    private float mImageHeight, mImageWidth, mPushImageHeight, mPushImageWidth ,mDeleteImageHeight , mDeleteImageWidth;
    private int mLeft = 0, mTop = 0;


    public SingleFingerView(Context context , Bitmap sourceBitmap) {
        this(context, null, 0 , sourceBitmap);
    }

    public SingleFingerView(Context context, AttributeSet attrs, int defStyle , Bitmap sourceBitmap) {
        super(context, attrs, defStyle);
        this._1dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
        this.parseAttr(context , sourceBitmap);
        View mRoot = View.inflate(context, R.layout.design_cloth_self_image_view, null);
        addView(mRoot);
        mPushView = (ImageView) mRoot.findViewById(R.id.push_view);
        mView = (ImageView) mRoot.findViewById(R.id.view);
        mView.setImageBitmap(sourceBitmap);
        mView.setTag(false);
        mDeleteView = (ImageView) mRoot.findViewById(R.id.delete_view);
        mPushView.setOnTouchListener(new PushBtnTouchListener(mView , mDeleteView));
        mView.setOnTouchListener(new ViewOnTouchListener(mPushView , mDeleteView));
        mDeleteView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SingleFingerView.this.setVisibility(View.GONE);
			}
		});
    }


    private void parseAttr(Context context, Bitmap sourceBitmap) {
        if (sourceBitmap == null) return;
        
        this.mCenterInParent = true;
        this.mImageHeight = sourceBitmap.getHeight();
        this.mImageWidth = sourceBitmap.getWidth();
        Bitmap pushImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.design_cloth_icon_control_normal);
        this.mPushImageHeight = pushImageBitmap.getHeight();
        this.mPushImageWidth = pushImageBitmap.getWidth();
        Bitmap deleteImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.design_cloth_icon_delete_normal);
        this.mDeleteImageHeight = deleteImageBitmap.getHeight();
        this.mDeleteImageWidth = deleteImageBitmap.getWidth();
    }

    private void setViewToAttr(int width , int height) {
    	View parentView = (View)this.getParent();
    	RelativeLayout.LayoutParams parentLP = (RelativeLayout.LayoutParams)parentView.getLayoutParams();
    	
        FrameLayout.LayoutParams viewLP = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
        viewLP.width = (int) mImageWidth;
        viewLP.height = (int) mImageHeight;
        int left = 0, top = 0;
        if (mCenterInParent) {
            left = parentLP.width / 2 - viewLP.width / 2;
            top = parentLP.height / 2 - viewLP.height / 2;
        } else {
            if (mLeft > 0) left = mLeft;
            if (mTop > 0) top = mTop;
        }
        
        if(left < 0){
        	left *= (-1);
        }
        if(top < 0){
        	top *= (-1);
        }
        
        viewLP.leftMargin = left;
        viewLP.topMargin = top;
        this.mView.setLayoutParams(viewLP);

        FrameLayout.LayoutParams pushViewLP = (FrameLayout.LayoutParams) mPushView.getLayoutParams();
        pushViewLP.width = (int) mPushImageWidth;
        pushViewLP.height = (int) mPushImageHeight;
        pushViewLP.leftMargin = (int) (viewLP.leftMargin + mImageWidth - mPushImageWidth / 2);
        pushViewLP.topMargin = (int) (viewLP.topMargin + mImageHeight - mPushImageHeight / 2);
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
     * 隐藏相关控制控件以便于预览
     * author:陈刚
     * 2014-12-2
     * descript:
     */
    public void hideControlView(){
    	if(mDeleteView != null && mPushView != null && mView != null){
			mDeleteView.setVisibility(View.GONE);
			mPushView.setVisibility(View.GONE);
			mView.setBackgroundResource(0);
    	}
    }
}
