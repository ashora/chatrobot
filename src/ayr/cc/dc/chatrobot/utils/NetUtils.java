package ayr.cc.dc.chatrobot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import ayr.cc.dc.chatrobot.entity.ChatEntity;
import ayr.cc.dc.chatrobot.entity.ChatEntity.Type;
import ayr.cc.dc.chatrobot.entity.ResultEntity;

import com.google.gson.Gson;

public class NetUtils {
	/**
	 * API_KEY 图灵机器人网站申请
	 */
	private final static String API_KEY = "c97668ecf57d02e75713351dcbee7828";
    
	/***
	 * 访问的URL,用于拼接
	 */
	private final static String URL = "http://www.tuling123.com/openapi/api";
	
	/**
	 * 使用Gson 将返回的json字符串封装成实体类
	 * @param sendMesage 用户发送的信息
	 * @return 服务器返回的数据封装的实体类
	 */
	public static ChatEntity getChatEntity(String sendMesage){
		ChatEntity entity = new ChatEntity();
		String message = getResult(sendMesage);
		Gson gson = new Gson();
		ResultEntity result = null;
		try {
			result  =gson.fromJson(message, ResultEntity.class);
			entity.setMessage(result.getText());
		} catch (Exception e) {
			entity.setMessage("服务器繁忙");
		}
		entity.setDate(new Date());
		entity.setType(Type.INFORECEIVE);
		return entity;
	}

	/**
	 * 获取返回的字符串
	 * @param sendMesage 用户发送的信息
	 * @return 服务器返回的数据
	 */
	public static String getResult(String sendMesage) {
		String result = "";
		HttpURLConnection conn  = null;
		ByteArrayOutputStream baos = null;
		try {
			String realURL=getUrl(sendMesage);
			URL url = new URL(realURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[128];
			int len = -1;
			baos = new ByteArrayOutputStream();
			while((len= is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			baos.flush();
			result  = new String(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(baos!=null){
				try {
					baos.close();
					baos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(conn!=null){
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}

	/**
	 * 拼接URL 
	 * @param sendMessage 用户输入的信息
	 * @return 拼接后的URRL
	 */
	public static String getUrl(String sendMessage) {
		String requesturl = "";
		try {
			String INFO = URLEncoder.encode(sendMessage, "utf-8");
			requesturl = URL + "?key=" + API_KEY + "&info=" + INFO;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requesturl;
	}
}
