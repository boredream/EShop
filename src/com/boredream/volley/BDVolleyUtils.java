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
	 * ת��get��ʽ��url,��get������urlƴ��
	 * 
	 * @param url			ԭurl
	 * @param getParams		��Ҫƴ�ӵĲ���map����
	 * @return 				ƴװ��ɵ�url
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
	 * ת��get��ʽ��url,��get������urlƴ��
	 * 
	 * @param url			ԭurl
	 * @param bean			��Ҫƴ�ӵ�bean����,�ᰴ�ձ�����-����ֵ�ļ�ֵ�Է�װ�ɲ���
	 * @return 				ƴװ��ɵ�url
	 */
	public static String parseGetUrlWithParams(String url, Bean2Paramsable bean) {
		return parseGetUrlWithParams(url, bean2params(bean));
	}

	/**
	 * encode URL����������ַ�
	 * <p>Ĭ�ϱ���Ϊutf-8,������BDVolleyConfig.URL_ENCODE_CHARSET_NAME�޸�
	 * 
	 * @param url 	����ǰurl
	 * @return		encode���url
	 */
	public static String encodeUrl(String url) {
		// ��������
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
	 * ������תΪmap����,�������ʵ��Bean2Paramsable�ӿ�
	 * 
	 * <p>map�����б�����������еı���,��������Ϊkey,�����������ֵ��Ϊvalue
	 * 
	 * @param bean	��Ҫת���Ķ���
	 * @return		ת�����map����
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
     * ��ȡ��Ӧ����header�еı����ʽ
     * <p>Ĭ�ϱ���Ϊutf-8,������BDVolleyConfig.RESPONSE_CHARSET_NAME�޸�
     * 
     * @return ��Ӧ���ݵı����ʽ
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
