package com.nemtool.explorer.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Namespaces implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String namespace;

    private Long no;

    private String rootnamespace;
    
    private Integer rootNamespaceId;

    private String creator;

    private Integer height;

    private Long timeStamp;

    private Long expiredtime;

    private String subnamespaces;

    private String mosaicnames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace == null ? null : namespace.trim();
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    @JSONField(name="rootNamespace")
    public String getRootnamespace() {
        return rootnamespace;
    }

    @JSONField(name="rootNamespace")
    public void setRootnamespace(String rootnamespace) {
        this.rootnamespace = rootnamespace == null ? null : rootnamespace.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
    
    @JSONField(name="expiredTime")
    public Long getExpiredtime() {
        return expiredtime;
    }
    
    @JSONField(name="expiredTime")
    public void setExpiredtime(Long expiredtime) {
        this.expiredtime = expiredtime;
    }

    @JSONField(name="subNamespaces")
    public String getSubnamespaces() {
        return subnamespaces;
    }

    @JSONField(name="subNamespaces")
    public void setSubnamespaces(String subnamespaces) {
        this.subnamespaces = subnamespaces == null ? null : subnamespaces.trim();
    }

    @JSONField(name="mosaicName")
    public String getMosaicnames() {
        return mosaicnames;
    }

    @JSONField(name="mosaicName")
    public void setMosaicnames(String mosaicnames) {
        this.mosaicnames = mosaicnames == null ? null : mosaicnames.trim();
    }

    @JSONField(name="rootNamespaceId")
	public Integer getRootNamespaceId() {
		return rootNamespaceId;
	}

    @JSONField(name="rootNamespaceId")
	public void setRootNamespaceId(Integer rootNamespaceId) {
		this.rootNamespaceId = rootNamespaceId;
	}
    
}