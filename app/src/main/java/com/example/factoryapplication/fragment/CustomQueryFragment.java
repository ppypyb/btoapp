package com.example.factoryapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.BaseFragment;
import com.example.factoryapplication.Constant;
import com.example.factoryapplication.R;
import com.example.factoryapplication.adapter.DdxzListAdapter;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.entity.DdxzEntity;
import com.example.factoryapplication.entity.GxmxListEntity;


/**客户查询
 * Created by dyc on 2016/10/9.
 */
public class CustomQueryFragment extends BaseFragment implements AdapterView.OnItemSelectedListener{

    private Activity activity ;
    private View view ;


    private ListView ddxz_listview ;

    Button btnQuery ;
    EditText edit ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_query, null);

        initView();
//        sendByGet(HttpUrlUtil.WARNALARM_TICKET_URL);
//		initData();
        return view;
    }

    private void initView() {
        ddxz_listview = (ListView) view.findViewById(R.id.ddxz_listview);
        ddxz_listview.setOnItemSelectedListener(this);

        sendByGet(Constant.BUSINESS_DDXZ + "?rybh=" + SPUtils.get(activity,"yhbh","")  );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity ;
    }

    @Override
    public void onRetSuccess(String result) {
        String jsonArray = String.valueOf(JSON.parse(result));
        DdxzEntity entity = JSON.parseObject(jsonArray, DdxzEntity.class);
//        dismissProcess();
        if(entity != null){

            DdxzListAdapter adapter = new DdxzListAdapter(activity,
                    entity.getData().getOrderList(), R.layout.item_ddxz);
            ddxz_listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
