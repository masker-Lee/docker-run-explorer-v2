package com.nemtool.explorer.pojo;

import java.io.Serializable;

/**
*
* @author Masker
* @date 2020.08.28
*/
public class AccountH2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String address;
	private String publickey;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPublickey() {
		return publickey;
	}
	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "AccountH2 [id=" + id + ", address=" + address + ", publickey=" + publickey + "]";
	}
	
	
	
}
