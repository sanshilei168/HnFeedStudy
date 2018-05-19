package nxt.com.ssllibrary.update;

import java.io.Serializable;

public class MVersionInfo implements Serializable {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 1L;
	private int  newVCode,localVCode;
	private String newVName,localVName;
	private String versionContent;
	private String versionUrl;
	
	public int getNewVCode() {
		return newVCode;
	}
	public void setNewVCode(int newVCode) {
		this.newVCode = newVCode;
	}
	public int getLocalVCode() {
		return localVCode;
	}
	public void setLocalVCode(int localVCode) {
		this.localVCode = localVCode;
	}
	public String getNewVName() {
		return newVName;
	}
	public void setNewVName(String newVName) {
		this.newVName = newVName;
	}
	public String getLocalVName() {
		return localVName;
	}
	public void setLocalVName(String localVName) {
		this.localVName = localVName;
	}
	public String getVersionContent() {
		return versionContent;
	}
	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}
	public String getVersionUrl() {
		return versionUrl;
	}
	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}
	
	
	
}
