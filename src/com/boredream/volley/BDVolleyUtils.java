package com.boredream.volley;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.protocol.HTTP;

public class BDVolleyUtils {

	/**
	 * 转换get方式的url,将get参数与url拼接
	 * 
	 * @param url			原url
	 * @param getParams		需要拼接的参数map集合
	 * @return 				拼装完成的url
	 */
	public static String parseGetUrlWithParams(String url, Map<String, Object> getParams) {
		StringBuilder newUrl = new StringBuilder(url);
		if(getParams != null && getParams.size() > 0) {
			newUrl.append("?");
			for(Entry<String, Object> entry : getParams.entrySet()) {
				try {
					newUrl.append(entry.getKey() + "=" 
							+ entry.getValue().toString() + "&");
				} catch (Exception e) { }
			}
			newUrl.substring(0, newUrl.length()-2);
		}
		return newUrl.toString();
	}
	
	/**
	 * 转换get方式的url,将get参数与url拼接
	 * 
	 * @param url			原url
	 * @param bean			需要拼接的bean对象,会按照变量名-变量值的键值对封装成参数
	 * @return 				拼装完成的url
	 */
	public static String parseGetUrlWithParams(String url, Bean2Paramsable bean) {
		return parseGetUrlWithParams(url, bean2params(bean));
	}

	/**
	 * encode URL里面的中文字符
	 * <p>默认编码为utf-8,可以在BDVolleyConfig.URL_ENCODE_CHARSET_NAME修改
	 * 
	 * @param url 	处理前url
	 * @return		encode后的url
	 */
	public static String encodeUrl(String url) {
		// 中文正则
		String regex = "[\\u4e00-\\u9fa5]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		while (matcher.find()) {
			String cString = matcher.group();
			try {
				url = url.replaceFirst(cString, URLEncoder.encode(
						cString, BDVolleyConfig.URL_ENCODE_CHARSET_NAME));
			} catch (UnsupportedEncodingException e) { }
		}
		return url;
	}

	/**
	 * 将对象转为map数组,对象必须实现Bean2Paramsable接口
	 * 
	 * <p>map数组中保存对象类所有的变量,变量命作为key,对象变量具体值作为value
	 * 
	 * @param bean	需要转换的对象
	 * @return		转换后的map数组
	 */
	public static Map<String, Object> bean2params(Bean2Paramsable bean) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Field field : bean.getClass().getDeclaredFields()) {
			try {
				params.put(field.getName(), field.get(bean));
			} catch (Exception e) { }
		}
		return params;
	}
	
	/**
     * 获取响应数据header中的编码格式
     * <p>默认编码为utf-8,可以在BDVolleyConfig.RESPONSE_CHARSET_NAME修改
     * 
     * @return 响应数据的编码格式
     */
	public static String getCharsetFromHeaders(Map<String, String> headers) {
        String contentType = headers.get(HTTP.CONTENT_TYPE);
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return BDVolleyConfig.RESPONSE_CHARSET_NAME;
    }

	public interface Bean2Paramsable {
		/* empty */
	}
}
