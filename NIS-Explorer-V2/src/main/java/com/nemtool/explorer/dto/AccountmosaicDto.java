package com.nemtool.explorer.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
*
* @author Masker
* @date 2020.10.13
*/
public class AccountmosaicDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String address;

    private String mosaicid;

    private Long quantity;
    
    private String namespace;
    
    private String mosaic;
    
    private int div;

    @JSONField(name="_id")
	public Integer getId() {
		return id;
	}

    @JSONField(name="_id")
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JSONField(name="mosaicID")
	public String getMosaicid() {
		return mosaicid;
	}

	@JSONField(name="mosaicID")
	public void setMosaicid(String mosaicid) {
		this.mosaicid = mosaicid;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getMosaic() {
		return mosaic;
	}

	public void setMosaic(String mosaic) {
		this.mosaic = mosaic;
	}

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}

}
