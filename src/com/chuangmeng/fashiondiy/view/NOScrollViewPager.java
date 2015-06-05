/**
 * NOScrollViewPager.java
 * com.chuangmeng.fashiondiy.view
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月13日 		hch
 *
 * Copyright (c) 2014, TNT All Rights Reserved.
 */

package com.chuangmeng.fashiondiy.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ClassName:NOScrollViewPager Function: TODO ADD FUNCTION Reason: TODO ADD
 * REASON
 * 
 * @author hch
 * @version
 * @since Ver 1.1
 * @Date 2014年11月13日 下午11:51:54
 * 
 */
public class NOScrollViewPager extends ViewPager {

//	private boolean isCanScroll = false;

	public NOScrollViewPager(Context context) {
		super(context);
	}

	public NOScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

   @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
