package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;


public class Transactions implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String hash;

    private Integer height;

    private String sender;

    private String recipient;

    private Long amount;

    private Long fee;

    private Long timeStamp;

    private Long deadline;

    private String signature;

    private Integer type;

    private Integer apostilleflag;

    private Integer mosaictransferflag;

    private Integer aggregateflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JSONField(name="apostilleFlag")
    public Integer getApostilleflag() {
        return apostilleflag;
    }

    @JSONField(name="apostilleFlag")
    public void setApostilleflag(Integer apostilleflag) {
        this.apostilleflag = apostilleflag;
    }

    @JSONField(name="mosaicTransferFlag")
    public Integer getMosaictransferflag() {
        return mosaictransferflag;
    }

    @JSONField(name="mosaicTransferFlag")
    public void setMosaictransferflag(Integer mosaictransferflag) {
        this.mosaictransferflag = mosaictransferflag;
    }

    @JSONField(name="aggregateFlag")
    public Integer getAggregateflag() {
        return aggregateflag;
    }

    @JSONField(name="aggregateFlag")
    public void setAggregateflag(Integer aggregateflag) {
        this.aggregateflag = aggregateflag;
    }

	@Override
	public String toString() {
		return "Transactions [id=" + id + ", hash=" + hash + ", height=" + height + ", sender=" + sender
				+ ", recipient=" + recipient + ", amount=" + amount + ", fee=" + fee + ", timeStamp=" + timeStamp
				+ ", deadline=" + deadline + ", signature=" + signature + ", type=" + type + ", apostilleflag="
				+ apostilleflag + ", mosaictransferflag=" + mosaictransferflag + ", aggregateflag=" + aggregateflag
				+ "]";
	}
    
    
}