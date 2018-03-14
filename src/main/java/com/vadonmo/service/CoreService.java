package com.vadonmo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vadonmo.model.resp.Article;
import com.vadonmo.model.resp.Image;
import com.vadonmo.model.resp.ImageMessage;
import com.vadonmo.model.resp.Music;
import com.vadonmo.model.resp.MusicMessage;
import com.vadonmo.model.resp.NewsMessage;
import com.vadonmo.model.resp.TextMessage;
import com.vadonmo.model.resp.Video;
import com.vadonmo.model.resp.VideoMessage;
import com.vadonmo.model.resp.Voice;
import com.vadonmo.model.resp.VoiceMessage;
import com.vadonmo.util.MessageUtil;

public class CoreService {
	public static void main(String args[]) {
		NewsMessage newsMessage = new NewsMessage();

		List<Article> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Article article = new Article();
			article.setTitle("我是图文标题" + i);
			article.setDescription("我是描述" + i);
			list.add(article);
		}
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(list.size());
		newsMessage.setArticles(list);
		System.out.println(MessageUtil.newsMessageToXml(newsMessage));
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			// textMessage.setFuncFlag(0);
			textMessage.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
			System.out.println(requestMap.toString());
			switch (msgType) {
			case MessageUtil.MESSAGE_TYPE_TEXT:
				// 回复文本消息
				String keyword = requestMap.get("Content");
				textMessage.setContent(requestMap.get("Content"));
				if (keyword.equals("音乐")) {
					MusicMessage musicMessage = new MusicMessage();
					musicMessage.setToUserName(fromUserName);
					musicMessage.setFromUserName(toUserName);
					musicMessage.setCreateTime(new Date().getTime());
					musicMessage.setMsgType(MessageUtil.MESSAGE_TYPE_MUSIC);
					Music music = new Music();
					music.setDescription("电视剧《加油吧实习生》插曲");
					music.setThumbMediaId("ikcfcovYde_TXLP5nsKFeTTiFKGkNi-ITgqA9eOZhNPAF16lNGuEf5IohBLaYCTk");
					music.setMusicUrl("http://118.89.231.141/upload/1.flac");
					music.setTitle("牛奶咖啡-明天你好");
					music.setHQMusicUrl("http://118.89.231.141/upload/1.flac");
					musicMessage.setMusic(music);
					respMessage = MessageUtil.messageToXml(musicMessage);
					break;
				} /*
					 * else if (keyword.equals("视频")) { VideoMessage videoMessage = new
					 * VideoMessage(); videoMessage.setToUserName(fromUserName);
					 * videoMessage.setFromUserName(toUserName); videoMessage.setCreateTime(new
					 * Date().getTime()); videoMessage.setMsgType(MessageUtil.MESSAGE_TYPE_VIDEO);
					 * Video video = new Video(); video.setDescription("我是一条小视频描述");
					 * video.setMediaId(
					 * "rtQp6crZWZpnKBSrDdTNgo6K5CAqsd37EE3Vs5nucYfhBZQC4f2HvhRLjo4h9m1r");
					 * video.setTitle("我是一条小视频标题"); videoMessage.setVideo(video); respMessage =
					 * MessageUtil.messageToXml(videoMessage); break; }
					 */
				else if (keyword.startsWith("天气")) {
					String keywd = keyword.replace("天气", "").trim();
					String jsonStr = WeatherService.getWeatherInfo(keywd.equals("") ? "ip" : keywd);
					JSONObject jsonObject = JSONObject.parseObject(jsonStr).getJSONArray("results").getJSONObject(0);
					JSONObject location = jsonObject.getJSONObject("location");
					JSONArray daily = jsonObject.getJSONArray("daily");
					String name = location.getString("name");
					NewsMessage newsMessage = new NewsMessage();

					List<Article> list = new ArrayList<>();
					Article article = new Article();
					article.setTitle(name + "未来三天天气预报");
					article.setPicUrl(
							"http://mmbiz.qpic.cn/mmbiz_jpg/WythowZsCeh5fYeG8d5aVryKFBWU13g73Yt4ltqGuKZtuwggvSprzuiadTpicaD5X8YhU8VM6FY1HtUJTzuWea0A/0");
					article.setUrl("");
					list.add(article);
					for (int i = 0, len = daily.size(); i < len; i++) {
						JSONObject object = daily.getJSONObject(i);
						Article a = new Article();
						a.setTitle(object.getString("date") + " " + object.getString("high") + "℃~"
								+ object.getString("low") + "℃ 白天：" + object.getString("text_day") + " 夜间："
								+ object.getString("text_night") + object.getString("wind_direction") + "风"
								+ object.getString("wind_scale") + "级");
						a.setDescription("");
						a.setPicUrl(
								"http://118.89.231.141/wechat/static/weather/" + object.getString("code_day") + ".png");
						a.setUrl("");
						list.add(a);
					}
					newsMessage.setFromUserName(toUserName);
					newsMessage.setToUserName(fromUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(list.size());
					newsMessage.setArticles(list);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				} else if (keyword.equals("分享")) {
					textMessage.setContent("http://vadon.win/wechat/share");
					respMessage = MessageUtil.messageToXml(textMessage);
				} else {
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				break;
			case MessageUtil.MESSAGE_TYPE_IMAGE:
				// 回复图片消息
				ImageMessage imageMessage = new ImageMessage();
				imageMessage.setToUserName(fromUserName);
				imageMessage.setFromUserName(toUserName);
				imageMessage.setCreateTime(new Date().getTime());
				imageMessage.setMsgType(MessageUtil.MESSAGE_TYPE_IMAGE);
				Image image = new Image(requestMap.get("MediaId"));
				imageMessage.setImage(image);
				respMessage = MessageUtil.messageToXml(imageMessage);
				break;
			case MessageUtil.MESSAGE_TYPE_VOICE:
				// 回复语音消息
				VoiceMessage voiceMessage = new VoiceMessage();
				voiceMessage.setToUserName(fromUserName);
				voiceMessage.setFromUserName(toUserName);
				voiceMessage.setCreateTime(new Date().getTime());
				voiceMessage.setMsgType(MessageUtil.MESSAGE_TYPE_VOICE);
				Voice voice = new Voice(requestMap.get("MediaId"));
				voiceMessage.setVoice(voice);
				respMessage = MessageUtil.messageToXml(voiceMessage);
				break;
			case MessageUtil.MESSAGE_TYPE_VIDEO:
				// 回复视频消息
				VideoMessage videoMessage = new VideoMessage();
				videoMessage.setToUserName(fromUserName);
				videoMessage.setFromUserName(toUserName);
				videoMessage.setCreateTime(new Date().getTime());
				videoMessage.setMsgType(MessageUtil.MESSAGE_TYPE_VIDEO);
				Video video = new Video();
				video.setDescription("描述");
				video.setMediaId(requestMap.get("MediaId"));
				video.setTitle("標題");
				videoMessage.setVideo(video);
				respMessage = MessageUtil.messageToXml(videoMessage);
				break;
			case MessageUtil.MESSAGE_TYPE_MUSIC:
				// 回复视频消息

				break;
			case MessageUtil.MESSAGE_TYPE_EVENT:// 事件
				String eventType = requestMap.get("Event");
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:// 关注
					textMessage.setContent("感谢关注");
					respMessage = MessageUtil.messageToXml(textMessage);
					break;
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:// 取消关注
					break;
				case MessageUtil.EVENT_TYPE_CLICK:// 点击事件
					String eventKey = requestMap.get("EventKey");
					switch (eventKey) {
					case "V1001_TODAY_MUSIC":
						textMessage.setContent("今日歌曲");
						MusicMessage musicMessage = new MusicMessage();
						musicMessage.setToUserName(fromUserName);
						musicMessage.setFromUserName(toUserName);
						musicMessage.setCreateTime(new Date().getTime());
						musicMessage.setMsgType(MessageUtil.MESSAGE_TYPE_MUSIC);
						Music music = new Music();
						music.setDescription("电影《我的少女时代》主题曲");
						music.setThumbMediaId("ikcfcovYde_TXLP5nsKFeTTiFKGkNi-ITgqA9eOZhNPAF16lNGuEf5IohBLaYCTk");
						music.setMusicUrl("http://118.89.231.141/upload/xxy.mp3");
						music.setTitle("小幸运");
						music.setHQMusicUrl("http://118.89.231.141/upload/xxy.mp3");
						musicMessage.setMusic(music);
						respMessage = MessageUtil.messageToXml(musicMessage);
						break;
					case "V1001_TODAY_SINGER":
						textMessage.setContent("歌手简介");
						respMessage = MessageUtil.messageToXml(textMessage);
						break;
					case "V1001_GOOD":
						textMessage.setContent("赞一下我们");
						respMessage = MessageUtil.messageToXml(textMessage);
						break;
					default:
						break;
					}

					break;
				case MessageUtil.EVENT_TYPE_SCANCODE_PUSH:
					respMessage = "success";// MessageUtil.messageToXml(textMessage);
					break;
				case MessageUtil.EVENT_TYPE_SCANCODE_WAITMSG:
					String scanResult = requestMap.get("ScanResult");// 扫描结果
					textMessage.setContent("扫描结果：\n" + scanResult);
					respMessage = MessageUtil.messageToXml(textMessage);
					break;
				case MessageUtil.EVENT_TYPE_LOCATION:
					respContent = "上传位置\n经度：" + requestMap.get("Longitude") + "\n纬度：" + requestMap.get("Latitude");
					textMessage.setContent(respContent);
					respMessage = "success";
					break;
				case MessageUtil.EVENT_TYPE_LOCATION_SELECT:
					respContent = "弹出选择器发送地址：" + requestMap.get("Poiname") + requestMap.get("Label") + "\n经度："
							+ requestMap.get("Location_X") + "\n纬度：" + requestMap.get("Location_Y");
					textMessage.setContent(respContent);
					respMessage = MessageUtil.messageToXml(textMessage);
					break;

				default:
					textMessage.setContent("无事件");
					respMessage = MessageUtil.messageToXml(textMessage);
					break;
				}
				break;
			case MessageUtil.MESSAGE_TYPE_LOCATION:
				respContent = "收到地址：" + requestMap.get("Label") + "\n经度：" + requestMap.get("Location_X") + "\n纬度："
						+ requestMap.get("Location_Y");
				textMessage.setContent(respContent);
				respMessage = MessageUtil.messageToXml(textMessage);
				break;
			default:
				textMessage.setContent(respContent);
				respMessage = "success";// MessageUtil.messageToXml(textMessage);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
