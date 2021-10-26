package com.example.factoryapplication.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.os.Environment;
import android.text.format.Time;

/**
 * 自定义Log输出
 * 
 * @author Administrator
 * 
 * @version 1.1.0 1、对代码按照规范进行编写 2、增加写入到文件功能,包括执行懿¨行数、时间ᾔAG(类名)、信忿
 * @author dzyssssss 2013.07.05
 * 
 * @PS: <!--SDCard写入数据权限 --> <uses-permission
 *      android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!--
 *      在SDCard中创建与删除文件权限 --> <uses-permission
 *      android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
 * 
 */
public class LogHelper {

	/** 是否输出日志 */
	public static final boolean IS_PRINT = true;

	/** 返回便*/
	private static final int RETURE = 6;

	private LogHelper() {
	}

	/**
	 * 黑色字样，任何信息都输出
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int v(String paramTag, String paramMsg) {

		try {
			if (IS_PRINT) {
				return android.util.Log.v(paramTag, paramMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return RETURE;
	}

	/**
	 * 输出信息和输出的异常
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return
	 */
	public static int v(String paramTag, String paramMsg, Throwable paramTr) {

		try {
			if (IS_PRINT) {
				return android.util.Log.v(paramTag, paramMsg, paramTr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 字体为蓝色，仅输出debug调试的意思，过滤起来可以通过DDMS的Logcat标签来耦?
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int d(String paramTag, String paramMsg) {

		try {
			if (IS_PRINT) {
				return android.util.Log.d(paramTag, paramMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 调试，日志信息和异常信息
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return
	 */
	public static int d(String paramTag, String paramMsg, Throwable paramTr) {

		try {
			if (IS_PRINT) {
				return android.util.Log.d(paramTag, paramMsg, paramTr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 绿色字体，一般提示倧消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信忿
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int i(String paramTag, String paramMsg) {

		try {
			if (IS_PRINT) {
				return android.util.Log.i(paramTag, paramMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 日志信息和异常信忿
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return
	 */
	public static int i(String paramTag, String paramMsg, Throwable paramTr) {

		try {
			if (IS_PRINT) {
				return android.util.Log.i(paramTag, paramMsg, paramTr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 橙色，警叿优化Android代码，会输出log.e的信忿
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int w(String paramTag, String paramMsg) {

		try {
			if (IS_PRINT) {
				return android.util.Log.w(paramTag, paramMsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RETURE;
	}

	/**
	 * 警告，日志信息和日志异常
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return
	 */
	public static int w(String paramTag, String paramMsg, Throwable paramTr) {
		try {
			if (IS_PRINT)
				return android.util.Log.w(paramTag, paramMsg, paramTr);
		} catch (Exception e) {
		}
		return RETURE;
	}

	/**
	 * 红色，错误信忿
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int e(String paramTag, String paramMsg) {
		try {
			if (IS_PRINT)
				return android.util.Log.e(paramTag, paramMsg);
		} catch (Exception e) {
		}
		return RETURE;
	}

	/**
	 * 错误信息和异常信忿
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return
	 */
	public static int e(String paramTag, String paramMsg, Throwable paramTr) {
		try {
			if (IS_PRINT)
				return android.util.Log.e(paramTag, paramMsg, paramTr);
		} catch (Exception e) {
		}
		return RETURE;
	}

	/**
	 * 
	 * @param tr
	 * @return
	 */
	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	/*
	 * 得到函数执行懿¨的行擿
	 * 
	 * @param
	 * 
	 * @return
	 */
	private static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[5].getLineNumber();
	}

	/*
	 * get current time
	 * 
	 * @param
	 * 
	 * @return
	 */
	private static String getCurrentTime() {
		String strCurTime = null;

		Time t = new Time(); //
		// Time t = new Time("GMT+8"); //加上Time Zone⾿

		t.setToNow(); // 取得系统时间⾿

		strCurTime = t.format(t.format3339(false)); // format3339 format2445

		return strCurTime;
	}

	
	/**
	 * 功能：记录日ｿ
	 * 
	 * @param paramLogFileFullName
	 *            保存日志文件名，若该参数为null，则保存ｿsd/tsmclient.log"ｿ
	 * @param paramDataStr
	 *            保存日志数据
	 * @param paramType
	 *            保存类型，true为覆盖保存，false为在原来文件后添加保ｿ
	 */
	public static void recordLog(String paramTag, String paramLogFileFullName,
			String paramData, boolean paramOverwite) {

		try {

			if (!IS_PRINT) {
				return;
			}

			if (paramData == null) {
				return;
			}

			if (paramLogFileFullName == null) {
				boolean boSDCardExist = Environment.getExternalStorageState()
						.equals(android.os.Environment.MEDIA_MOUNTED);

				if (boSDCardExist) {
					paramLogFileFullName = Environment
							.getExternalStorageDirectory().getPath();
					paramLogFileFullName += File.separator;
					paramLogFileFullName += "tsmclinet_apud.txt";
				}
			}

			File fileSave = new File(paramLogFileFullName);
			if (!fileSave.exists()) {
				//fileSave.mkdirs();
				fileSave.createNewFile();
			}

			String strLine = String.valueOf(getLineNumber());
			String strTime = getCurrentTime();

			FileOutputStream fosStream = null;

			if (paramOverwite) {

				if (fileSave.exists()) {
					fileSave.delete();
				}

				fileSave.createNewFile();
			} else {
				if (!fileSave.exists()) {
					fileSave.createNewFile();
				}
			}

			fosStream = new FileOutputStream(fileSave, true);
			fosStream.write((paramTag + "  Time:" + strTime + "  Line:"
					+ strLine + "  Data:" + paramData + "\r\n").getBytes());
			fosStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
