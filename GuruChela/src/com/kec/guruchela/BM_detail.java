package com.kec.guruchela;

public class BM_detail {

	String code = null;
	String name = null;
	String continent = null;
	String region = null;
	String continent1 = null;
	String region1 = null;

	public String getSubject() {
		return code;
	}

	public void setSubject(String code) {
		this.code = code;
	}

	public String getTLect() {
		return name;
	}

	public void setTLect(String name) {
		this.name = name;
	}

	public String getBLect() {
		return continent;
	}

	public void setBLect(String continent) {
		this.continent = continent;
	}

	public String getRAtt() {
		return region;
	}

	public void setRAtt(String region) {
		this.region = region;
	}

	public String getCAtt() {
		return continent1;
	}

	public void setCAtt(String continent1) {
		this.continent1 = continent1;
	}

	public String getBEff() {
		return region1;
	}

	public void setBEff(String region1) {
		this.region1 = region1;
	}

}
