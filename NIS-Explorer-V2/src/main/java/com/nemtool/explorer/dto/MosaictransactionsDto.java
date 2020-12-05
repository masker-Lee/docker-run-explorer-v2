package com.nemtool.explorer.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MosaictransactionsDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String hash;

    private Long no;

    private String sender;

    private String recipient;

    private String namespace;

    private String mosaic;

    private Long quantity;

    private Long timeStamp;
    
    private int div;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace == null ? null : namespace.trim();
    }

    public String getMosaic() {
        return mosaic;
    }

    public void setMosaic(String mosaic) {
        this.mosaic = mosaic == null ? null : mosaic.trim();
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}

	@Override
	public String toString() {
		return "MosaictransactionsDto [id=" + id + ", hash=" + hash + ", no=" + no + ", sender=" + sender
				+ ", recipient=" + recipient + ", namespace=" + namespace + ", mosaic=" + mosaic + ", quantity="
				+ quantity + ", timeStamp=" + timeStamp + ", div=" + div + "]";
	}
    
}