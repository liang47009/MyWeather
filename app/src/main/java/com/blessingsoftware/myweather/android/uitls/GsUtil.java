package com.blessingsoftware.myweather.android.uitls;
//本工具类，首先利用JSONArray和JSONObject解析数据，随后组装为实体类对象
//再调用save()方法将数据存储至数据库中
import android.text.TextUtils;

import com.blessingsoftware.myweather.android.DB.City;
import com.blessingsoftware.myweather.android.DB.County;
import com.blessingsoftware.myweather.android.DB.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GsUtil {
    //省级数据的解析
    public static boolean ProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces=new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++){
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //市级数据的解析
    public static boolean CityResponse(String response,int provinceID){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCities=new JSONArray(response);
                for(int i=0;i<allCities.length();i++){
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceID(provinceID);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //县级数据的解析
    public static boolean CountyResponse(String response,int cityID){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounties=new JSONArray(response);
                for(int i=0;i<allCounties.length();i++){
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherID(countyObject.getString("weatherID"));
                    county.setCityID(cityID);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
