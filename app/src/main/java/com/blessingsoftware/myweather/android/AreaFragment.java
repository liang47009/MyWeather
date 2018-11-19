package com.blessingsoftware.myweather.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blessingsoftware.myweather.android.DB.City;
import com.blessingsoftware.myweather.android.DB.County;
import com.blessingsoftware.myweather.android.DB.Province;

import java.util.ArrayList;
import java.util.List;

public class AreaFragment extends Fragment {
    //定义三层级别，越小优先级越高
    public static final int level_province=0;
    public static final int level_city=1;
    public static final int level_county=2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String>dataList=new ArrayList<>();
    //省市县列表和被选中的
    private List<Province> provinceList;
    private List<City>cityList;
    private List<County>countyList;
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    //判断当前选中选项的级别
    private int currentlevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText=(TextView)view.findViewById(R.id.title_text);
        backButton=(Button)view.findViewById(R.id.back_button);
        listView=(ListView)view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentlevel==level_province){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }else if(currentlevel==level_city){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }

    
}
