package com.nemtool.explorer.pojo;

public class AccountsWithBLOBs extends Accounts {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String label;

    private String remark;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}