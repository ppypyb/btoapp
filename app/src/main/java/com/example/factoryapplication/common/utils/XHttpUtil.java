//package com.example.factoryapplication.common.utils;
//
//import java.util.Map;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
///**
// * 使用xutils发送http
// * @author dyc
// *
// */
//public class XHttpUtil {
//	/**
//	 * 连接超时时间
//	 */
//	private static final int CONNTIMEOUT = 3000;
//
//	HttpUtils httpUtils = new HttpUtils(CONNTIMEOUT) ;;
//
//	private void init(){
//		httpUtils.configSoTimeout(CONNTIMEOUT) ;
//	}
//
//	/**
//	 * 通过 GET 发送
//	 * @param url
//	 * @param callback
//	 */
//	public void sendByGet(String url ,RequestCallBack<String> callback){
//		init();
//		httpUtils.send(HttpMethod.GET, url, callback) ;
//	}
//
//	public void sendByPost(String url, Map<String,String> map,RequestCallBack<String> callback) {
//		RequestParams params = new RequestParams();
//		for (String key : map.keySet()) {
//			params.addBodyParameter(key, map.get(key));
//		}
//		sendByPost(url, params, callback);
//	}
//
//	/**
//	 * 通过 POST 发送
//	 * @param url
//	 * @param params
//	 * @param callback
//	 */
//	public void sendByPost(String url, RequestParams params,RequestCallBack<String> callback) {
//		init();
//		httpUtils.send(HttpMethod.POST, url, params,callback);
//	}
//}
