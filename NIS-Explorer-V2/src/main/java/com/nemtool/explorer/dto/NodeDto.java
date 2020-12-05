package com.nemtool.explorer.dto;

import java.io.Serializable;

/**
*
* @author Masker
* @date 2020.10.16
*/
public class NodeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String host;
	private String port;
	private String name;
	private String version;
	private int superNodeID;
	private int height;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getSuperNodeID() {
		return superNodeID;
	}
	public void setSuperNodeID(int superNodeID) {
		this.superNodeID = superNodeID;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
