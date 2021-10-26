package com.example.factoryapplication.adapter;

import android.content.Context;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CommonAdapter;
import com.example.factoryapplication.common.widget.ViewHolder;
import com.example.factoryapplication.entity.DdxzListItemEntity;
import com.example.factoryapplication.entity.departmentProgressList;

import java.util.List;

/**
 * 工单统计 adapter
 * @author dyc
 *
 */
public class DdxzListAdapter extends CommonAdapter<DdxzListItemEntity> {

	public DdxzListAdapter(Context context, List<DdxzListItemEntity> datas,
                           int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, DdxzListItemEntity t) {
		holder.setText(R.id.ddxz_gzdh,"订单号:" + t.getDdbh()) ;
		holder.setText(R.id.ddxz_jjdh,"产品名称::" + t.getCpmc()) ;
		holder.setText(R.id.ddxz_djrq,"版本图号:" + t.getCpth()) ;
		holder.setText(R.id.ddxz_gwgx,"材质:" + t.getCpcz()) ;
		holder.setText( R.id.ddxz_zpsl, "生产交期:" + t.getJhrq() );
		holder.setText( R.id.ddxz_fpsl, "完成日期:"+ t.getZdrq()) ;
		holder.setText(R.id.ddxz_pcsl,"排产数量:" + t.getPcsl() ) ;
		holder.setText(R.id.ddxz_wcsl,"  完成数量:"+ t.getWcsl()) ;

		holder.setImgResource(R.id.ddxz_cptu, t.getCptp()) ;

//        holder.
//		holder.setText(R.id.value_scr, t.getJhwcsj()) ;
	}
}
