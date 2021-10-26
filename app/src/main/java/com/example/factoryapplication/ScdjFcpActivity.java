package com.example.factoryapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.JlcxGxListAdapter;
import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.common.utils.HttpHelper;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.common.widget.LabelInputView;
import com.example.factoryapplication.entity.JlcxGxEntity;
import com.example.factoryapplication.entity.JlcxGxListEntity;
import com.example.factoryapplication.entity.JlcxRetEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.example.factoryapplication.entity.scdj.GwgxRetEneity;
import com.example.factoryapplication.entity.scdj.ScdjYyEntity;
import com.example.factoryapplication.entity.scdj.ScdjYyItemEntity;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 废次品登记
 * Created by dyc on 2016/10/12.
 */

public class ScdjFcpActivity extends HttpActivity{

    private static final String TAG = ScdjFcpActivity.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;


    int clickCur = 0 ;
    public String startTime ,endTime ;


    @ViewInject(R.id.fcpdj_et11)
    EditText fcpdj_et11 ;
    @ViewInject(R.id.fcpdj_et12)
    EditText fcpdj_et12 ;
    @ViewInject(R.id.fcpdj_et13)
    EditText fcpdj_et13 ;

    @ViewInject(R.id.ll_dd)
    LinearLayout ll_dd ;

    @ViewInject(R.id.scdj_sub_btn)
    Button scdj_sub_btn ;
    @ViewInject(R.id.scdj_can_btn)
    Button scdj_can_btn ;


    List<JlcxGxEntity> list ;
    Map<String ,JlcxGxEntity> map = new HashMap<String ,JlcxGxEntity>() ;

    Map<String,EditText> etMap = new HashMap<String,EditText>() ;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
//            ToastUtil.show(ScdjFcpActivity.this,"请求结果:" + val ,Toast.LENGTH_LONG);

            Intent intent = new Intent() ;
            intent.putExtra("total",""+total) ;
            setResult(RESULT_OK, intent);
            finish();
        }
    } ;


    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            String httpRet = HttpHelper.sendJsonPost(Constant.BUSINESS_FCPDJ,json) ;
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value","请求结果" + httpRet);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    } ;

    private Activity activity ;
    private View view ;

    String djbh = null;
    private String fclb ,jjdh,rybh,gzdh ,ddbh;
    private String gxbh;

    int total = 0 ;
    private  String json;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent() ;
        djbh = intent.getStringExtra("djbh") ;
        fclb = intent.getStringExtra("fclb") ;
        gxbh = intent.getStringExtra("gxbh") ;

        jjdh  = intent.getStringExtra("jjdh") ;
        rybh= intent.getStringExtra("rybh") ;
        gzdh= intent.getStringExtra("gzdh") ;

        ddbh= intent.getStringExtra("ddbh") ;

//        djbh = "0001" ;
//        fclb = "0111" ;
//        gxbh = "2111" ;
        setContentView(R.layout.activity_scdj_fcp);

        activity = this;
        setTitle("废次品登记");

        initView() ;
    }

    /**
     * 取消选择
     */
    public void onCancelSel() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private void initView() {
        scdj_can_btn.setOnClickListener(this);
        scdj_sub_btn.setOnClickListener(this);
        String rybh = SPUtils.get(activity,"yhbh","").toString() ;

        sendByGet(Constant.BUSINESS_FPYY + "?gxbh=" + gxbh +"&fclb=" + fclb + "&gzdh=" + gzdh + "&jjdh=" + jjdh + "&rybh=" + rybh + "&ddbh=" + ddbh);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        switch (id){
            case R.id.scdj_sub_btn:
                List<Map> list = new ArrayList<>() ;

                Iterator iter = etMap.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    EditText val = etMap.get(key);
                    String str = val.getText().toString() ;

                    if(!TextUtils.isEmpty(str)) {
                        int num = Integer.parseInt(str);

                        Map item = new HashMap();
                        item.put("yy", key);
                        item.put("sl", num);
                        list.add(item);
                        total += num;
                    }
                }
                Map<String, Object> map = new HashMap<>() ;
                map.put("djbh" ,djbh) ;
                map.put("fclb" ,fclb) ;
                map.put("gxbh" ,gxbh) ;
                map.put("scraps",list) ;

//                Map mapReq = new HashMap() ;
//                mapReq.put("params" ,map) ;
                showProcess();

//                RequestParams params = new RequestParams(Constant.BUSINESS_FCPDJ);
//                params.setAsJsonContent(true);//设置为json内容
                json = JSON.toJSONString(map);
//                params.setBodyContent(json);//设置正文内容
                new Thread(runnable).start();

                break;
            case R.id.scdj_can_btn:
                this.finish();
                break;

        }
    }


    List<ScdjYyItemEntity> data ;

    @Override
    public void onRetSuccess(String result) {
//        if(result.contains("code") && result.contains("10000")){
//            ToastUtil.show(ScdjFcpActivity.this,"保存成功", Toast.LENGTH_LONG);
//            Intent i = new Intent();
//            i.putExtra("totoalCount", total);
//            setResult(3, i);
//        }
        if(result.contains("yybh")) {
            String jsonArray = String.valueOf(JSON.parse(result));
            ScdjYyEntity entity = JSON.parseObject(jsonArray, ScdjYyEntity.class);
            data = entity.getData() ;
            LayoutInflater layoutInflater = getLayoutInflater() ;
            ll_dd.removeAllViews();
            for (int i=0;i<data.size();i++){
                ScdjYyItemEntity scdjYyItemEntity = data.get(i) ;
                View linearLayout = layoutInflater.inflate(R.layout.item_fpdjsl ,null) ;
                ll_dd.addView(linearLayout);
                TextView tv1 = linearLayout.findViewById(R.id.fcpdj_et11) ;
                EditText et2 = linearLayout.findViewById(R.id.fcpdj_et12) ;
                TextView tv3 = linearLayout.findViewById(R.id.fcpdj_et13) ;
                tv1.setText(scdjYyItemEntity.getYy());
                et2.setText(scdjYyItemEntity.getSl());
                tv3.setText(scdjYyItemEntity.getGxmc());

                etMap.put(scdjYyItemEntity.getYybh(),et2) ;
            }
        }

    }

    @Override
    public void onRetFailure(String msg) {

    }

}
