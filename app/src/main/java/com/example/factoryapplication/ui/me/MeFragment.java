package com.example.factoryapplication.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.factoryapplication.LoginActivity;
import com.example.factoryapplication.R;
import com.example.factoryapplication.RegActivity;
import com.example.factoryapplication.common.utils.SPUtils;

public class MeFragment extends Fragment implements View.OnClickListener {

    private MeViewModel notificationsViewModel;

    TextView textView1,textView2,textView3,textView4;

    Button server_btn ,zhuxiao_btn ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);

//        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        textView1 = root.findViewById(R.id.me_mc);
        textView2 = root.findViewById(R.id.me_gh);
        textView3 = root.findViewById(R.id.me_bm);
        textView4 = root.findViewById(R.id.me_zw);

        server_btn = root.findViewById(R.id.server_btn);
        zhuxiao_btn= root.findViewById(R.id.zhuxiao_btn);

        String xm = SPUtils.get(getActivity(),"yhxm","").toString() ;
        String gh = SPUtils.get(getActivity(),"yhbh","").toString() ;
        String bm = SPUtils.get(getActivity(),"bmbh","").toString() ;
        String zx = SPUtils.get(getActivity(),"bmzw","").toString() ;

        textView1.setText("姓名:" + xm);
        textView2.setText("工号:" + gh);
        textView3.setText("部门:" + bm);
        textView4.setText("职位:" + zx);

        server_btn.setOnClickListener(this);
        zhuxiao_btn.setOnClickListener(this);

//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId() ;
        switch (id){
            case R.id.server_btn :
                Intent intent = new Intent(getActivity(), RegActivity.class) ;
                intent.putExtra("RESET_SERVE" ,"1") ;
                startActivity(intent);
//                getActivity().finish();
                break;
            case R.id.zhuxiao_btn:
                SPUtils.remove(getActivity(),LoginActivity.USER_NAME);
                SPUtils.remove(getActivity(),LoginActivity.USER_PWD);
                Intent intent1 = new Intent(getActivity(), LoginActivity.class) ;
                startActivity(intent1);
                getActivity().finish();
                break;
        }
    }
}