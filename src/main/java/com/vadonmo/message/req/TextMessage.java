package com.vadonmo.message.req;

public class TextMessage extends BaseMessage {
	// 文本消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
