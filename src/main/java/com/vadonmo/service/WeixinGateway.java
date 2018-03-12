package com.vadonmo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.vadonmo.model.AccessToken;
import com.vadonmo.util.Global;

public class WeixinGateway {
	// private static Logger logger = Logger.getLogger(WeixinGateway.class);
	private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin/";
	// 获取access_token地址
	private static final String ACCESS_TOKEN_URL = BASE_URL
			+ "token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 创建菜单
	private static final String MENU_CREATE_URL = BASE_URL + "menu/create?access_token=ACCESS_TOKEN";
	// 查询菜单
	private static final String MENU_GET_URL = BASE_URL + "menu/get?access_token=ACCESS_TOKEN";
	// 删除菜单
	private static final String MENU_DELETE_URL = BASE_URL + "menu/delete?access_token=ACCESS_TOKEN";

	public static void main(String args[]) throws Exception {
		getStaticAccessToken();
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public static String createMenu(String menu) throws Exception {
		System.out.println(menu);
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = MENU_CREATE_URL.replace("ACCESS_TOKEN", getStaticAccessToken());
			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setEntity(new StringEntity(menu, "UTF-8"));
			CloseableHttpResponse response = httpCilent.execute(httpPost);
			try {
				String jsonStr = toStringInfo(response.getEntity(), "UTF-8");
				System.out.println("entity****:" + jsonStr);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpCilent.close();
		}
	}

	/**
	 * 查询菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getMenu() throws Exception {
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = MENU_GET_URL.replace("ACCESS_TOKEN", getStaticAccessToken());
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = toStringInfo(response.getEntity(), "UTF-8");
				System.out.println("entity****:" + entity.getContent());
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpCilent.close();
		}
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String deleteMenu() throws Exception {
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = MENU_DELETE_URL.replace("ACCESS_TOKEN", getStaticAccessToken());
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = toStringInfo(response.getEntity(), "UTF-8");
				System.out.println("entity****:" + entity.getContent());
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpCilent.close();
		}
	}

	/**
	 * 获取accessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getStaticAccessToken() throws Exception {
		if (Global.ACCESS_TOKEN != null) {
			Long oldtime = Global.ACCESS_TOKEN_UPDATE_TIME;
			Date oldDate = new Date(oldtime);
			System.out.println("caoguoming***全局表里token的值---token：" + Global.ACCESS_TOKEN);
			Calendar c = Calendar.getInstance();
			c.setTime(oldDate);
			c.set(Calendar.HOUR, c.get(Calendar.HOUR) + 1);
			// 校验时间是否超过
			if (new Date().getTime() > c.getTimeInMillis()) {
				System.out.println("caoguoming****更新token的系统时间***---" + new Date().getTime());
				System.out.println("caoguoming****全局表里token的时间---" + c.getTimeInMillis());
				System.out.println("caoguoming****更新token前的值--token：---" + Global.ACCESS_TOKEN);
				String gtoken = getAccessToken();
				if (gtoken == null) {
					System.out.println("获取token fail");
				} else {
					// 重新录入
					System.out.println("caoguoming****更新token后的值***token：---" + gtoken);
					AccessToken accessToken = new Gson().fromJson(gtoken, AccessToken.class);
					Global.ACCESS_TOKEN = accessToken.getAccess_token();
					Global.ACCESS_TOKEN_UPDATE_TIME = new Date().getTime();
					// caoguoming添加生成token的明细----结束--------
					System.out.println("caoguoming***生成token的明细***--success---gtoken：" + gtoken);
					gtoken = Global.ACCESS_TOKEN;
				}
				return gtoken;
			} else {
				System.out.println("caoguoming****token在有效期***返回token的值为：---" + Global.ACCESS_TOKEN);
				return Global.ACCESS_TOKEN;
			}
		} else {
			String gtoken = getAccessToken();
			if (gtoken == null) {
				System.out.println("获取token fail");
			} else {
				AccessToken accessToken = new Gson().fromJson(gtoken, AccessToken.class);
				Global.ACCESS_TOKEN = accessToken.getAccess_token();
				Global.ACCESS_TOKEN_UPDATE_TIME = new Date().getTime();
				return Global.ACCESS_TOKEN;
			}
		}
		return null;
	}

	/**
	 * 获取accessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getAccessToken() throws Exception {
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = ACCESS_TOKEN_URL.replace("APPID", Global.APPID).replace("APPSECRET", Global.APPSECRET);
			HttpGet httpGet = new HttpGet(requestUrl); // 设置响应头信息
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = toStringInfo(response.getEntity(), "UTF-8");
				System.out.println("entity****:" + entity.getContent());
				// 微信返回的报文时GBK，直接使用httpcore解析乱码
				// String jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpCilent.close();
		}
	}

	private static String toStringInfo(HttpEntity entity, String defaultCharset) throws Exception, IOException {
		final InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
					"HTTP entity too large to be buffered in memory");
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Charset charset = null;

			if (charset == null) {
				charset = Charset.forName(defaultCharset);
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			final Reader reader = new InputStreamReader(instream, charset);
			final CharArrayBuffer buffer = new CharArrayBuffer(i);
			final char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} finally {
			instream.close();
		}
	}

}
