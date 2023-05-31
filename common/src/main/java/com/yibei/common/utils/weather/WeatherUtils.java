package com.yibei.common.utils.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeatherUtils {

    public static Map<String, Integer> weatherEnumMap = weatherEnumInit();

    public static String weatherImgUrl = "http://www.weather.com.cn/m/i/weatherpic/29x20/d";

    public static String suffix = ".gif";


    /**
     * 通过城市名称获取该城市的天气信息
     *
     * @param cityName
     * @return
     */
    public  static String GetWeatherData(String cityName) {
        StringBuilder sb=new StringBuilder();;
        try {
            //cityname = URLEncoder.encode(cityName, "UTF-8");
            String weather_url = "http://wthrcdn.etouch.cn/weather_mini?city="+cityName;


            URL url = new URL(weather_url);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8"); // 设置读取流的编码格式，自定义编码
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null) {
                sb.append(line+" ");
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(sb.toString());
        return sb.toString();

    }


    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     * @param weatherInfobyJson
     * @return
     */
    public static WeatherInfo GetWeather(String weatherInfobyJson){
        JSONObject dataOfJson = JSONObject.fromObject(weatherInfobyJson);
        if(dataOfJson.getInt("status")!=1000) {
            return null;
        }

        //创建WeatherInfo对象，提取所需的天气信息
        WeatherInfo weatherInfo = new WeatherInfo();

        //从json数据中提取数据
        String data = dataOfJson.getString("data");

        dataOfJson = JSONObject.fromObject(data);
        weatherInfo.setCityname(dataOfJson.getString("city"));
        weatherInfo.setAirquality(dataOfJson.optString("aqi"));
        //获取预测的天气预报信息
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        //取得当天的
        JSONObject result=forecast.getJSONObject(0);

        weatherInfo.setDate(result.getString("date"));

        String high = result.getString("high").substring(2);
        String low  = result.getString("low").substring(2);

        weatherInfo.setTemperature(low+"~"+high);

        weatherInfo.setWeather(result.getString("type"));

        return weatherInfo;
    }

    public static Map<String,Integer> weatherEnumInit(){

        weatherEnumMap = new HashMap<>();

        weatherEnumMap.put("晴",0);
        weatherEnumMap.put("多云",1);
        weatherEnumMap.put("阴",2);
        weatherEnumMap.put("阵雨",3);
        weatherEnumMap.put("雷阵雨",4);
        weatherEnumMap.put("雷阵雨伴有冰雹",5);
        weatherEnumMap.put("雨夹雪",6);
        weatherEnumMap.put("小雨",7);
        weatherEnumMap.put("中雨",8);
        weatherEnumMap.put("大雨",9);
        weatherEnumMap.put("暴雨",10);
        weatherEnumMap.put("大暴雨",11);
        weatherEnumMap.put("特大暴雨",12);
        weatherEnumMap.put("阵雪",13);
        weatherEnumMap.put("小雪",14);
        weatherEnumMap.put("中雪",15);
        weatherEnumMap.put("大雪",16);
        weatherEnumMap.put("暴雪",17);
        weatherEnumMap.put("雾",18);
        weatherEnumMap.put("冻雨",19);
        weatherEnumMap.put("沙尘暴",20);
        weatherEnumMap.put("小到中雨",21);
        weatherEnumMap.put("中到大雨",22);
        weatherEnumMap.put("大到暴雨",23);
        weatherEnumMap.put("暴雨到大暴雨",24);
        weatherEnumMap.put("大暴雨到特大暴雨",25);
        weatherEnumMap.put("小到中雪",26);
        weatherEnumMap.put("中到大雪",27);
        weatherEnumMap.put("大到暴雪",28);
        weatherEnumMap.put("浮尘",29);
        weatherEnumMap.put("扬沙",30);
        weatherEnumMap.put("强沙尘暴",31);
        weatherEnumMap.put("霾",53);

        return weatherEnumMap;
    }

    /**
     * 获取天气对应的图标
     * @param weather
     * @return
     * */
    public static String getWeatherImgUrl(String weather){

        String imgUrl = null;

        Integer imgNum = weatherEnumMap.get(weather);
        if(imgNum!=null){
            imgUrl = weatherImgUrl + imgNum + suffix;
        }

        return imgUrl;
    }

}
