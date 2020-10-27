package com.northbund.vblog.common.httpClient;/*
 * HttpTest.java  Mar 6, 2009
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.net.URLDecoder;


/**
 * @author Beyond
 */
public class HttpTest {

	public static String uri = "http://222.66.24.235:80/ctmp/";//现网地址

	public static String acsrccode = "dw9aYNyW";
	public static String pswd = "4BIsw4Tw";
	
	public static void main(String[] args) {

		/*for (int i = 0; i < 1; i++) {
			new Thread() {
				@Override
				public void run() {
					try {

					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}*/
		send();
		//queryReportByCreateTime();
	}
	
	public static void send() {


//		TokenCtrl tokenCtrl = new TokenCtrl();
//		tokenCtrl.setSpeed(20);
//		tokenCtrl.start();
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(uri, false);
			method.setURI(new URI(base, "HttpSendSM", false));
			for (int i = 0; i < 1; i++) {
//				tokenCtrl.getToken();
//				System.out.println("send" + i);
				method.setQueryString(new NameValuePair[] { 
						new NameValuePair("acsrccode", acsrccode),
						new NameValuePair("pswd", pswd),
						new NameValuePair("mobile", "13636568747"),
//						new NameValuePair("mobile", "15522540272"),
						new NameValuePair("uselongmsg", "false"),
						new NameValuePair("msg", "【北外滩影像日志】陆大大测试" ) });
				client.executeMethod(method);
//				System.out.println(method.getStatusCode() + ":" + method.getStatusText());
				System.out.println(URLDecoder.decode(method.getResponseBodyAsString(), "UTF-8"));
			}

		} catch (URIException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
	}
	
}
