package com.example.factoryapplication.adapter;

import android.content.Context;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CommonAdapter;
import com.example.factoryapplication.common.widget.ViewHolder;
import com.example.factoryapplication.entity.JlcxEntity;
import com.example.factoryapplication.entity.JlcxGxItemEntity;
import com.example.factoryapplication.entity.departmentProgressList;

import java.util.List;

/**
 * 工单统计 adapter
 * @author dyc
 *
 */
public class JlcxGxListAdapter extends CommonAdapter<JlcxGxItemEntity> {

	public JlcxGxListAdapter(Context context, List<JlcxGxItemEntity> datas,
                             int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, JlcxGxItemEntity t) {
		holder.setText(R.id.jlcx_gzdh,"跟踪单号" + t.getGzdh()) ;
		holder.setText( R.id.jlcx_jjdh, "交接单号:" + t.getJjdh() ) ;
		holder.setText(R.id.jlcx_djrq, "登记日期:" + t.getDjrq()) ;
		holder.setText(R.id.jlcx_gwgx,"岗位工序:" + t.getGxmc()) ;

		holder.setText( R.id.jlcx_zpsl, "正品数量:" + t.getZpsl() ) ;
		holder.setText(R.id.jlcx_fpsl, "废品数量:" + t.getFpsl()) ;
		holder.setText(R.id.jlcx_cpsl,"次品数量:" + t.getCpsl()) ;

//		holder.setText(R.id.value_scr, t.getJhwcsj()) ;
	}
}
