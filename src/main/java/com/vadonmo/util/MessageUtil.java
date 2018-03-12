package com.vadonmo.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.vadonmo.model.resp.Article;
import com.vadonmo.model.resp.ImageMessage;
import com.vadonmo.model.resp.MusicMessage;
import com.vadonmo.model.resp.NewsMessage;
import com.vadonmo.model.resp.TextMessage;

public class MessageUtil {

	public static void main(String atgs[]) throws DocumentException {
		String xml = "<xml><ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>"
				+ "<FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>"
				+ "<CreateTime>1408090502</CreateTime>" + "<MsgType><![CDATA[event]]></MsgType>"
				+ "<Event><![CDATA[scancode_push]]></Event>" + "<EventKey><![CDATA[6]]></EventKey>"
				+ "<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>" + "<ScanResult><![CDATA[1]]></ScanResult>"
				+ "</ScanCodeInfo>" + "</xml>";
		Document document = DocumentHelper.parseText(xml);
		// 得到xml根元素
		Element root = document.getRootElement();
		Map<String, String> map = new HashMap<String, String>();
		map = getXml(root);
		System.out.println(map);
	}

	/**
	 * 文本
	 */
	public static final String MESSAGE_TYPE_TEXT = "text";

	/**
	 * 音乐
	 */
	public static final String MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 图文
	 */
	public static final String MESSAGE_TYPE_NEWS = "news";
	/**
	 * 图片
	 */
	public static final String MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 链接
	 */
	public static final String MESSAGE_TYPE_LINK = "link";

	/**
	 * 地理位置
	 */
	public static final String MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 音频
	 */
	public static final String MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 视频
	 */
	public static final String MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 推送
	 */
	public static final String MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	/**
	 * 事件类型：scancode_push(扫码推事件的事件推送)
	 */
	public static final String EVENT_TYPE_SCANCODE_PUSH = "scancode_push";
	/**
	 * 事件类型：scancode_waitmsg(扫码推事件的事件推送)
	 */
	public static final String EVENT_TYPE_SCANCODE_WAITMSG = "scancode_waitmsg";
	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	/**
	 * 事件类型：location_select(弹出地理位置选择器的事件推送)
	 */
	public static final String EVENT_TYPE_LOCATION_SELECT = "location_select";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();

		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// List<Element> elementList = root.elements();
		map = getXml(root);
		// for (Element element : elementList) {
		// map.put(element.getName(), element.getText());
		// }
		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getXml(Element root) {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		List<Element> elementList = root.elements();
		for (Element element : elementList) {
			// 得到当前节点下的节点
			if (element.nodeCount() > 1) {
				map.putAll(getXml(element));
			} else {
				map.put(element.getName(), element.getText());
				// System.out.println("name:" + element.getName() + "val:" + element.getText());
			}
		}
		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param <T>
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static <T> String messageToXml(T t) {
		xstream.alias("xml", t.getClass());
		// System.out.println(xstream.toXML(t));
		return xstream.toXML(t);
	}

	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
