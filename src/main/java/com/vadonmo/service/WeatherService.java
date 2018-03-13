package com.vadonmo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vadonmo.model.resp.Article;
import com.vadonmo.model.resp.NewsMessage;
import com.vadonmo.util.Global;
import com.vadonmo.util.MessageUtil;

public class WeatherService {
	private static final String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";
	private static final String TIANQI_API_SECRET_KEY = "unxmjnrwzbt1yiav"; //
	private static final String TIANQI_API_USER_ID = "U5B8747C90"; //

	public static void main(String args[]) throws Exception {
		String jsonStr = WeatherService.getWeatherInfo("济南");
		JSONObject jsonObject = JSONObject.parseObject(jsonStr).getJSONArray("results").getJSONObject(0);
		JSONObject location = jsonObject.getJSONObject("location");
		JSONArray daily = jsonObject.getJSONArray("daily");
		String name = location.getString("name");
		NewsMessage newsMessage = new NewsMessage();

		List<Article> list = new ArrayList<>();
		Article article = new Article();
		article.setTitle(name);
		article.setPicUrl(
				"http://mmbiz.qpic.cn/mmbiz_jpg/WythowZsCeh5fYeG8d5aVryKFBWU13g73Yt4ltqGuKZtuwggvSprzuiadTpicaD5X8YhU8VM6FY1HtUJTzuWea0A/0");
		article.setUrl("");
		list.add(article);
		for (int i = 0, len = daily.size(); i < len; i++) {
			JSONObject object = daily.getJSONObject(i);
			Article a = new Article();
			a.setTitle(object.getString("date") + "白天：" + object.getString("text_day") + "夜间："
					+ object.getString("text_night"));
			a.setDescription("");
			a.setPicUrl(
					"http://mmbiz.qpic.cn/mmbiz_jpg/WythowZsCeh5fYeG8d5aVryKFBWU13g73Yt4ltqGuKZtuwggvSprzuiadTpicaD5X8YhU8VM6FY1HtUJTzuWea0A/0");
			a.setUrl("");
			list.add(a);
		}
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(list.size());
		newsMessage.setArticles(list);
		System.out.println(MessageUtil.newsMessageToXml(newsMessage));
	}

	private static String generateSignature(String data, String key) throws SignatureException {
		String result;
		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
			result = new sun.misc.BASE64Encoder().encode(rawHmac);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public static String generateGetDiaryWeatherURL(String location, String language, String unit, String start,
			String days) throws SignatureException, UnsupportedEncodingException {
		String timestamp = String.valueOf(new Date().getTime());
		String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
		String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
		return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language="
				+ language + "&unit=" + unit + "&start=" + start + "&days=" + days;
	}

	public static String getWeatherInfo(String location) throws Exception {
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		try {
			String requestUrl = generateGetDiaryWeatherURL(location, "zh-Hans", "c", "0", "7");
			HttpGet httpGet = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			try {
				String jsonStr = Global.toStringInfo(response.getEntity(), "UTF-8");
				System.out.println("jsonStr****:" + jsonStr);

				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpCilent.close();
		}
	}
}
