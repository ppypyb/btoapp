package com.example.factoryapplication.adapter;

import android.content.Context;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CommonAdapter;
import com.example.factoryapplication.common.widget.ViewHolder;
import com.example.factoryapplication.entity.departmentProgressList;
import com.example.factoryapplication.ui.home.ReceiptCountItem;

import java.util.List;

/**
 * 工单统计 adapter
 * @author dyc
 *
 */
public class DdjdListAdapter extends CommonAdapter<departmentProgressList> {

	public DdjdListAdapter(Context context, List<departmentProgressList> datas,
                           int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, departmentProgressList t) {
		holder.setText(R.id.name_tv,t.getBmmc()) ;
		holder.setText( R.id.value_scr, "生产交期:" + t.getJhwcsj()) ;
		holder.setText( R.id.value_wcr, "完成日期:"+ t.getScwcsj()) ;
		holder.setText(R.id.value_jjr, "交接数量:" + t.getScjjsl() + "  完成:"+ t.getScwcsl()) ;
		holder.setText(R.id.value_wcs,"废品数量:" + t.getScfpsl() + "  剩余数量:"+ t.getScsysl()) ;

//		holder.setText(R.id.value_scr, t.getJhwcsj()) ;
	}
}
