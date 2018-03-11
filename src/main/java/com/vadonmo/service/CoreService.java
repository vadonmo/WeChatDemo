package com.vadonmo.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vadonmo.message.resp.Image;
import com.vadonmo.message.resp.ImageMessage;
import com.vadonmo.message.resp.Music;
import com.vadonmo.message.resp.MusicMessage;
import com.vadonmo.message.resp.TextMessage;
import com.vadonmo.message.resp.Video;
import com.vadonmo.message.resp.VideoMessage;
import com.vadonmo.message.resp.Voice;
import com.vadonmo.message.resp.VoiceMessage;
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
					music.setThumbNediaId("1pKpzsA-7vJo-bWCzYrIHZU426kuG7eCp28yrqO4qBi2bKsAUteoaA1_5J1jHfWh");
					music.setMusicURL("");
					music.setTitle("標題");
					musicMessage.setMusic(music);
					respMessage = MessageUtil.messageToXml(musicMessage);
					break;
				}
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
			default:
				respMessage = MessageUtil.messageToXml(textMessage);
				break;
			}

			// 回复文本消息
			/*
			 * TextMessage textMessage = new TextMessage();
			 * textMessage.setToUserName(fromUserName);
			 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
			 * Date().getTime()); textMessage.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
			 * textMessage.setFuncFlag(0);
			 * 
			 * // 文本消息 if (msgType.equals(MessageUtil.MESSAGE_TYPE_TEXT)) { respContent =
			 * requestMap.get("Content"); } // 图片消息 else if
			 * (msgType.equals(MessageUtil.MESSAGE_TYPE_IMAGE)) { respContent =
			 * "您发送的是图片消息！"; } // 地理位置消息 else if
			 * (msgType.equals(MessageUtil.MESSAGE_TYPE_LOCATION)) { respContent =
			 * "您发送的是地理位置消息！"; } // 链接消息 else if
			 * (msgType.equals(MessageUtil.MESSAGE_TYPE_LINK)) { respContent = "您发送的是链接消息！";
			 * } // 音频消息 else if (msgType.equals(MessageUtil.MESSAGE_TYPE_VOICE)) {
			 * respContent = "您发送的是音频消息！"; } // 事件推送 else if
			 * (msgType.equals(MessageUtil.MESSAGE_TYPE_EVENT)) { // 事件类型 String eventType =
			 * requestMap.get("Event"); // 订阅 if
			 * (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { respContent =
			 * "谢谢您的关注！"; } // 取消订阅 else if
			 * (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // TODO
			 * 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息 } // 自定义菜单点击事件 else if
			 * (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) { // TODO
			 * 自定义菜单权没有开放，暂不处理该类消息 } }
			 * 
			 * textMessage.setContent(respContent); respMessage =
			 * MessageUtil.messageToXml(textMessage);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	
}
