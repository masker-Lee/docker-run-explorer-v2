package com.nemtool.explorer.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
*
* @author Masker
* @date 2020.10.13
*/
public class MosaicDto implements Serializable{

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

    private String transferable;

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
    
    private int levyDiv;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JSONField(name="mosaicID")
	public String getMosaicid() {
		return mosaicid;
	}

	@JSONField(name="mosaicID")
	public void setMosaicid(String mosaicid) {
		this.mosaicid = mosaicid;
	}

	@JSONField(name="mosaicName")
	public String getMosaicname() {
		return mosaicname;
	}

	@JSONField(name="mosaicName")
	public void setMosaicname(String mosaicname) {
		this.mosaicname = mosaicname;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
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

	public String getTransferable() {
		return transferable;
	}

	public void setTransferable(String transferable) {
		this.transferable = transferable;
	}

	@JSONField(name="levyType")
	public Integer getLevytype() {
		return levytype;
	}

	@JSONField(name="levyType")
	public void setLevytype(Integer levytype) {
		this.levytype = levytype;
	}

	@JSONField(name="levyRecipient")
	public String getLevyrecipient() {
		return levyrecipient;
	}

	@JSONField(name="levyRecipient")
	public void setLevyrecipient(String levyrecipient) {
		this.levyrecipient = levyrecipient;
	}

	@JSONField(name="levyNamespace")
	public String getLevynamespace() {
		return levynamespace;
	}

	@JSONField(name="levyNamespace")
	public void setLevynamespace(String levynamespace) {
		this.levynamespace = levynamespace;
	}

	@JSONField(name="levyMosaic")
	public String getLevymosaic() {
		return levymosaic;
	}

	@JSONField(name="levyMosaic")
	public void setLevymosaic(String levymosaic) {
		this.levymosaic = levymosaic;
	}

	@JSONField(name="levyFee")
	public Long getLevyfee() {
		return levyfee;
	}

	@JSONField(name="levyFee")
	public void setLevyfee(Long levyfee) {
		this.levyfee = levyfee;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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
		this.description = description;
	}

	public int getLevyDiv() {
		return levyDiv;
	}

	public void setLevyDiv(int levyDiv) {
		this.levyDiv = levyDiv;
	}
    
}
