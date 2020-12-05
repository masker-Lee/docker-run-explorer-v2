package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description: blocks class
 * @author Masker
 * @date 2020.06.28
 */
public class Blocks implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer height;
    private Long timeStamp;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	@JSONField(name="timeStamp")
	public Long getTimestamp() {
		return timeStamp;
	}
	@JSONField(name="timeStamp")
	public void setTimestamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Blocks [id=" + id + ", height=" + height + ", timeStamp=" + timeStamp + "]";
	}
    
    
   
}