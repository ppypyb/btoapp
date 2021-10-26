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
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.DdjdListAdapter;
import com.example.factoryapplication.entity.DdjdRetEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**进度查询
 * Created by dyc on 2016/10/12.
 */
public class DdjdActivityF_old extends BaseFragment implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {

    private static final String TAG = DdjdActivityF_old.class.getName();

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

    @ViewInject(R.id.ddbh)
    private TextView ddbh ;
    @ViewInject(R.id.cpbh)
    private TextView cpbh ;
    @ViewInject(R.id.bbth)
    private TextView bbth ;
    @ViewInject(R.id.cz)
    private TextView cz ;

    @ViewInject(R.id.ddjd_listview)
    private ListView ddjd_listview;

    private Activity activity ;
    private View view ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ddcx);
        view = inflater.inflate(R.layout.activity_ddcx, container, false);

//        setTitle("订单进度");
        ddjd_listview = (ListView) view.findViewById(R.id.ddjd_listview);

        ddbh = view.findViewById(R.id.ddbh);
        cpbh = view.findViewById(R.id.cpbh);;
        bbth = view.findViewById(R.id.bbth);;
        cz  = view.findViewById(R.id.cz);;

        initView() ;
        return view;
//        bottomView.setSelIndex(4);
    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        int id = v.getId() ;
//        switch (id){
//            case R.id.btn_eoms_query:
//                startTime = etBegin.getText().toString()  + "00分00" ;
////                endTime = etEnd.getText().toString()  + "00分00";
//                break;
//
//        }
//    }

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
        ddjd_listview.setOnItemClickListener(this);
        ddjd_listview.setOnItemSelectedListener(this);

//        layoutInflater = LayoutInflater.from(this);
//
//
//        showProcess();
        Map<String,String> map = new HashMap();

        sendByGet(Constant.BUSINESS_DDJDCX );
    }

    @Override
    public void onRetSuccess(String result) {
        String jsonArray = String.valueOf(JSON.parse(result));
        DdjdRetEntity entity = JSON.parseObject(jsonArray, DdjdRetEntity.class);
//        dismissProcess();
        if(entity != null){
            ddbh.setText("订单编号:" + entity.getData().getDdbh());
            cpbh.setText("产品编号:" + entity.getData().getCpbh());
            bbth.setText("版本图号:" + entity.getData().getCpth());
            cz.setText("材质:" + entity.getData().getCpcz());
            LogUtils.d("---" + entity.getData().getCpcz());

            DdjdListAdapter adapter = new DdjdListAdapter(activity,
                    entity.getData().getDepartmentProgressList(), R.layout.item_ddjd);
            ddjd_listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
