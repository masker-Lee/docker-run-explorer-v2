package com.nemtool.explorer.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
*
* @author Masker
* @date 2020.09.29
*/
public class AccountDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String address;

    private Long balance;

    private Long blocks;

    private Long fees;

    private Long lastblock;

    private String publickey;

    private Long timeStamp;
    
    private String label;
    
    private String remark;
    
    private double importance;
    
    private String remoteStatus;
    
    private int harvestedBlocks;
    
    private long vestedBalance;
    
    private int minCosignatories;
    
    private int multisig;
    
    private String cosignatories;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@JSONField(name="lastBlock")
	public Long getLastblock() {
		return lastblock;
	}

	@JSONField(name="lastBlock")
	public void setLastblock(Long lastblock) {
		this.lastblock = lastblock;
	}

	@JSONField(name="publicKey")
	public String getPublickey() {
		return publickey;
	}

	@JSONField(name="publicKey")
	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	@JSONField(name="timeStamp")
	public Long getTimeStamp() {
		return timeStamp;
	}

	@JSONField(name="timeStamp")
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

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

	public double getImportance() {
		return importance;
	}

	public void setImportance(double importance) {
		this.importance = importance;
	}

	public String getRemoteStatus() {
		return remoteStatus;
	}

	public void setRemoteStatus(String remoteStatus) {
		this.remoteStatus = remoteStatus;
	}

	public int getHarvestedBlocks() {
		return harvestedBlocks;
	}

	public void setHarvestedBlocks(int harvestedBlocks) {
		this.harvestedBlocks = harvestedBlocks;
	}

	public long getVestedBalance() {
		return vestedBalance;
	}

	public void setVestedBalance(long vestedBalance) {
		this.vestedBalance = vestedBalance;
	}

	public int getMinCosignatories() {
		return minCosignatories;
	}

	public void setMinCosignatories(int minCosignatories) {
		this.minCosignatories = minCosignatories;
	}

	public int getMultisig() {
		return multisig;
	}

	public void setMultisig(int multisig) {
		this.multisig = multisig;
	}

	public String getCosignatories() {
		return cosignatories;
	}

	public void setCosignatories(String cosignatories) {
		this.cosignatories = cosignatories;
	}
	
	

}
