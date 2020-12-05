package com.nemtool.explorer.dto;

import java.io.Serializable;

/**
*
* @author Masker
* @date 2020.10.14
*/
public class NamespaceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String namespace;
	
	private int height;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
