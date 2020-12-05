package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Poll implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Long timeStamp;

    private String creator;

    private String address;

    private Integer type;

    private Integer multiple;

    private Long doe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JSONField(name="timeStamp")
    public Long getTimestamp() {
        return timeStamp;
    }

    @JSONField(name="timeStamp")
    public void setTimestamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Long getDoe() {
        return doe;
    }

    public void setDoe(Long doe) {
        this.doe = doe;
    }
}