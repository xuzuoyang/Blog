package com.dkd.common.httpClient;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * A singleton instance for http connection.
 * <p>It is used for a http connection and posting a http request returned by a response.<dr>
 */
public class ConnectionUtil {
	/**
	 * Singleton instance of ConnectionUtil.
	 */
	private static ConnectionUtil CONNECTIONUTIL = new ConnectionUtil();
	
	private HttpMethodRetryHandler retryhandler;
	/**
	 * the client of Http connection.
	 */
	private static HttpClient CLIENT;
	
	private ConnectionUtil()
	{
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		CLIENT = new HttpClient(connectionManager);
		// the timeout for waiting for establishing a connection
		CLIENT.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 30);
		// the timeout for waiting for data
		CLIENT.getHttpConnectionManager().getParams().setSoTimeout(1000 * 300);
	}
	
	/**
	 * return the singleton instance
	 * @return ConnectionUtil instance: CONNECTIONUTIL
	 */
	public final static ConnectionUtil getInstance()
	{
		return CONNECTIONUTIL;
	}
	
	/**
	 * do Https Post and get the response of the post
	 * @param connectionURL a url for the connection(not null)
	 * @param request the request(not null)
	 * @return null or response
	 * @throws Exception 
	 * @exception PreGWTransactionException throw when some exception occurred including UnknownHostException,NoHttpResponseException,InterruptedIOException and so on.
	 */
	public String doHTTPsPOST(String connectionURL, String request) throws Exception{
		String response = null;
		PostMethod post = null;
		
		try {
			// url = fully qualified url of the server to which you are posting
			post = new PostMethod(connectionURL); 
			HttpMethodParams params = new HttpMethodParams();
			params.setVersion(HttpVersion.HTTP_1_0);
			params.setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
			RequestEntity body = new StringRequestEntity(request,"application/x-www-form-urlencoded", "UTF-8");
			post.setRequestEntity(body);
			post.setHttp11(false);
		}catch(Exception e)
		{
			if(null != post)
				post.releaseConnection();
			throw new Exception("连接错误",e);
		}		
		
		try{
			CLIENT.executeMethod(post);
			response = post.getResponseBodyAsString();
		}catch (UnknownHostException e) {
			throw new Exception("域名无法解析",e);
		} catch (NoHttpResponseException e) {
			throw new Exception("HTTP没有响应",e);
		} catch (InterruptedIOException e) {
			throw new Exception("IO中断异常",e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("发送或读取数据异常",e);
		}
		finally{
			post.releaseConnection();
		}

		return response == null?"":response;
	}
	
	public String doHTTPsPOST(String connectionURL, Map<String, String> requestMap) throws Exception{
		String response = null;
		PostMethod post = null;
		try {
			// url = fully qualified url of the server to which you are posting
			post = new PostMethod(connectionURL); 
//			HttpMethodParams params = new HttpMethodParams();
//			params.setVersion(HttpVersion.HTTP_1_0);
//			params.setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
			
			if(requestMap!=null&&requestMap.size()>0){
				Iterator<String> it = requestMap.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					String value = requestMap.get(key);
					post.addParameter(key,value);
				}
			}
			
		}catch(Exception e)
		{
			if(null != post)
				post.releaseConnection();
			throw new Exception("连接错误",e);
		}		
		
		try{
		    post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			CLIENT.executeMethod(post);
			response = post.getResponseBodyAsString();
		}catch (UnknownHostException e) {
			throw new Exception("域名无法解析",e);
		} catch (NoHttpResponseException e) {
			throw new Exception("HTTP没有响应",e);
		} catch (InterruptedIOException e) {
			throw new Exception("IO中断异常",e);
		} catch (Exception e) {
			throw new Exception("发送或读取数据异常",e);
		}
		finally{
			post.releaseConnection();
		}

		return response == null?"":response;
	}
	
	
	public String doHTTPsGet(String uri) throws Exception {
		GetMethod getMethod = null;
		try {
			getMethod = new GetMethod(uri);
			HttpMethodParams params = new HttpMethodParams();
			params.setVersion(HttpVersion.HTTP_1_0);
			params.setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
			getMethod.setParams(params);
			CLIENT.executeMethod(getMethod);
			return getMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage(), ex);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}

	public String doHTTPsGet(String uri,String charSetName) throws Exception {
		GetMethod getMethod = null;
		try {
			getMethod = new GetMethod(uri);
			HttpMethodParams params = new HttpMethodParams();
			params.setVersion(HttpVersion.HTTP_1_0);
			params.setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
			getMethod.setParams(params);
			CLIENT.executeMethod(getMethod);
			return new String(getMethod.getResponseBody(), charSetName);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage(), ex);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}
}
