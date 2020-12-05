package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Supernodepayouts implements Serializable{
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

    private Long timeStamp;

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
        this.sender = sender == null ? null : sender.trim();
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
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

    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}