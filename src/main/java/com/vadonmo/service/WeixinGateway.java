package com.vadonmo.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.vadonmo.model.AccessToken;
import com.vadonmo.model.KfAccount;
import com.vadonmo.util.Global;

public class WeixinGateway {
	// private static Logger logger = Logger.getLogger(WeixinGateway.class);
	private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/";
	// 获取access_token地址
	private static final String ACCESS_TOKEN_URL = API_URL_PREFIX
			+ "token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单
	private static final String MENU_CREATE_URL = API_URL_PREFIX + "menu/create?access_token=ACCESS_TOKEN";
	private static final String MENU_GET_URL = API_URL_PREFIX + "menu/get?access_token=ACCESS_TOKEN";
	private static final String MENU_DELETE_URL = API_URL_PREFIX + "menu/delete?access_token=ACCESS_TOKEN";
	// 客服
	private static final String API_BASE_URL_PREFIX = "https://api.weixin.qq.com/";
	private static final String KFACCOUNT_ADD_URL = API_BASE_URL_PREFIX
			+ "customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	private static final String KFACCOUNT_GETLIST_URL = API_URL_PREFIX
			+ "customservice/getkflist?access_token=ACCESS_TOKEN";

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
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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
	 * 获取客服基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getKfList() throws Exception {
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = KFACCOUNT_GETLIST_URL.replace("ACCESS_TOKEN", getStaticAccessToken());
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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
	 * 添加客服帐号
	 * 
	 * @param kfAccount
	 * @return
	 * @throws Exception
	 */
	public static String addKfAccount(KfAccount kfAccount) throws Exception {
		System.out.println(kfAccount);
		String kf = "{\"kf_account\" : \"test1@test\",\"nickname\" : \"客服1\"}";
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = KFACCOUNT_ADD_URL.replace("ACCESS_TOKEN", getStaticAccessToken());
			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setEntity(new StringEntity(kf, "UTF-8"));
			CloseableHttpResponse response = httpCilent.execute(httpPost);
			try {
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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
			c.set(Calendar.HOUR, c.get(Calendar.HOUR) + 2);
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
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
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

	

	/**
	 * 这里说下，在上传视频素材的时候，微信说不超过20M，我试了下，超过10M调通的可能性都比较小，建议大家上传视频素材的大小小于10M比交好
	 * 
	 * @param accessToken
	 * @param file
	 *            上传的文件
	 * @param title
	 *            上传类型为video的参数
	 * @param introduction
	 *            上传类型为video的参数
	 */
	public void uploadPermanentMedia2(String accessToken, File file, String title, String introduction) {
		try {

			// 这块是用来处理如果上传的类型是video的类型的
			JSONObject j = new JSONObject();
			j.put("title", title);
			j.put("introduction", introduction);

			// 拼装请求地址
			String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##";
			uploadMediaUrl = uploadMediaUrl.replace("##ACCESS_TOKEN##", accessToken);

			URL url = new URL(uploadMediaUrl);
			String result = null;
			long filelength = file.length();
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			String type = "video/mp4"; // 我这里写死
			/**
			 * 你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
			 */
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary
			// 可以是任意的值(111,2222)都行
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：

			StringBuilder sb = new StringBuilder();

			// 这块是post提交type的值也就是文件对应的mime类型值
			sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); // 这里是参数名，参数名和值之间要用两次
			sb.append(type + "\r\n"); // 参数的值

			// 这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
			sb.append(j.toString() + "\r\n");

			/**
			 * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
			 * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={}
			 * 这样，如果写成这样，上面的 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
			 */

			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			// 这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\";filelength=\""
					+ filelength + "\" \r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println(sb.toString());
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.containsKey("media_id")) {
				System.out.println("media_id:" + jsonObject.getString("media_id"));
			} else {
				System.out.println(jsonObject.toString());
			}
			System.out.println("json:" + jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
