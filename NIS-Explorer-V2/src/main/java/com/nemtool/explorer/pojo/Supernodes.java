package com.nemtool.explorer.pojo;

import java.io.Serializable;

public class Supernodes implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String host;

    private String name;

    private String time;

    private String payoutaddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getPayoutaddress() {
        return payoutaddress;
    }

    public void setPayoutaddress(String payoutaddress) {
        this.payoutaddress = payoutaddress == null ? null : payoutaddress.trim();
    }
}