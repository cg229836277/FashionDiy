package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.R;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

class ViewOnTouchListener implements View.OnTouchListener {
    Point pushPoint;
    Point deletePoint;
    int lastImgLeft;
    int lastImgTop;
    FrameLayout.LayoutParams viewLP;
    FrameLayout.LayoutParams pushBtnLP;
    FrameLayout.LayoutParams deleteBtnLP;
    int lastPushBtnLeft;
    int lastPushBtnTop;
    
    int lastDeleteBtnLeft;
    int lastDeleteBtnTop;
    
    private View mPushView;
    private View mDeleteView;
    
    private boolean isMove = false;
    private DesignDetailTextTouchInterface designDetailTextTouchInterface;
    private AutoResizeTextView mView;
    private int count = 0;
	private long firClick ,secClick;

	ViewOnTouchListener(View mPushView , View mDeleteView,AutoResizeTextView mView,DesignDetailTextTouchInterface designDetailTextTouchInterface) {
        this.mPushView = mPushView;
        this.mDeleteView = mDeleteView;
        this.mView = mView;
        this.designDetailTextTouchInterface = designDetailTextTouchInterface;
    }
    
    ViewOnTouchListener(View mPushView , View mDeleteView) {
        this.mPushView = mPushView;
        this.mDeleteView = mDeleteView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
    	setBorderLineVisiable(view , true);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
		            count++;  
		            if(count == 1){  
		                firClick = System.currentTimeMillis();  
		                  
		            } else if (count == 2){  
		                secClick = System.currentTimeMillis();  
		                if(secClick - firClick < 1000){  
		                    //双击事件  
		                	if(designDetailTextTouchInterface != null){
		                		designDetailTextTouchInterface.twoTouch();
		                	}
		                }  
		                count = 0;  
		                firClick = 0;  
		                secClick = 0;  
		                  
		        }  
            	
            	if(designDetailTextTouchInterface != null){
            		designDetailTextTouchInterface.touchListener(mView);
            	}
            	isMove = false;
                if (null == viewLP) {
                    viewLP = (FrameLayout.LayoutParams) view.getLayoutParams();
                }
                if (null == pushBtnLP) {
                    pushBtnLP = (FrameLayout.LayoutParams) mPushView.getLayoutParams();
                }
                
                if(null == deleteBtnLP){
                	deleteBtnLP = (FrameLayout.LayoutParams) mDeleteView.getLayoutParams();
                }
                
                pushPoint = getRawPoint(event);
                lastImgLeft = viewLP.leftMargin;
                lastImgTop = viewLP.topMargin;
                lastPushBtnLeft = pushBtnLP.leftMargin;
                lastPushBtnTop = pushBtnLP.topMargin;
                
                deletePoint = getRawPoint(event);
                lastDeleteBtnLeft = deleteBtnLP.leftMargin;
                lastDeleteBtnTop = deleteBtnLP.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
            	isMove = true;
                Point newPoint = getRawPoint(event);
                float moveX = newPoint.x - pushPoint.x;
                float moveY = newPoint.y - pushPoint.y;

                viewLP.leftMargin = (int) (lastImgLeft + moveX);
                viewLP.topMargin = (int) (lastImgTop + moveY);
                view.setLayoutParams(viewLP);

                pushBtnLP.leftMargin = (int) (lastPushBtnLeft + moveX);
                pushBtnLP.topMargin = (int) (lastPushBtnTop + moveY);
                mPushView.setLayoutParams(pushBtnLP);
                
                deleteBtnLP.leftMargin = (int) (lastDeleteBtnLeft + moveX);
                deleteBtnLP.topMargin = (int) (lastDeleteBtnTop + moveY);
                mDeleteView.setLayoutParams(deleteBtnLP);

                return false;
                
            case MotionEvent.ACTION_UP:
            	if(view != null){
                	setIconImageState(view);
                	setBorderLineVisiable(view , false);
            	}
            	if(isMove){
            		return true;
            	}
            	break;
        }
        return false;
    }


    private Point getRawPoint(MotionEvent event) {
        return new Point((int) event.getRawX(), (int) event.getRawY());
    }
    
	/**
	 * 设置设计边界线的显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-21 下午2:24:49
	 */
	public void setBorderLineVisiable(View imageView , boolean isShow) {
		View view = (View)imageView.getParent().getParent();
		if (isShow) {
			view.setBackgroundResource(R.drawable.design_border_line);
		} else {
			view.setBackgroundResource(0);
		}
	}
	
	/**
	 * 设置图片操作（删除旋转操作）的显示与隐藏
	 * 
	 * @author chengang
	 * @date 2014-11-20 下午3:50:03
	 * @param imageView
	 */
	public void setIconImageState(View imageView) {
		if (imageView.getTag() instanceof Boolean && mDeleteView != null && mPushView != null) {
			boolean flag = (Boolean) imageView.getTag();
			if (flag) {
				imageView.setBackgroundColor(Color.TRANSPARENT);
				imageView.setTag(false);
				this.mDeleteView.setVisibility(View.GONE);
				this.mPushView.setVisibility(View.GONE);
			} else {
				imageView.setBackgroundResource(R.drawable.design_icon_image_background);
				imageView.setTag(true);
				this.mDeleteView.setVisibility(View.VISIBLE);
				this.mPushView.setVisibility(View.VISIBLE);
			}
		}
	}
}
