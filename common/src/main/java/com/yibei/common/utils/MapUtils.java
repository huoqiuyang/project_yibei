package com.yibei.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

public class MapUtils {
    /**
     * List<Map<String, Object>>转List<T>
     */
    public static <T> List<T> mapListToEntity(List<Map<String, Object>> list, Class<T> clazz){
        try {
            if (list == null || list.size()==0) {
                return new ArrayList<>();
            }
            List<T> tList = new ArrayList<T>();
            // 获取类中声明的所有字段
            Field[] fields = clazz.getDeclaredFields();

            T t;
            for (Map<String, Object> map : list) {
                // 每次都先初始化一遍,然后再设置值
                t = clazz.newInstance();
                for (Field field : fields) {
                    // 把序列化的字段去除掉
                    if (!"serialVersionUID".equals(field.getName())) {
                        // 由于Field都是私有属性，所有需要允许修改
                        field.setAccessible(true);

                        // 设置值, 类型要和vo中的属性名称对应好,不然会报类型转换错误
                        field.set(t, map.get(field.getName()));
                    }
                }
                tList.add(t); // 把转换好的数据添加到集合中
            }
            return tList;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static <T> T mapToEntity(Map map,Class<T> type) {
        T t = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            t = type.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    descriptor.getWriteMethod().invoke(t, value);
                }
            }
        } catch (Exception e) {
            System.out.println("map转实体类出现异常");
        }
        return t;
    }

    /**
     * 将Map中的key由下划线转换为驼峰
     *
     * @param map
     * @return
     */
    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = toFormatCol(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 将List中map的key值命名方式格式化为驼峰
     *
     * @param
     * @return
     */
    public static List<Map<String, Object>> formatHumpName(List<Map<String, Object>> list) {
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> o : list) {
            newList.add(formatHumpName(o));
        }
        return newList;
    }


   /* //一个对象是不是时间 测试
    private  static  String[] parsePatterns = {"yyyy-MM-dd","yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};
    public static Date getDate(Object o) {
        try {
           return DateUtils.parseDate(o.toString(), parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Object o = "2020/1/1";
        final Date date = getDate(o);
        System.out.println(date);
    }
*/

    /**
     *
     * @description: 实体类转Map去空值
     * @author: Jeff
     * @date: 2019年10月29日
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                if(o != null){
                    map.put(field.getName(), o);
                    field.setAccessible(flag);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     *
     * @description: 实体类转Map
     * @author: Jeff
     * @date: 2019年10月29日
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMapAll(Object object) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
