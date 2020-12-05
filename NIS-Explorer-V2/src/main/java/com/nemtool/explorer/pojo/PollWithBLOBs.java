package com.nemtool.explorer.pojo;

public class PollWithBLOBs extends Poll {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;

    private String description;

    private String strings;

    private String addresses;

    private String whitelist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStrings() {
        return strings;
    }

    public void setStrings(String strings) {
        this.strings = strings == null ? null : strings.trim();
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses == null ? null : addresses.trim();
    }

    public String getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist == null ? null : whitelist.trim();
    }
}