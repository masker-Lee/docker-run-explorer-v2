package com.nemtool.explorer.dto;

import java.io.Serializable;

/**
*
* @author Masker
* @date 2020.10.16
*/
public class SupernodeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String payoutAddress;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayoutAddress() {
		return payoutAddress;
	}
	public void setPayoutAddress(String payoutAddress) {
		this.payoutAddress = payoutAddress;
	}
	
	

}
