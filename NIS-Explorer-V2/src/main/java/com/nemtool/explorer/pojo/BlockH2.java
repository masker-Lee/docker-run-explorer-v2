package com.nemtool.explorer.pojo;
/**
*
* @author Masker
* @date 2020.09.02
*/
public class BlockH2 {
	private int id;
	private int harvesterid;
	private int harvestedinname;
	private long totalfee;
	private int height;
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHarvesterid() {
		return harvesterid;
	}
	public void setHarvesterid(int harvesterid) {
		this.harvesterid = harvesterid;
	}
	public int getHarvestedinname() {
		return harvestedinname;
	}
	public void setHarvestedinname(int harvestedinname) {
		this.harvestedinname = harvestedinname;
	}
	public long getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(long totalfee) {
		this.totalfee = totalfee;
	}
	@Override
	public String toString() {
		return "BlockH2 [id=" + id + ", harvesterid=" + harvesterid + ", harvestedinname=" + harvestedinname
				+ ", totalfee=" + totalfee + ", height=" + height + "]";
	}
	
	
	
	
}
