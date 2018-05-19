package nxt.com.ssllibrary.mytools;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class JSONTool {

	/**
	 * @return 将字符串转化成json
	 */
	public static JSONObject parseFromJson(String json) {
//		JSONObject object = new JSONObject();
		if (TextUtils.isEmpty(json))
			return null;
		JSONObject	object = JSON.parseObject(json, JSONObject.class);
//			object = new JSONObject(json);
			return object;
	}

	/**
	 * 
	 * @return 获取JSON对象中的JSON
	 */
	public static JSONObject getJsonObj(JSONObject obj, String key) {
		JSONObject jsonobj ;
		if (obj.containsKey(key)) {
				jsonobj = obj.getJSONObject(key);
			}else{
			jsonobj = new JSONObject();
		}
		return jsonobj;
	}

	/**
	 * 
	 * @return 获取JSON对象中的JSONArray
	 */
	public static JSONArray getJsonArry(JSONObject obj, String key) {
		JSONArray jsonobj;
		if (obj.containsKey(key)) {
				jsonobj = obj.getJSONArray(key);
		}else{
			jsonobj = new JSONArray();
		}
		return jsonobj;
	}

	/**
	 * @return 获取JSON对象中的String
	 */
	public static String getJsonString(JSONObject obj, String key) {
		String str = "";
		if (obj.containsKey(key)) {
				str = obj.getString(key);
				if (str.equals("null"))
					str = "";
		}
		return str;
	}

	/**
	 * 
	 * @return 获取JSON对象中的String
	 */
	public static Object getJsonObject(JSONObject obj, String key) {
		Object object ;
		if (obj.containsKey(key)) {
				object = obj.get(key);
		}else{
			object = new Object();
		}
		return object;
	}

	/**
	 * 
	 * @return 获取JSON对象中的boolean
	 */
	public static boolean getJsonBoolean(JSONObject obj, String key) {
		boolean flag = false;
		if (obj.containsKey(key)) {
				flag = obj.getBoolean(key);
		}
		return flag;
	}

	/**
	 * 
	 * @return 获取JSON对象中的Float
	 */
	public static double getJsonFloat(JSONObject obj, String key) {
		double f = 0;
		if (obj.containsKey(key)) {
				f = obj.getDouble(key);
		}
		return f;
	}

	/**
	 * 
	 * @return 获取JSON对象中的double
	 */
	public static double getJsonDouble(JSONObject obj, String key) {
		double dd = 0;
		if (obj.containsKey(key)) {
				dd = obj.getDouble(key);
		}
		return dd;
	}

	/**
	 * 
	 * @return 获取JSON对象中的int
	 */
	public static int getJsonInt(JSONObject obj, String key) {
		int str = 0;
		if (obj.containsKey(key)) {
				str = obj.getIntValue(key);
		}
		return str;
	}


	/**
	 *
	 * @return 获取JSON对象中的long
	 */
	public static long getJsonLong(JSONObject obj, String key) {
		long str = 0;
		if (obj.containsKey(key)) {
				str = obj.getLong(key);
		}
		return str;
	}
	
	/**
	 * @return 把实体类转化成字符串类型
	 */
	  public static String toBeanString(Object value)
	    {
//	        Gson gson = new Gson();
//	        String str = gson.toJson(value);
//	        return str;
	        
//	        Gson gson = new GsonBuilder()
//	        .setDateFormat("yyyy-MM-dd")
//	        .create();
//	        String str = gson.toJson(value);


//			SerializerFeature.PrettyFormat:格式化输出
//			SerializerFeature.WriteMapNullValue:是否输出值为null的字段,默认为false
//			SerializerFeature.DisableCircularReferenceDetect:消除循环引用
//			SerializerFeature.WriteNullStringAsEmpty:将为null的字段值显示为""
//			WriteNullListAsEmpty：List字段如果为null,输出为[],而非null
//			WriteNullNumberAsZero：数值字段如果为null,输出为0,而非null
//			WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
//			SkipTransientField：如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
//			SortField：按字段名称排序后输出。默认为false
//			WriteDateUseDateFormat：全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, WriteDateUseDateFormat);
//			BeanToArray：将对象转为array输出
//			QuoteFieldNames：输出key时是否使用双引号,默认为true
//			UseSingleQuotes：输出key时使用单引号而不是双引号,默认为false（经测试，这里的key是指所有的输出结果，而非key/value的key,而是key,和value都使用单引号或双引号输出）

			if(value == null){
				return "";
			}
			return JSON.toJSONStringWithDateFormat(value, "yyyy-MM-dd");
	    }

	/**
	 * 将位置信息转换为城市
	 *
	 * @param addr
	 *            位置
	 * @return 城市名称
	 */
	public static String formatCity(String addr) {
		String city = null;
		if (addr.contains("北京市") && addr.contains("区")) {
			city = addr.substring(addr.indexOf("市") + 1, addr.indexOf("区"));
//		} else if (addr.contains("县")) {
//			city = addr.substring(addr.indexOf("市") + 1, addr.indexOf("县"));
		} else {
//			int start = addr.indexOf("市");
//			int end = addr.lastIndexOf("市");
//			if (start == end) {
			if (addr.contains("省")) {
				city = addr.substring(addr.indexOf("省") + 1,
						addr.indexOf("市"));
			} else if (addr.contains("市")) {
				city = addr.substring(0, addr.indexOf("市"));
			}
//			} else {
//				city = addr.substring(addr.indexOf("市") + 1,
//						addr.lastIndexOf("市"));  //实现获取到县一级地市
//			}
		}
		return city;
	}





//	
//	  public static <T> T getJosonBean(String jsonString, Class<T> cls) {
//	        T t = null;
//	        try {
//	            Gson gson = new Gson();
//	            t = gson.fromJson(jsonString, cls);
//	        } catch (Exception e) {
//	        }
//	        return t;
//	    }
//
//	  public static <T, cls> List<T> getPersons(String jsonString, Class<T> cls) {
//	        List<T> list = new ArrayList<T>();
//	        try {
//	            Gson gson = new Gson();
//	            list = gson.fromJson(jsonString, new TypeToken<List<cls>>() {
//	            }.getType());
//	        } catch (Exception e) {
//	        }
//	        return list;
//	    }
//	  
//	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        try {
//            Gson gson = new Gson();
//            list = gson.fromJson(jsonString,
//                    new TypeToken<List<Map<String, Object>>>() {
//                    }.getType());
//        } catch (Exception e) {
//        }
//        return list;
//    }
}
