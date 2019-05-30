package com.bfd.api.zuul.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpRequestUtil {

    private static final Logger LOG = Logger.getLogger(HttpRequestUtil.class);
    private static String AUTHORITY_WEB_URL;

    static {
        AUTHORITY_WEB_URL = "";
    }
    
    /**
     * 向指定URL发送POST请求,参数为json字符串
     */
    @SuppressWarnings("deprecation")
    public static String doPost(String uri, String jsonObj) {
        String resStr = null;
        HttpClient htpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(AUTHORITY_WEB_URL + uri);
        postMethod.addRequestHeader("Content-Type", "application/json");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        postMethod.setRequestBody(jsonObj);
        try {
            int statusCode = htpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = postMethod.getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        LOG.info("The page was redirected to :" + location);
                    } else {
                        LOG.info("Location field value is null");
                    }
                } else {
                    LOG.error("Post Method failed: " + postMethod.getStatusLine());
                }
                return resStr;
            }
            byte[] responseBody = postMethod.getResponseBody();
            resStr = new String(responseBody, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return resStr;
    }

    /**
     * 用于处理参数类型为json的post请求
     *
     * @param url
     * @param jsonObj
     * @return
     */
    public static String doPostByJSON(String url, String jsonObj) {
        String resStr = null;
        HttpClient htpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        postMethod.setRequestBody(jsonObj);
        try {
            int statusCode = htpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = postMethod.getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        LOG.info("The page was redirected to :" + location);
                    } else {
                        LOG.info("Location field value is null");
                    }
                } else {
                    LOG.error("Post Method failed: " + postMethod.getStatusLine());
                }
                return resStr;
            }
            byte[] responseBody = postMethod.getResponseBody();
            resStr = new String(responseBody, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return resStr;
    }

    /**
     * 向指定URL发送PUT请求,参数为json字符串
     */
    public static String doPost(String url, Map<String, String> params) {
        String resStr = null;
        HttpClient htpClient = new HttpClient();

        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        try {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }

            int statusCode = htpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = postMethod.getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        LOG.info("The page was redirected to :" + location);
                    } else {
                        LOG.info("Location field value is null");
                    }
                } else {
                    LOG.error("Post Method failed: " + postMethod.getStatusLine());
                }
                return resStr;
            }
           InputStream inputStream = postMethod.getResponseBodyAsStream();  
    	   BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));  
    	   StringBuffer result = new StringBuffer(); 
    	   String str= "";  
    	   while((str = br.readLine()) != null){  
    		  result.append(str );  
    	   }  
    	   String charset = postMethod.getResponseCharSet();
           resStr = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return resStr;
    }

    /**
     * 向指定URL发送PUT请求,参数为json字符串
     */
    public static String doGet(String url) {
        String resStr = null;
        HttpClient htpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader("Content-Type", "application/json");
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        try {

            int statusCode = htpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = getMethod.getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        LOG.info("The page was redirected to :" + location);
                    } else {
                        LOG.info("Location field value is null");
                    }
                } else {
                    LOG.error("Post Method failed: " + getMethod.getStatusLine());
                }
                return resStr;
            }
            byte[] responseBody = getMethod.getResponseBody();
            resStr = new String(responseBody, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return resStr;
    }

    /**
     * 向指定URL发送POST请求
     */
    public static String doPostText(String url, Map<String, String> params, String charset) {
        String resStr = null;
        HttpClient htpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        try {
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }
            
            int statusCode = htpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = postMethod.getResponseHeader("location");
                    String location = null;
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        LOG.info("The page was redirected to :" + location);
                    } else {
                        LOG.info("Location field value is null");
                    }
                } else {
                    LOG.error("Post Method failed: " + postMethod.getStatusLine());
                }
                return resStr;
            }
           InputStream inputStream = postMethod.getResponseBodyAsStream();  
     	   BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
     	   StringBuffer result = new StringBuffer(); 
     	   String str= "";  
     	   while((str = br.readLine()) != null){  
     		  result.append(str);  
     	   }  
           
           resStr = new String(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return resStr;
    }

    public static JSONArray getJSONArrayData(String url, Map<String, String> params) {
        String result = doPostText(url, params, "UTF-8");
        if(org.apache.commons.lang.StringUtils.isBlank(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        if("000000".equals(jsonObject.getString("code"))){
            return jsonObject.getJSONArray("data");
        }
        return null;
    }
    
    public static JSONObject getJSONObjectData(String url, Map<String, String> params) {
        String result = doPostText(url, params, "UTF-8");
        if(org.apache.commons.lang.StringUtils.isBlank(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        if("000000".equals(jsonObject.getString("code"))){
            return jsonObject.getJSONObject("data");
        }
        return null;
    }
    
    public static JSONObject getJSONObjectData(String url) {
    	String result = doGet(url);
    	if(org.apache.commons.lang.StringUtils.isBlank(result)){
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        if("000000".equals(jsonObject.getString("code"))){
            return jsonObject.getJSONObject("data");
        }
		return null;
    }
   
}