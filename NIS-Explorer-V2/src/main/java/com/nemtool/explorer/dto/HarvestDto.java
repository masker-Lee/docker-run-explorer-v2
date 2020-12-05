package com.nemtool.explorer.dto;

import java.io.Serializable;

/**
*
* @author Masker
* @date 2020.10.08
*/
public class HarvestDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int allBlocks;
	private int dayBlocks;
	private int monthBlocks;
	private long allFee;
	private long dayFee;
	private long monthFee;
	private double allBlocksPerFee;
	private double dayBlocksPerFee;
	private double monthBlocksPerFee;
	private double allBlocksPerFeeInUSD;
	private double dayBlocksPerFeeInUSD;
	private double monthBlocksPerFeeInUSD;
	private double allBlocksPerFeeInBTC;
	private double dayBlocksPerFeeInBTC;
	private double monthBlocksPerFeeInBTC;
	public int getAllBlocks() {
		return allBlocks;
	}
	public void setAllBlocks(int allBlocks) {
		this.allBlocks = allBlocks;
	}
	public int getDayBlocks() {
		return dayBlocks;
	}
	public void setDayBlocks(int dayBlocks) {
		this.dayBlocks = dayBlocks;
	}
	public int getMonthBlocks() {
		return monthBlocks;
	}
	public void setMonthBlocks(int monthBlocks) {
		this.monthBlocks = monthBlocks;
	}
	public long getAllFee() {
		return allFee;
	}
	public void setAllFee(long allFee) {
		this.allFee = allFee;
	}
	public long getDayFee() {
		return dayFee;
	}
	public void setDayFee(long dayFee) {
		this.dayFee = dayFee;
	}
	public long getMonthFee() {
		return monthFee;
	}
	public void setMonthFee(long monthFee) {
		this.monthFee = monthFee;
	}
	public double getAllBlocksPerFee() {
		return allBlocksPerFee;
	}
	public void setAllBlocksPerFee(double allBlocksPerFee) {
		this.allBlocksPerFee = allBlocksPerFee;
	}
	public double getDayBlocksPerFee() {
		return dayBlocksPerFee;
	}
	public void setDayBlocksPerFee(double dayBlocksPerFee) {
		this.dayBlocksPerFee = dayBlocksPerFee;
	}
	public double getMonthBlocksPerFee() {
		return monthBlocksPerFee;
	}
	public void setMonthBlocksPerFee(double monthBlocksPerFee) {
		this.monthBlocksPerFee = monthBlocksPerFee;
	}
	public double getAllBlocksPerFeeInUSD() {
		return allBlocksPerFeeInUSD;
	}
	public void setAllBlocksPerFeeInUSD(double allBlocksPerFeeInUSD) {
		this.allBlocksPerFeeInUSD = allBlocksPerFeeInUSD;
	}
	public double getDayBlocksPerFeeInUSD() {
		return dayBlocksPerFeeInUSD;
	}
	public void setDayBlocksPerFeeInUSD(double dayBlocksPerFeeInUSD) {
		this.dayBlocksPerFeeInUSD = dayBlocksPerFeeInUSD;
	}
	public double getMonthBlocksPerFeeInUSD() {
		return monthBlocksPerFeeInUSD;
	}
	public void setMonthBlocksPerFeeInUSD(double monthBlocksPerFeeInUSD) {
		this.monthBlocksPerFeeInUSD = monthBlocksPerFeeInUSD;
	}
	public double getAllBlocksPerFeeInBTC() {
		return allBlocksPerFeeInBTC;
	}
	public void setAllBlocksPerFeeInBTC(double allBlocksPerFeeInBTC) {
		this.allBlocksPerFeeInBTC = allBlocksPerFeeInBTC;
	}
	public double getDayBlocksPerFeeInBTC() {
		return dayBlocksPerFeeInBTC;
	}
	public void setDayBlocksPerFeeInBTC(double dayBlocksPerFeeInBTC) {
		this.dayBlocksPerFeeInBTC = dayBlocksPerFeeInBTC;
	}
	public double getMonthBlocksPerFeeInBTC() {
		return monthBlocksPerFeeInBTC;
	}
	public void setMonthBlocksPerFeeInBTC(double monthBlocksPerFeeInBTC) {
		this.monthBlocksPerFeeInBTC = monthBlocksPerFeeInBTC;
	}
	
	
	

}
