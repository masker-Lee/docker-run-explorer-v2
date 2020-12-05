package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Accounts implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String address;

    private Long balance;

    private Long blocks;

    private Long fees;

    private Long lastblock;

    private String publickey;

    private Long timeStamp;
    
    private String label;
    
    private String remark;
    

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getBlocks() {
        return blocks;
    }

    public void setBlocks(Long blocks) {
        this.blocks = blocks;
    }

    public Long getFees() {
        return fees;
    }

    public void setFees(Long fees) {
        this.fees = fees;
    }

    public Long getLastblock() {
        return lastblock;
    }

    public void setLastblock(Long lastblock) {
        this.lastblock = lastblock;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey == null ? null : publickey.trim();
    }

    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}