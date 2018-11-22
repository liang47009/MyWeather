package com.blessingsoftware.myweather.android.uitls;
//本工具类，首先利用JSONArray和JSONObject解析数据，随后组装为实体类对象
//再调用save()方法将数据存储至数据库中

import android.text.TextUtils;

import com.blessingsoftware.myweather.android.DB.City;
import com.blessingsoftware.myweather.android.DB.County;
import com.blessingsoftware.myweather.android.DB.Province;
import com.blessingsoftware.myweather.android.GSON.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GsUtil {
    //将返回的数据解析成Weather实体类
    public static Weather WeatherResponse(String response) {
        try {
//            {"HeWeather": [{"basic":{"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.00","city":"北京","id":"CN101010100","update":{"loc":"2018-11-22 09:45","utc":"2018-11-22 01:45"}},"update":{"loc":"2018-11-22 09:45","utc":"2018-11-22 01:45"},"status":"ok","now":{"cloud":"0","cond_code":"100","cond_txt":"晴","fl":"1","hum":"30","pcpn":"0.0","pres":"1029","tmp":"4","vis":"10","wind_deg":"139","wind_dir":"东南风","wind_sc":"1","wind_spd":"3","cond":{"code":"100","txt":"晴"}},"daily_forecast":[{"date":"2018-11-22","cond":{"txt_d":"晴"},"tmp":{"max":"8","min":"-3"}},{"date":"2018-11-23","cond":{"txt_d":"多云"},"tmp":{"max":"7","min":"0"}},{"date":"2018-11-24","cond":{"txt_d":"晴"},"tmp":{"max":"9","min":"-3"}}],"aqi":{"city":{"aqi":"57","pm25":"20","qlty":"良"}},"suggestion":{"comf":{"type":"comf","brf":"较舒适","txt":"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。"},"sport":{"type":"sport","brf":"较适宜","txt":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。"},"cw":{"type":"cw","brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}}}]}
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //省级数据的解析
    public static boolean ProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //市级数据的解析
    public static boolean CityResponse(String response, int provinceID) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceID(provinceID);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //县级数据的解析
    public static boolean CountyResponse(String response, int cityID) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherID(countyObject.getString("weather_id"));
                    county.setCityID(cityID);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
