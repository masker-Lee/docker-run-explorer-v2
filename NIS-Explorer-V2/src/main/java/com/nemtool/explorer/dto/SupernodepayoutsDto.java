package com.nemtool.explorer.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
*
* @author Masker
* @date 2020.10.16
*/
public class SupernodepayoutsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private Integer round;
    private String sender;
    private String recipient;
    private Long amount;
    private Long fee;
    private String supernodeName;
    private Integer supernodeID;
    private Long timeStamp;
    
    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
	public String getSupernodeName() {
		return supernodeName;
	}
	public void setSupernodeName(String supernodeName) {
		this.supernodeName = supernodeName;
	}
	public Integer getSupernodeID() {
		return supernodeID;
	}
	public void setSupernodeID(Integer supernodeID) {
		this.supernodeID = supernodeID;
	}
    
    
	
}
