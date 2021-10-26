package com.example.factoryapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.DdjdListAdapter;
import com.example.factoryapplication.adapter.GxmxListAdapter;
import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.entity.DdjdRetEntity;
import com.example.factoryapplication.entity.GxmxListEntity;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**工序明细
 * Created by dyc on 2016/10/12.
 */
public class GxmxActivity extends HttpActivity {

    private static final String TAG = GxmxActivity.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;

    private String mTextviewArray[];

    public static final int TAB_HANDING = 1 ;
    public static final int TAB_HAND_OVER = 2 ;
    public static int curSelTab = TAB_HANDING;

    int clickCur = 0 ;
    public String startTime ,endTime ;

    @ViewInject(R.id.et_eoms_begion)
    private TextView etBegin ;

    @ViewInject(R.id.btn_eoms_query)
    private Button btnQuery ;

    private TextView tview[];

    boolean queryFalgNRead = true ;



    @ViewInject(R.id.gxmx_listview)
    private ListView gxmx_listview;
    String ddbh = null , bmbh =null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gxmx);

        Intent intent = getIntent() ;
        ddbh = intent.getStringExtra("ddbh") ;
        bmbh = intent.getStringExtra("bmbh") ;
        LogUtils.e("ddbh" + ddbh);

        setTitle("订单进度");

        initView() ;
//        bottomView.setSelIndex(4);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        switch (id){
            case R.id.btn_eoms_query:
                startTime = etBegin.getText().toString()  + "00分00" ;
//                endTime = etEnd.getText().toString()  + "00分00";
                break;

        }
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
//        etBegin.setOnClickListener(this);
//
//        btnQuery.setOnClickListener(this);


        layoutInflater = LayoutInflater.from(this);


        showProcess();
        Map<String,String> map = new HashMap();
//        String bmbh = SPUtils.get(this,"bmbh","011").toString() ;
//        bmbh = "011" ;
        sendByGet(Constant.BUSINESS_GXMX + "?bmbh=" +bmbh +"&ddbh="+ddbh  );
    }

    @Override
    public void onRetSuccess(String result) {
        String jsonArray = String.valueOf(JSON.parse(result));
        GxmxListEntity entity = JSON.parseObject(jsonArray, GxmxListEntity.class);
        dismissProcess();
        if(entity != null){

            GxmxListAdapter adapter = new GxmxListAdapter(this,
                    entity.getData(), R.layout.item_gxmx);
            gxmx_listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }
}
