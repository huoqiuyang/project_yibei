package com.yibei.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


public class PageData extends HashMap implements Map{

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;

	public PageData(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}

	public PageData() {
		map = new HashMap();
	}

	//单个key-value构造
	public PageData(String key, Object value) {
		map = new HashMap();
		map.put(key,value);
	}
	//可变参数构造
	public PageData(PageData... pds) {
		map = new HashMap();
		for (PageData pd:pds){
			map.putAll(pd);
		}
	}
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(Object key) {
		return get(key).toString();
	}

	public Integer getInt(Object key) {
		return Integer.parseInt(get(key).toString());
	}

	public Long getLong(Object key) {
		return Long.parseLong(get(key).toString());
	}
	public float getFloat(Object key) {
		return Float.parseFloat(get(key).toString());
	}

	public double getDouble(Object key) {
		return Double.parseDouble(get(key).toString());
	}

	public BigDecimal getBigDecimal(Object key) {
		return new BigDecimal(get(key).toString());
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set keySet() {
		return map.keySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		map.putAll(t);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection values() {
		return map.values();
	}

	/**
	 * 把map的key转换成驼峰命名
	 * @param pd
	 * @return
	 */
	public static PageData toReplaceKeyLow(PageData pd) {
		PageData re_pd = new PageData();
		if (re_pd != null) {
			Iterator var2 = pd.entrySet().iterator();

			while (var2.hasNext()) {
				Entry<String, Object> entry = (Entry) var2.next();
				re_pd.put(underlineToCamel((String) entry.getKey()), pd.get(entry.getKey()));
			}

			pd.clear();
		}

		return re_pd;
	}

	public static final char UNDERLINE = '_';


	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(Character.toLowerCase(param.charAt(i)));
			}
		}
		return sb.toString();
	}
}
