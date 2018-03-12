package com.vadonmo.model.resp;

public class Image {
	// MediaId 是 通过素材管理中的接口上传多媒体文件，得到的id。
	private String MediaId;

	public Image() {

	}

	public Image(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
