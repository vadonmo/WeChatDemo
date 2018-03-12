package com.vadonmo.model.req;

public class LinkMessage extends BaseMessage {
	// Title 消息标题
	// Description 消息描述
	// Url 消息链接
	private String Title;
	private String Description;
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
