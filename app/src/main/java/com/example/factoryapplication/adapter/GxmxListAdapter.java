package com.example.factoryapplication.adapter;

import android.content.Context;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CommonAdapter;
import com.example.factoryapplication.common.widget.ViewHolder;
import com.example.factoryapplication.entity.GxmxItemEntity;
import com.example.factoryapplication.entity.departmentProgressList;

import java.util.List;

/**
 * 工单统计 adapter
 * @author dyc
 *
 */
public class GxmxListAdapter extends CommonAdapter<GxmxItemEntity> {

	public GxmxListAdapter(Context context, List<GxmxItemEntity> datas,
                           int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, GxmxItemEntity t) {
		holder.setText(R.id.value_gx1,t.getGxmc()) ;
		holder.setText(R.id.value_gx2,t.getWcsl()) ;
		holder.setText(R.id.value_gx3,t.getFpsl()) ;
		holder.setText(R.id.value_gx4,t.getCpsl()) ;
		holder.setText(R.id.value_gx5,t.getWcjd()) ;

	}
}
