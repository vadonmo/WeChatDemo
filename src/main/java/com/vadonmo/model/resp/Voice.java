package com.vadonmo.model.resp;

public class Voice {
	// MediaId 是 通过素材管理中的接口上传多媒体文件，得到的id
	private String MediaId;

	public Voice() {
	}

	public Voice(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
