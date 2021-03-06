package com.upchina.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	
	private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        System.out.println("Url--->" + url);
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static Object sendRequest( String url,Map<String, Object> paraMap, String protocolNo)
			throws Exception {
		String param = JacksonUtil.beanToJson(paraMap);
		logger.debug("请求参数param："+param);
		String response = HttpRequest.sendPost(url,
				param, protocolNo);
		Object result = JacksonUtil.jsonToBean(response, Map.class);
		logger.debug("返回结果result："+result);
		return result;
	}
    
    /**
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param protocolNo
     * 			   请求头添加protocol-no字段
     * @return
     */
    public static String sendPost(String url, String param,String protocolNo) {
    	PrintWriter out = null;
    	BufferedReader in = null;
    	String result = "";
    	try {
    		URL realUrl = new URL(url);
    		// 打开和URL之间的连接
    		HttpURLConnection  conn = (HttpURLConnection) realUrl.openConnection();
    		// 设置通用的请求属性
    		conn.setRequestProperty("protocol-no", protocolNo);
    		conn.setRequestProperty("connection", "close");
//    		conn.setRequestProperty("accept", "*/*");
//    		conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    		// 发送POST请求必须设置如下两行
    		conn.setRequestMethod("POST");
    		
    		conn.setDoOutput(true);
    		conn.setDoInput(true);
    		// 获取URLConnection对象对应的输出流
    		out = new PrintWriter(conn.getOutputStream());
    		// 发送请求参数
    		out.print(param);
    		// flush输出流的缓冲
    		out.flush();
    		// 定义BufferedReader输入流来读取URL的响应
    		in = new BufferedReader(
    				new InputStreamReader(conn.getInputStream(),"GB2312"));
    		
    		String line;
    		while ((line = in.readLine()) != null) {
    			result += line;
    		}
    	} catch (Exception e) {
    		System.out.println("发送 POST 请求出现异常！"+e);
    		e.printStackTrace();
    	}
    	//使用finally块来关闭输出流、输入流
    	finally{
    		try{
    			if(out!=null){
    				out.close();
    			}
    			if(in!=null){
    				in.close();
    			}
    		}
    		catch(IOException ex){
    			ex.printStackTrace();
    		}
    	}
    	return result;
    }
    
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static void appendToFile(String fileName, String content){
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {

    }
}