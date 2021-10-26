//package com.example.factoryapplication;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.example.factoryapplication.common.widget.LoadListView;
//import com.lidroid.xutils.util.LogUtils;
//import com.ultrapower.tomas.gsinfowinene.common.widget.LoadListView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**公告查询
// * Created by dyc on 2016/10/9.
// */
//public class NoticeQueryFragment extends BaseFragment implements LoadListView.ILoadListener {
//
//    private Activity activity ;
//    private View view ;
//    private LoadListView eomsListView;
//
//    NoticeListPageEntity pageEntity ;
//    int startPage = 1 , pageSize = 10 ;
//
//    NoticeListAdapter adapter ;
//
//    public NoticeQueryFragment(){}
//
//    boolean isRead = false ;
//
//    String startTime, endTime ;
//
//    public void setIsRead(boolean isRead) {
//        this.isRead = isRead;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_eoms_query, null);
//        initView();
//        init();
//
////        searchData();
//        return view;
//    }
//
//    public void setTime(String startTime,String endTime) {
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//    public void searchData(String startTime,String endTime,boolean isRead) {
//        if ((!TextUtils.isEmpty(startTime)&&!startTime.equals(this.startTime))|| (!TextUtils.isEmpty(endTime)&&!endTime.equals(this.endTime))){
//            startPage = 1 ;
//            if(pageEntity != null) {
//                pageEntity.content.clear();
//                adapter.notifyDataSetChanged();
//            }
//        }
//        this.startTime = startTime ;
//        this.endTime = endTime ;
//
//        this.isRead = isRead ;
//
//        LogUtils.w("go in searchData" + isRead);
//        HashMap<String,String> map = new HashMap<>() ;
////        map.put("search-REGION-eq-string" ,Constant.strRegion ) ;
//        map.put("search-STARTTIME-eq-string" ,startTime ) ;
//        map.put("search-ENDTIME-eq-string", endTime) ;
//        map.put("page", ""+startPage) ;
//        map.put("rows", "" + pageSize) ;
//        map.put("time", "" + System.currentTimeMillis()) ;
//        if (isRead)map.put("search-ISREAD-eq-string", "" + 1) ;
//        else map.put("search-ISREAD-eq-string", "" + 0) ;
//        if (!TextUtils.isEmpty(Constant.strRegion))
//            sendByPost(Constant.BUSINESS_NOTICE_LIST + "?" + "&search-REGION-eq-string=" +Constant.strRegion , map);
//        else
//            sendByPost(Constant.BUSINESS_NOTICE_LIST + "?search-CRMMANAGER-eq-string=" +  Constant.cmNo , map);
//    }
//
//    private void initView() {
//
//        pageEntity = new NoticeListPageEntity();
//        pageEntity.content = new ArrayList<NoticeListEntity>() ;
//
//        eomsListView = (LoadListView) view.findViewById(R.id.eoms_list);
//        adapter = new NoticeListAdapter(activity,pageEntity.content,R.layout.notice_list_item) ;
//        eomsListView.setAdapter(adapter);
//
//        eomsListView.setInterface(this);
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity ;
//        LogUtils.w("attach" + isRead);
//    }
//
//    @Override
//    public void onRetSuccess(String result) {
//        if(result!=null&&!result.equals("")) {
//            String jsonArray = String.valueOf(JSON.parse(result));
//            NoticeListPageEntity list = JSON.parseObject(jsonArray, NoticeListPageEntity.class);
//            if(list != null ){
//                pageEntity.content.addAll(list.content);
//                //在主线程里更新
//                adapter.notifyDataSetChanged();
//                eomsListView.loadComplete() ;
//            }else{
//                Toast.makeText(activity, "没有查询到数据",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onRetFailure(String msg) {
//
//    }
//
//
//    @Override
//    public void onLoad() {
//        startPage ++ ;
//        searchData(startTime ,endTime,isRead);
//    }
//}
