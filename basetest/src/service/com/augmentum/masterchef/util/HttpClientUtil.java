package com.augmentum.masterchef.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {

	private static HttpClientUtil instance;

	private MultiThreadedHttpConnectionManager connectionManager;
	private HttpClient client;

	public static HttpClientUtil getInstance() {
		if (instance == null) {
			instance = new HttpClientUtil();
		}
		return instance;
	}

	public HttpClientUtil() {

		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.setMaxConnectionsPerHost(InitProps.getInstance()
				.getMaxConnectionsPerHost());
		connectionManager.setMaxTotalConnections(InitProps.getInstance()
				.getMaxTotalConnections());
		client = new HttpClient(connectionManager);

	}

	public String getMethod(String url, Map<String, String> headers)
			throws IOException {

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		setHeaders(method, headers);
		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		String responseString = null;

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			responseString = new String(responseBody, InitProps.getInstance()
					.getResponseCharset());
		} catch (IOException e) {
			throw new IOException("Failed request: GET " + url, e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return responseString;
	}

	public String postMethod(String url, String requestBody,
			Map<String, String> headers) throws IOException {

		// Create a method instance.
		PostMethod method = new PostMethod(url);

		setHeaders(method, headers);
		if (requestBody != null && requestBody.length() > 0) {
			method.setRequestBody(requestBody);
		}

		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		String responseString = null;

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			responseString = new String(responseBody, InitProps.getInstance()
					.getResponseCharset());

		} catch (IOException e) {
			throw new IOException("Failed request: POST " + url, e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return responseString;
	}

	public String putMethod(String url, String requestBody,
			Map<String, String> headers) throws IOException {

		// Create a method instance.
		PutMethod method = new PutMethod(url);

		setHeaders(method, headers);
		if (requestBody != null && requestBody.length() > 0) {
			method.setRequestBody(requestBody);
		}

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		String responseString = null;

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			responseString = new String(responseBody, InitProps.getInstance()
					.getResponseCharset());

		} catch (IOException e) {
			throw new IOException("Failed request: PUT " + url, e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return responseString;
	}

	public String deleteMethod(String url, Map<String, String> headers)
			throws IOException {

		// Create a method instance.
		DeleteMethod method = new DeleteMethod(url);

		setHeaders(method, headers);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		String responseString = null;

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			responseString = new String(responseBody, InitProps.getInstance()
					.getResponseCharset());

		} catch (HttpException e) {
			throw new IOException("Failed request: DELETE " + url, e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return responseString;
	}

	/**
	 * Put headers into the method
	 * 
	 * @param method
	 * @param headers
	 */
	private void setHeaders(HttpMethodBase method, Map<String, String> headers) {
		if (headers != null) {
			Set<Entry<String, String>> restfulHeadersSet = headers.entrySet();

			Iterator<Entry<String, String>> restfulHeadersIterator = restfulHeadersSet
					.iterator();

			while (restfulHeadersIterator.hasNext()) {
				Entry<String, String> restfulHeader = restfulHeadersIterator
						.next();

				method.setRequestHeader(restfulHeader.getKey(),
						restfulHeader.getValue());
			}
		}
	}

	/**
	 * Generate the query string based on the key/value in the Map. The output
	 * format is: key1=value1&key2=value2
	 * 
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generateQueryString(Map parameters)
			throws UnsupportedEncodingException {
		StringBuffer sb = null;
		if (parameters != null) {
			sb = new StringBuffer();
			boolean signForAnd = false;
			for (Object o : parameters.entrySet()) {
				Entry entry = (Entry) o;
				if (signForAnd) {
					sb.append("&");
					signForAnd = true;
				}
				sb.append(entry.getKey())
						.append("=")
						.append(URLEncoder.encode(entry.getValue().toString(),
								InitProps.getInstance().getResponseCharset()));
			}
		}
		return null;

	}

}
