package com.chuangmeng.fashiondiy.util;

import android.graphics.Bitmap;

public class SavePictureBean {
	private Bitmap waterBitmap;
	private float surfaceWidth;
	private float surfaceHeight;
	private byte[] byteData;
	
	public SavePictureBean(byte[] tempByteData , Bitmap tempBitmap , float width , float height){
		this.waterBitmap = tempBitmap;
		this.surfaceWidth = width;
		this.surfaceHeight = height;
		this.byteData = tempByteData;
	}
	
	public Bitmap getBitmap(){
		return waterBitmap;
	}
	
	public float getWidth(){
		return surfaceWidth;
	}
	
	public float getHeight(){
		return surfaceHeight;
	}
	public byte[] getByteData(){
		return byteData;
	}
}
