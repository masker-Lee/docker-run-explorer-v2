package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Mosaics implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String mosaicid;

    private String mosaicname;

    private String namespace;

    private Integer divisibility;

    private Long initialsupply;

    private Integer supplymutable;

    private Integer transferable;

    private Integer levytype;

    private String levyrecipient;

    private String levynamespace;

    private String levymosaic;

    private Long levyfee;

    private String creator;

    private Long timeStamp;

    private Integer height;

    private Long no;

    private String description;

    public Integer getId() {
        return id;
    }

    @JSONField(name="mosaicID")
    public void setId(Integer id) {
        this.id = id;
    }

    @JSONField(name="mosaicID")
    public String getMosaicid() {
        return mosaicid;
    }

    public void setMosaicid(String mosaicid) {
        this.mosaicid = mosaicid == null ? null : mosaicid.trim();
    }

    @JSONField(name="mosaicName")
    public String getMosaicname() {
        return mosaicname;
    }

    @JSONField(name="mosaicName")
    public void setMosaicname(String mosaicname) {
        this.mosaicname = mosaicname == null ? null : mosaicname.trim();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace == null ? null : namespace.trim();
    }

    public Integer getDivisibility() {
        return divisibility;
    }

    public void setDivisibility(Integer divisibility) {
        this.divisibility = divisibility;
    }

    @JSONField(name="initialSupply")
    public Long getInitialsupply() {
        return initialsupply;
    }

    @JSONField(name="initialSupply")
    public void setInitialsupply(Long initialsupply) {
        this.initialsupply = initialsupply;
    }

    @JSONField(name="supplyMutable")
    public Integer getSupplymutable() {
        return supplymutable;
    }

    @JSONField(name="supplyMutable")
    public void setSupplymutable(Integer supplymutable) {
        this.supplymutable = supplymutable;
    }

    public Integer getTransferable() {
        return transferable;
    }

    public void setTransferable(Integer transferable) {
        this.transferable = transferable;
    }

    public Integer getLevytype() {
        return levytype;
    }

    public void setLevytype(Integer levytype) {
        this.levytype = levytype;
    }

    public String getLevyrecipient() {
        return levyrecipient;
    }

    public void setLevyrecipient(String levyrecipient) {
        this.levyrecipient = levyrecipient == null ? null : levyrecipient.trim();
    }

    public String getLevynamespace() {
        return levynamespace;
    }

    public void setLevynamespace(String levynamespace) {
        this.levynamespace = levynamespace == null ? null : levynamespace.trim();
    }

    public String getLevymosaic() {
        return levymosaic;
    }

    public void setLevymosaic(String levymosaic) {
        this.levymosaic = levymosaic == null ? null : levymosaic.trim();
    }

    public Long getLevyfee() {
        return levyfee;
    }

    public void setLevyfee(Long levyfee) {
        this.levyfee = levyfee;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	@Override
	public String toString() {
		return "Mosaics [id=" + id + ", mosaicid=" + mosaicid + ", mosaicname=" + mosaicname + ", namespace="
				+ namespace + ", divisibility=" + divisibility + ", initialsupply=" + initialsupply + ", supplymutable="
				+ supplymutable + ", transferable=" + transferable + ", levytype=" + levytype + ", levyrecipient="
				+ levyrecipient + ", levynamespace=" + levynamespace + ", levymosaic=" + levymosaic + ", levyfee="
				+ levyfee + ", creator=" + creator + ", timeStamp=" + timeStamp + ", height=" + height + ", no=" + no
				+ ", description=" + description + "]";
	}

    
}