package com.nemtool.explorer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description: config.class for config.properties
 * @author Masker
 * @date 2020.06.28
 */
@PropertySource(value = {"classpath:config.properties"})
@ConfigurationProperties(prefix = "con")
@Component
public class Config {
    
	private String nisHost;
    private String nisPort;
    private String wsPort;
    private String wsPath;
    private String apostilleAccount;
    private String supernodePayoutAccount;
    private String supernodeDataUrl;
    private String version;
    private String pollUrl;
    private String h2DatabasePath;
    
	public String getH2DatabasePath() {
		return h2DatabasePath;
	}
	public void setH2DatabasePath(String h2DatabasePath) {
		this.h2DatabasePath = h2DatabasePath;
	}
	public String getSupernodeDataUrl() {
		return supernodeDataUrl;
	}
	public void setSupernodeDataUrl(String supernodeDataUrl) {
		this.supernodeDataUrl = supernodeDataUrl;
	}
	public String getSupernodePayoutAccount() {
		return supernodePayoutAccount;
	}
	public void setSupernodePayoutAccount(String supernodePayoutAccount) {
		this.supernodePayoutAccount = supernodePayoutAccount;
	}
	public String getApostilleAccount() {
		return apostilleAccount;
	}
	public void setApostilleAccount(String apostilleAccount) {
		this.apostilleAccount = apostilleAccount;
	}
	public String getNisHost() {
		return nisHost;
	}
	public void setNisHost(String nisHost) {
		this.nisHost = nisHost;
	}
	public String getNisPort() {
		return nisPort;
	}
	public void setNisPort(String nisPort) {
		this.nisPort = nisPort;
	}
	public String getWsPort() {
		return wsPort;
	}
	public void setWsPort(String wsPort) {
		this.wsPort = wsPort;
	}
	public String getWsPath() {
		return wsPath;
	}
	public void setWsPath(String wsPath) {
		this.wsPath = wsPath;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPollUrl() {
		return pollUrl;
	}
	public void setPollUrl(String pollUrl) {
		this.pollUrl = pollUrl;
	}
	

}

