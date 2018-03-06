package com.kec.guruchela;

public class ItemDetails {
	private String name;
	private String uname;
	private String itemDescription;

	public String getDescription() {
		return name;
	}

	public void setDescription(String name) {
		this.name = name;
	}

	public String getShareditem() {
		return itemDescription;
	}

	public void setShareditem(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

}
