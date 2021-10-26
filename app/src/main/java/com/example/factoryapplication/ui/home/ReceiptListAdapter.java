package com.example.factoryapplication.ui.home;

import java.util.List;

import android.content.Context;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CommonAdapter;
import com.example.factoryapplication.common.widget.ViewHolder;

/**
 * 工单统计 adapter
 * @author dyc
 *
 */
public class ReceiptListAdapter extends CommonAdapter<ReceiptCountItem> {

	public ReceiptListAdapter(Context context, List<ReceiptCountItem> datas,
			int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, ReceiptCountItem t) {
		holder.setText(R.id.name_tv,t.getDepartName()) ;
//		holder.setText(R.id.value_tv, t.getReceiptCount()) ;
	}
}
