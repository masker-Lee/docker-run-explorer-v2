package com.nemtool.explorer.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: HttpUtils
 * @Description: 
 * @author: lu 
 * @date: 2018-12-17 17:22:52
 */
public class HttpUtils {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	private static int retryTime = 0;
	
    HttpConnectionPoolUtil connManager;
    
    /**
     * @Description: method get
     * @param url
     * @param param
     * @return 
     */
    public static String doGet(String url, Map<String, String> param) {
        // get httpclient from pool
        CloseableHttpClient httpclient = HttpConnectionPoolUtil.getHttpClient(url);
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
        	// create url
            URIBuilder builder = new URIBuilder(url);
            // add parameters
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) 
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
//        	logger.error(ExceptionsUtil.getExceptionAllinformation(e));
        } finally {
            try {
                if (response != null) 
                    response.close();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return resultString;
    }
    
    /**
     * @Description: method post
     * @param url
     * @param param
     * @return 
     */
    public static String doPost(String url, Map<String, String> param) {
        CloseableHttpClient httpClient = HttpConnectionPoolUtil.getHttpClient(url);
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            // add parameters
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // excute http post
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            if (resultString.isEmpty()) {
            	retryTime++;
                if(retryTime>3) {
             	   throw new RuntimeException("do post http exception");
                }
                resultString = doPost(url, param);
			}
        } catch (Exception e) {
//           logger.error(ExceptionsUtil.getExceptionAllinformation(e));
        } finally {
            try {
            	if (response != null) {
            		response.close();
                    retryTime = 0;
				}
            } catch (Exception e) {
//               logger.error(ExceptionsUtil.getExceptionAllinformation(e));
               retryTime++;
               if(retryTime>3) {
            	   throw new RuntimeException("do post http exception");
               }
               resultString = doPost(url, param);
            }
        }
        return resultString;
    }
    
    /**
     * @Description: method post (json)
     * @param url
     * @param json
     * @return 
     */
    public static String doPostJson(String url, String json) {
        CloseableHttpClient httpClient = HttpConnectionPoolUtil.getHttpClient(url);
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // excute http post
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            if (resultString.isEmpty()) {
            	retryTime++;
                if(retryTime>3) {
             	   throw new RuntimeException("do post json http exception");
                }
                resultString = doPostJson(url, json);
			}
        } catch (Exception e) {
//        	logger.error(ExceptionsUtil.getExceptionAllinformation(e));
        } finally {
            try {
            	if(response != null) {
            		response.close();
            		retryTime = 0;
            	}
            } catch (Exception e) {
//               logger.error(ExceptionsUtil.getExceptionAllinformation(e));
               retryTime++;
               if(retryTime>3) {
            	   throw new RuntimeException("do post json http exception");
               }
               resultString = doPostJson(url, json);
            }
        }
        return resultString;
    }
    
    
    public static String httpPostWithjson(String url, String json){
        String result = "";
        try {
        	URL urlhttp = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlhttp.openConnection();
            
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            conn.connect();
            
            OutputStreamWriter out = new OutputStreamWriter(  
            		conn.getOutputStream(), "UTF-8"); 
            
            
            out.append(json);
            out.flush();
            out.close();
            conn.getResponseCode();
            result = conn.getResponseMessage();

        } catch (Exception e) {
//            e.printStackTrace();

        } 
        return result;
    }
   
}