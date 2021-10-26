package com.example.factoryapplication.common.utils;


import android.app.Activity;
import android.content.Context;
import android.view.Display;

public class ScreenUtil {
	 /** 屏幕宽 */
	public static int screenWidth;
	/** 屏幕高 */
	public static int screenHeight;


	public static int getScreenWidth(Context ctx) {
		Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay(); // 获得屏幕显示
		return display.getWidth();
	}


	public static int getScreenHeight(Context  ctx) {
		Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay(); // 获得屏幕显示
		return display.getHeight();
	}

	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
}
