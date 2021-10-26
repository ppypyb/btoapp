package com.example.factoryapplication.common.base.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.Constant;
import com.example.factoryapplication.common.widget.CustomProgressDialog;
import com.example.factoryapplication.entity.scdj.OcrEntity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.Map;

/**
 * http功能Activity
 * @author dyc
 *
 */
public abstract class HttpFileActivity extends BaseActivity {

	/**
	 * 连接超时时间
	 */
	private static final int CONNTIMEOUT = 3000;

	HttpUtils httpUtils ;

	//等待对话框
	public CustomProgressDialog progressDia ;

	private void init(){
		httpUtils = new HttpUtils(CONNTIMEOUT) ;
		httpUtils.configSoTimeout(CONNTIMEOUT) ;

		progressDia = new CustomProgressDialog(this) ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init() ;
	}

	public void onHttpStart(){
		progressDia.show();
	}

	/**
	 * @param msg
	 * 正常Http返回处理
	 */
	public abstract void onRetSuccess(String msg) ;
	/**
	 *失败Http 处理
	 * @param msg
	 */
	public abstract void onRetFailure(String msg) ;

	/**
	 * @param msg
	 * 正常Http返回处理
	 */
	public abstract void onRetSuccessFile(String msg) ;
	/**
	 *失败Http 处理
	 * @param msg
	 */
	public abstract void onRetFailureFile(String msg) ;

	public void sendByGet(String url){
		Log.i("Http","Url=" + url) ;

		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>(){
			@Override
			public void onStart() {
				super.onStart();
				onHttpStart();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				progressDia.dismiss();
				onRetFailure(arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				progressDia.dismiss();
				onRetSuccess(arg0.result);
			}}) ;
	}


	public void sendByPost(String url, Map<String, String> map) {
		RequestParams params = new RequestParams();
		for (String key : map.keySet()) {
			params.addBodyParameter(key, map.get(key));
		}

		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						super.onStart();
						onHttpStart();
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						onRetFailure(arg1);
						progressDia.dismiss();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						onRetSuccess(arg0.result);
						progressDia.dismiss();
					}
				});
	}

	public void sendFilePost(String url, RequestParams params) {

		HttpUtils http = new HttpUtils();
		http.configSoTimeout(95000) ;
		http.send(HttpMethod.POST,
				Constant.BUSINESS_OCR,
				params, new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String resultStr = responseInfo.result;
						Log.e("1", "上传成功：" + resultStr);

						if(resultStr.contains("numbers")) {
                            String jsonArray = String.valueOf(JSON.parse(resultStr));
                            OcrEntity entity = JSON.parseObject(jsonArray, OcrEntity.class);
//						ToastUtil.show(ScdjActivityF.this,entity.getData().getNumbers(), Toast.LENGTH_LONG);
//						return entity.getData().getNumbers();
                            onRetSuccessFile(entity.getData().getNumbers());
                        }
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Log.e("1", "上传失败：" + error.getExceptionCode() + ":" + msg);
					}
				});
	}
}
