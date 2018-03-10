package com.vadonmo.message.resp;

public class Music {
	// Title 否 音乐标题
	// Description 否 音乐描述
	// MusicURL 否 音乐链接
	// HQMusicUrl 否 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	// ThumbMediaId 是 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
	private String Title;
	private String Description;
	private String MusicURL;
	private String HQMusicUrl;
	private String ThumbNediaId;

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

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbNediaId() {
		return ThumbNediaId;
	}

	public void setThumbNediaId(String thumbNediaId) {
		ThumbNediaId = thumbNediaId;
	}
}
