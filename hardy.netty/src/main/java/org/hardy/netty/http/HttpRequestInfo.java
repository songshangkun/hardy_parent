package org.hardy.netty.http;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpMethod;

/**
 * 将FullHttpRequest解析后保存信息的类。这个类包含了参数,无参数的URL,和访问方法
 * @author song
 *
 */
public class HttpRequestInfo {
    /**
     * request传递的参数对应,因为通过url传递的参数都是以String形式出现的所以这里保存为String-String
     */
	private Map<String, Object> parmMap = new HashMap<>();
	/**
	 * 去掉后面参数的 URL
	 */
	private String uri;
	/**
	 * request访问方法 
	 */
	private HttpMethod method;

	public Map<String, Object> getParmMap() {
		return parmMap;
	}

	public void setParmMap(Map<String, Object> parmMap) {
		this.parmMap = parmMap;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "HttpRequestInfo [parmMap=" + parmMap + ", uri=" + uri + ", method=" + method + "]";
	}
	 
	 
}
