package com.vadonmo.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vadonmo.model.resp.Image;
import com.vadonmo.model.resp.ImageMessage;
import com.vadonmo.model.resp.Music;
import com.vadonmo.model.resp.MusicMessage;
import com.vadonmo.model.resp.TextMessage;
import com.vadonmo.model.resp.Video;
import com.vadonmo.model.resp.VideoMessage;
import com.vadonmo.model.resp.Voice;
import com.vadonmo.model.resp.VoiceMessage;
import com.vadonmo.util.MessageUtil;

public class CoreService {
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
					music.setDescription("描述");
					music.setThumbMediaId("SyfAQWIOwYcMvHdAXSypctU8boIt0rzmr0zwmCqEt-2M1w8JqUsBDwNe9yvm-ba2");
					music.setMusicUrl("");
					music.setTitle("標題");
					music.setHQMusicUrl("");
					musicMessage.setMusic(music);
					respMessage = MessageUtil.messageToXml(musicMessage);
					break;
				} /*else if (keyword.equals("视频")) {
					VideoMessage videoMessage = new VideoMessage();
					videoMessage.setToUserName(fromUserName);
					videoMessage.setFromUserName(toUserName);
					videoMessage.setCreateTime(new Date().getTime());
					videoMessage.setMsgType(MessageUtil.MESSAGE_TYPE_VIDEO);
					Video video = new Video();
					video.setDescription("我是一条小视频描述");
					video.setMediaId("rtQp6crZWZpnKBSrDdTNgo6K5CAqsd37EE3Vs5nucYfhBZQC4f2HvhRLjo4h9m1r");
					video.setTitle("我是一条小视频标题");
					videoMessage.setVideo(video);
					respMessage = MessageUtil.messageToXml(videoMessage);
					break;
				}*/

				respMessage = MessageUtil.messageToXml(textMessage);
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
						break;
					case "V1001_TODAY_SINGER":
						textMessage.setContent("歌手简介");
						break;
					case "V1001_GOOD":
						textMessage.setContent("赞一下我们");
						break;
					default:
						break;
					}
					respMessage = MessageUtil.messageToXml(textMessage);
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
