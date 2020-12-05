package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
*
* @author Masker
* @date 2020.09.05
*/
public class TransactionH2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private long timeStamp;
	private int senderid;
	private int recipientid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JSONField(name="timeStamp")
	public long getTimestamp() {
		return timeStamp;
	}
	@JSONField(name="timeStamp")
	public void setTimestamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getSenderid() {
		return senderid;
	}
	public void setSenderid(int senderid) {
		this.senderid = senderid;
	}
	public int getRecipientid() {
		return recipientid;
	}
	public void setRecipientid(int recipientid) {
		this.recipientid = recipientid;
	}
	@Override
	public String toString() {
		return "TransactionH2 [id=" + id + ", timeStamp=" + timeStamp + ", senderid=" + senderid + ", recipientid="
				+ recipientid + "]";
	}

}
