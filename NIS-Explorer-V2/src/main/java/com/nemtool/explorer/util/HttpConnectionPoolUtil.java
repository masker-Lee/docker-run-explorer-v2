package com.nemtool.explorer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
*
* @author Masker
* @date 2020.08.15
*/
public class HttpConnectionPoolUtil {
	 
    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(HttpConnectionPoolUtil.class);
 
    private static final int CONNECT_TIMEOUT = 10;
    private static final int SOCKET_TIMEOUT = 30;
    private static final int MAX_CONN = 50; 
    private static final int Max_PRE_ROUTE = 50;
    private static final int MAX_ROUTE = 50;
    private static CloseableHttpClient httpClient; 
    private static PoolingHttpClientConnectionManager manager; 
    private static ScheduledExecutorService monitorExecutor;
 
    private final static Object syncLock = new Object(); 
 
    /**
     * http config
     * @param httpRequestBase http request
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase){
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
 
        httpRequestBase.setConfig(requestConfig);
    }
 
    public static CloseableHttpClient getHttpClient(String url){
        String hostName = url.split("/")[2];
        int port = 80;
        if (hostName.contains(":")){
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }
        if (httpClient == null){
            synchronized (syncLock){
                if (httpClient == null){
                    httpClient = createHttpClient(hostName, port);
                    monitorExecutor = Executors.newScheduledThreadPool(1);
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            manager.closeExpiredConnections();
                            manager.closeIdleConnections(5, TimeUnit.MILLISECONDS);
                        }
                    }, 5, 5, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }
 
    /**
     *  httpclient contrution
     * @param host 
     * @param port 
     * @return
     */
    public static CloseableHttpClient createHttpClient(String host, int port){
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();
 
        manager = new PoolingHttpClientConnectionManager(registry);
        //config
        manager.setMaxTotal(MAX_CONN); 
        manager.setDefaultMaxPerRoute(Max_PRE_ROUTE); 
 
        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);
 
        // if error, retry
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 3){
//                    logger.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException){
//                    logger.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException){
//                    logger.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException){
//                    logger.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException){
//                    logger.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException){
//                    logger.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException){
//                    logger.error("SSLException");
                    return false;
                }
 
                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)){
                    //if request is not close request
                    return true;
                }
                return false;
            }
        };
 
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
        return client;
    }
 
    /**
     * post config
     * @param httpPost
     * @param params
     */
    private static void setPostParams(HttpPost httpPost, Map<String, String> params){
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for (String key: keys){
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
    }
 
    public static JSONObject post(String url, Map<String, String> params){
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        setPostParams(httpPost, params);
        CloseableHttpResponse response = null;
        InputStream in = null;
        JSONObject object = null;
        try {
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                in = entity.getContent();
                String result = IOUtils.toString(in, "utf-8");
                object = JSON.parseObject(result);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            try{
                if (in != null) in.close();
                if (response != null) response.close();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return object;
    }
 
    /**
     *  close pool
     */
    public static void closeConnectionPool(){
        try {
            httpClient.close();
            manager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}