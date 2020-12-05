package com.nemtool.explorer.pojo;

import java.io.Serializable;

public class Accountmosaics implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String address;

    private String mosaicid;

    private Long quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMosaicid() {
        return mosaicid;
    }

    public void setMosaicid(String mosaicid) {
        this.mosaicid = mosaicid == null ? null : mosaicid.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

	@Override
	public String toString() {
		return "Accountmosaics [id=" + id + ", address=" + address + ", mosaicid=" + mosaicid + ", quantity=" + quantity
				+ "]";
	}
}