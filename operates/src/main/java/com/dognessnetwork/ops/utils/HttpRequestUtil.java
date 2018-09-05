package com.dognessnetwork.ops.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.dognessnetwork.ops.common.Constant;
public class HttpRequestUtil {
	   
		public static String sendPost(String testurl){  
	    	 StringBuffer json=new StringBuffer(); 
	        try {   
	            URL url = new URL(testurl);  
	            //得到connection对象。  
	            System.out.println("testurl:"+testurl);
	            System.getProperties().put("http.proxySet", Constant.HTTP_PROXY_SET); 
	            System.getProperties().put("http.proxyHost", Constant.HTTP_PROXY_HOST); 
	            System.getProperties().put("http.proxyPort", Constant.HTTP_PROXY_PORT);      
	           // System.getProperties().put("http.proxyPassword", "123456"); 
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	            //设置请求方式  
	            connection.setRequestMethod("POST"); 
	            System.out.println("connection:"+connection);
	            //连接  
	            connection.connect();  
	            //得到响应码  
	            int responseCode = connection.getResponseCode();  
	            System.out.println("responseCode:");
	            if(responseCode == HttpURLConnection.HTTP_OK){  
	                //得到响应流  
	                InputStream inputStream = connection.getInputStream();  
	                System.out.println(1111);
	                //获取响应  
	                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	                System.out.println(2222);
	                String line;
	                while ((line = reader.readLine()) != null){  
	                    json.append(line); 
	                    System.out.println(line);
	                }  
	                System.out.println(3333);
	                reader.close();  
	                //该干的都干完了,记得把连接断了  
	                connection.disconnect();  
	                
	     
	            }  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }
	        return json.toString();
	    }   
		
		 public static String post(String requestUrl, String accessToken, String params)
		            throws Exception {
		        String contentType = "application/x-www-form-urlencoded";
		        return HttpRequestUtil.post(requestUrl, accessToken, contentType, params);
		    }

		    public static String post(String requestUrl, String accessToken, String contentType, String params)
		            throws Exception {
		        String encoding = "UTF-8";
		        if (requestUrl.contains("nlp")) {
		            encoding = "GBK";
		        }
		        return HttpRequestUtil.post(requestUrl, accessToken, contentType, params, encoding);
		    }

		    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
		            throws Exception {
		        String url = requestUrl + "?access_token=" + accessToken;
		        return HttpRequestUtil.postGeneralUrl(url, contentType, params, encoding);
		    }

		    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
		            throws Exception {
		        URL url = new URL(generalUrl);
		        // 打开和URL之间的连接
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("POST");
		        // 设置通用的请求属性
		        connection.setRequestProperty("Content-Type", contentType);
		        connection.setRequestProperty("Connection", "Keep-Alive");
		        connection.setUseCaches(false);
		        connection.setDoOutput(true);
		        connection.setDoInput(true);

		        // 得到请求的输出流对象
		        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		        out.write(params.getBytes(encoding));
		        out.flush();
		        out.close();

		        // 建立实际的连接
		        connection.connect();
		        // 获取所有响应头字段
		        Map<String, List<String>> headers = connection.getHeaderFields();
		        // 遍历所有的响应头字段
		        for (String key : headers.keySet()) {
		            System.err.println(key + "--->" + headers.get(key));
		        }
		        // 定义 BufferedReader输入流来读取URL的响应
		        BufferedReader in = null;
		        in = new BufferedReader(
		                new InputStreamReader(connection.getInputStream(), encoding));
		        String result = "";
		        String getLine;
		        while ((getLine = in.readLine()) != null) {
		            result += getLine;
		        }
		        in.close();
		        System.err.println("result:" + result);
		        return result;
		    }
		    
		    public static String load(String url,String param) throws Exception
		    {
		    	String response = "";
		    	
		        URL restURL = new URL(url);
		        System.getProperties().put("http.proxySet", Constant.HTTP_PROXY_SET); 
	            System.getProperties().put("http.proxyHost", Constant.HTTP_PROXY_HOST); 
	            System.getProperties().put("http.proxyPort", Constant.HTTP_PROXY_PORT);   
		        /*
		         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
		         */
		        System.out.println(url);
		        System.out.println(param);
		        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		        
		        //请求方式
		        conn.setRequestMethod("POST");
		        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
		        conn.setDoOutput(true);
		        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
		        conn.setAllowUserInteraction(false);
		        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

				out.writeBytes(param);
				out.flush();
				out.close();
				System.out.println("conn:"+conn.getResponseCode());
				InputStream in = null;
				if (200!=conn.getResponseCode()){
					in = conn.getErrorStream();
				}else {
					in = conn.getInputStream();
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String line = "";
				while ((line = reader.readLine()) != null) {
					response += line;
				}
				reader.close();
				conn.disconnect();
				if (200!=conn.getResponseCode()){
					System.out.println("error: "+url+" "+param+" "+conn.getResponseCode()+" "+conn.getResponseMessage()+" "+response);
				}
				return response;
		    }

}
