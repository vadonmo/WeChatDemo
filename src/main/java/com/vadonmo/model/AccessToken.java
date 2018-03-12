package com.vadonmo.model;

public class AccessToken {
	// {"access_token":"7_40nTpwJsbUtxxcbZQXQRzUgZuDc-LjaNpW8tkMWRkhCPbye1AXwAjj8KbSGb6Q5m-Yexx_nXmU5-GWIP6QUitCT6QucPFyLkY007ikVSh_VE5xO9ec2GKdxGimr-zyQSYgdyctInrtqaV9TQYFNeADANUC","expires_in":7200}
	private String access_token;
	private Long updateDate;
	private int expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

}
