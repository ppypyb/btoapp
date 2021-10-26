package com.example.factoryapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.DdjdListAdapter;
import com.example.factoryapplication.adapter.JlcxGxListAdapter;
import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.entity.DdjdRetEntity;
import com.example.factoryapplication.entity.JlcxEntity;
import com.example.factoryapplication.entity.JlcxGxEntity;
import com.example.factoryapplication.entity.JlcxGxListEntity;
import com.example.factoryapplication.entity.JlcxRetEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**记录查询
 * Created by dyc on 2016/10/12.
 */

public class JlcxActivity extends HttpActivity implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {

    private static final String TAG = JlcxActivity.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;

    private String mTextviewArray[];

    public static final int TAB_HANDING = 1 ;
    public static final int TAB_HAND_OVER = 2 ;
    public static int curSelTab = TAB_HANDING;

    int clickCur = 0 ;
    public String startTime ,endTime ;


    boolean queryFalgNRead = true ;

//    @ViewInject(R.id.jl_ddbh)
    private TextView ddbh ;
//    @ViewInject(R.id.jl_cpmc)
    private TextView cpmc ;
//    @ViewInject(R.id.jl_bbth)
    private TextView bbth ;
//    @ViewInject(R.id.jl_cz)
    private TextView cz ;
//    @ViewInject(R.id.jl_sc)
    private TextView scrq ;

//    @ViewInject(R.id.jl_scbm)
    private TextView scbm ;
//    @ViewInject(R.id.jl_scgr)
    private TextView scgr ;

//    @ViewInject(R.id.jl_listview)
    private ListView ddjd_listview;

//    @ViewInject(R.id.jlcx_ll_add)
    private LinearLayout jlcx_ll_add ;

    List<JlcxGxEntity> list ;
    Map<String ,JlcxGxEntity> map = new HashMap<String ,JlcxGxEntity>() ;

//    private Activity activity ;
//    private View view ;

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity ;
//    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jlcx);

        setTitle("记录查询");

        initView() ;
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_custom_query, null);
//        ddjd_listview = (ListView) view.findViewById(R.id.jl_listview);
//
//
//
//       ddbh = view.findViewById(R.id.jl_ddbh);;
//       cpmc  = view.findViewById(R.id.jl_cpmc);
//       bbth  = view.findViewById(R.id.jl_bbth);
//       cz  = view.findViewById(R.id.jl_cz);
//       scrq  = view.findViewById(R.id.jl_sc);
//
//       scbm  = view.findViewById(R.id.jl_scbm);
//
//       scgr  = view.findViewById(R.id.jl_scgr);
//       jlcx_ll_add = view.findViewById(R.id.jlcx_ll_add);;
//        return view ;
//    }
//


    /**
     * 取消选择
     */
    public void onCancelSel() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private void initView() {

//        ddjd_listview.setOnItemClickListener(this);
//        ddjd_listview.setOnItemSelectedListener(this);

//        layoutInflater = LayoutInflater.from(this);


//        showProcess();
        Map<String,String> map = new HashMap();

        sendByGet(Constant.BUSINESS_JLCX );
    }

    @Override
    public void onRetSuccess(String result) {
        if(result.contains("orderProcessList")){
        String jsonArray = String.valueOf(JSON.parse(result));
        JlcxRetEntity entity = JSON.parseObject(jsonArray, JlcxRetEntity.class);
//        dismissProcess();
        if(entity != null) {
            ddbh.setText("订单编号:" + entity.getData().getDdbh());
            cpmc.setText("产品名称:" + entity.getData().getCpbh());
            bbth.setText("版本图号:" + entity.getData().getCpth());
            cz.setText("材质:" + entity.getData().getCpcz());
            scrq.setText("生产日期:" + entity.getData().getJhrq() + " 排产数量:" + entity.getData().getJhrq());

            scbm.setText("生产部门:" + entity.getData().getBmmc());
            scgr.setText("生产工人:" + entity.getData().getRyxm());
            LogUtils.d("---" + entity.getData().getCpcz());

            list = entity.getData().getOrderProcessList();

            JlcxGxListAdapter jlcxGxListAdapter = new JlcxGxListAdapter(this,
                    entity.getData().getJobProcessList(), R.layout.item_jlcx);
            ddjd_listview.setAdapter(jlcxGxListAdapter);

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    map.put(list.get(i).getGxmc(), list.get(i));
                    Button btn = new Button(this);
                    btn.setText(list.get(i).getGxmc());
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            String gxbh = map.get(tv.getText()).getGxbh();

                            sendByGet(Constant.BUSINESS_GWGX + "&gxbh=" + gxbh);
                        }
                    });
                    jlcx_ll_add.addView(btn);
                }
            }
        }


//            DdjdListAdapter adapter = new DdjdListAdapter(this,
//                    entity.getData().getDepartmentProgressList(), R.layout.item_ddjd);
//            ddjd_listview.setAdapter(adapter);
        }else{
            String jsonArray = String.valueOf(JSON.parse(result));
            JlcxGxListEntity entity = JSON.parseObject(jsonArray, JlcxGxListEntity.class);

            JlcxGxListAdapter jlcxGxListAdapter = new JlcxGxListAdapter(this,
                    entity.getData(), R.layout.item_jlcx);
            ddjd_listview.setAdapter(jlcxGxListAdapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
