package com.chuangmeng.fashiondiy.view;

import com.chuangmeng.fashiondiy.util.StringUtil;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 控制按钮的事件监听接口
 * @Title：FashionDIY
 * @Description：
 * @date 2014-11-26 下午4:10:07
 * @author chengang
 * @version 1.0
 */
class PushBtnTouchListener implements View.OnTouchListener {
    Point pushPoint;
    Point deletePoint;
    
    int lastImgWidth;
    int lastImgHeight;
    int lastImgLeft;
    int lastImgTop;
    int lastImgAngle;
    double lastComAngle;

    int pushImgWidth;
    int pushImgHeight;
    
    int deleteImgWidth;
    int deleteImgHeight;
    
    float lastTextSize;

    int lastPushBtnLeft;
    int lastPushBtnTop;
    
    int lastDeleteBtnLeft;
    int lastDeleteBtnTop;


    private View mView;
    private View mDeleteView;
    private Point mViewCenter;
    private static final double PI = 3.14159265359;
    private String TAG = "PushOnTouch";
    AutoResizeTextView currentTextView = null;    

    /**
     * 构造函数
     * @param mView  控制的对象，ImageView或者TextView
     * @param deleteView  删除按钮的视图对象
     */
    public PushBtnTouchListener(View mView , View deleteView) {
		this.mView = mView;
		this.mDeleteView = deleteView;
		if (mView instanceof AutoResizeTextView) {
			currentTextView = (AutoResizeTextView) mView;
		}
    }

    private FrameLayout.LayoutParams pushBtnLP;
    private FrameLayout.LayoutParams imgLP;
    private FrameLayout.LayoutParams deleteImgLP;
    float lastX = -1;
    float lastY = -1;

    @Override
    public boolean onTouch(View pushView, MotionEvent event) {   	
    	
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 主点按下
            case MotionEvent.ACTION_DOWN:
                pushBtnLP = (FrameLayout.LayoutParams) pushView.getLayoutParams();
                imgLP = (FrameLayout.LayoutParams) mView.getLayoutParams();
                deleteImgLP = (FrameLayout.LayoutParams) mDeleteView.getLayoutParams();

                pushPoint = getPushPoint(pushBtnLP, event);
                lastImgWidth = imgLP.width;
                lastImgHeight = imgLP.height;
                lastImgLeft = imgLP.leftMargin;
                lastImgTop = imgLP.topMargin;
                
    			if (currentTextView != null) {
    				lastTextSize = currentTextView.getTextSize();
    			}
                
                deletePoint = getPushPoint(deleteImgLP, event);
                
                lastImgAngle = (int) mView.getRotation();

                lastPushBtnLeft = pushBtnLP.leftMargin;
                lastPushBtnTop = pushBtnLP.topMargin;
                
                lastDeleteBtnLeft = deleteImgLP.leftMargin;
                lastDeleteBtnTop = deleteImgLP.topMargin;

                pushImgWidth = pushBtnLP.width;
                pushImgHeight = pushBtnLP.height;
                
                deleteImgWidth = deleteImgLP.width;
                deleteImgHeight = deleteImgLP.height;
                
                lastX = event.getRawX();
                lastY = event.getRawY();
                refreshImageCenter();
                break;
            // 副点按下
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                if (lastX != -1) {
                    if (Math.abs(rawX - lastX) < 5 && Math.abs(rawY - lastY) < 5) {
                        return false;
                    }
                }
                lastX = rawX;
                lastY = rawY;

                Point O = mViewCenter, A = pushPoint, B = getPushPoint(pushBtnLP, event);
                float dOA = getDistance(O, A);
                float dOB = getDistance(O, B);
                float f = dOB / dOA;
                
//                Log.e(this.getClass().toString(), "字体大小是!!!!!" + lastTextSize);
//                Log.e(this.getClass().toString(), "缩放率是" + f);
    			float textViewSize = lastTextSize * f;
    			if (currentTextView != null) {
    				Log.e(this.getClass().toString(), "字体大小是#####" + textViewSize);
    				currentTextView.setTextSize(textViewSize);	
//    				lastTextSize = textViewSize;
    			}

                int newWidth = (int) (lastImgWidth * f);
                int newHeight = (int) (lastImgHeight * f);


                imgLP.leftMargin = lastImgLeft - ((newWidth - lastImgWidth) / 2);
                imgLP.topMargin = lastImgTop - ((newHeight - lastImgHeight) / 2);
                imgLP.width = newWidth;
                imgLP.height = newHeight;
                mView.setLayoutParams(imgLP);

                float fz = (((A.x - O.x) * (B.x - O.x)) + ((A.y - O.y) * (B.y - O.y)));
                float fm = dOA * dOB;
                double comAngle = (180 * Math.acos(fz / fm) / PI);
                if (Double.isNaN(comAngle)) {
                    comAngle = (lastComAngle < 90 || lastComAngle > 270) ? 0 : 180;
                } else if ((B.y - O.y) * (A.x - O.x) < (A.y - O.y) * (B.x - O.x)) {
                    comAngle = 360 - comAngle;
                }
                lastComAngle = comAngle;

                float angle = (float) (lastImgAngle + comAngle);
                angle = angle % 360;
                mView.setRotation(angle);
                mDeleteView.setRotation(angle);
                
                Point imageRB = new Point(mView.getLeft() + mView.getWidth(), mView.getTop() + mView.getHeight());
                Point anglePoint = getAnglePoint(O, imageRB, angle);

                pushBtnLP.leftMargin = (int) (anglePoint.x - pushImgWidth / 2);
                pushBtnLP.topMargin = (int) (anglePoint.y - pushImgHeight / 2);
                pushView.setLayoutParams(pushBtnLP);           
                
                int deltaLeft = pushBtnLP.leftMargin - lastPushBtnLeft;
                int deltaTop = pushBtnLP.topMargin - lastPushBtnTop;
                
                deleteImgLP.leftMargin = lastDeleteBtnLeft - deltaLeft;
                deleteImgLP.topMargin = lastDeleteBtnTop - deltaTop;
                mDeleteView.setLayoutParams(deleteImgLP);
                
                break;
        }
        return false;
    }

    private void refreshImageCenter() {
        int x = mView.getLeft() + mView.getWidth() / 2;
        int y = mView.getTop() + mView.getHeight() / 2;
        mViewCenter = new Point(x, y);
    }


    private Point getPushPoint(FrameLayout.LayoutParams lp, MotionEvent event) {
        return new Point(lp.leftMargin + (int) event.getX(), lp.topMargin + (int) event.getY());
    }

    private float getDistance(Point a, Point b) {
        float v = ((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y));
        return ((int) (Math.sqrt(v) * 100)) / 100f;
    }

    private Point getAnglePoint(Point O, Point A, float angle) {
        int x, y;
        float dOA = getDistance(O, A);
        double p1 = angle * PI / 180f;
        double p2 = Math.acos((A.x - O.x) / dOA);
        x = (int) (O.x + dOA * Math.cos(p1 + p2));

        double p3 = Math.acos((A.x - O.x) / dOA);
        y = (int) (O.y + dOA * Math.sin(p1 + p3));
        return new Point(x, y);
    }
    
	public void setText(String text){
		currentTextView.setText(text);
		currentTextView.setMaxTextSize(200);
	}
}
