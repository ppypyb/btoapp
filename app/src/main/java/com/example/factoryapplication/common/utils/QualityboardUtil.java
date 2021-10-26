//package com.example.factoryapplication.common.utils;;
//
//import com.ultrapower.tomas.qualityboard.R;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.Display;
//
//public class QualityboardUtil {
//	private static String[] refundService_TitleArray = new String[]{"网络类型","分公司","退服基站数","停电基站数","基站总数","退服小区数","小区总数"};
//	private static String[] groupList_2GData = new String[]{"2G基站退服","2G基站停电","2G小区退服"};
//	private static String[] groupList_4GData = new String[]{"4G基站退服","4G小区退服"};
//	private static String performWarn_titleArray[] = {"性能告警统计", "性能告警查询"};
//	private static String netOptimize_titleArray[] = {"指标名称", "统计时间", "问题小区数"};
//	 /** 屏幕宽 */
//	public static int screenWidth;
//	/** 屏幕高 */
//	public static int screenHeight;
//
//	public static String[] getRefundService_TitleArray(Context c) {
//
//		refundService_TitleArray = c.getResources().getStringArray(R.array.refund_Service_title);
//		return refundService_TitleArray;
//	}
//
//	public static int getScreenWidth(Context ctx) {
//		Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay(); // 获得屏幕显示
//		return display.getWidth();
//	}
//
//
//	public static int getScreenHeight(Context  ctx) {
//		Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay(); // 获得屏幕显示
//		return display.getHeight();
//	}
//
//	public static String[] getGroupList_2GData(Context  ctx) {
//		groupList_2GData = ctx.getResources().getStringArray(R.array.refund_Service_2G_warn);
//		return groupList_2GData;
//	}
//
//	public static String[] getGroupList_4GData(Context  ctx) {
//		groupList_4GData = ctx.getResources().getStringArray(R.array.refund_Service_4G_warn);
//		return groupList_4GData;
//	}
//
//	public static String[] getPerformWarn_titleArray(Context c) {
//
//		refundService_TitleArray = c.getResources().getStringArray(R.array.performance_title_tab);
//		return refundService_TitleArray;
//	}
//
//	public static String[] getNetOptimize_titleArray(Context c) {
//
//		refundService_TitleArray = c.getResources().getStringArray(R.array.perform_netOptimize_title_tab);
//		return refundService_TitleArray;
//	}
//}
