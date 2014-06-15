/**
 * 
 */
package com.mike.appmodel;

/**
 * @author mickey20142014
 *
 */
public class ModelClass {

	/**
	 * @param title
	 * @param title_sub_info
	 */
	public ModelClass(String title, String title_sub_info) {
		super();
		this.title = title;
		this.title_sub_info = title_sub_info;
	}
	String title;
	String title_sub_info;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the title_sub_info
	 */
	public String getTitle_sub_info() {
		return title_sub_info;
	}
	/**
	 * @param title_sub_info the title_sub_info to set
	 */
	public void setTitle_sub_info(String title_sub_info) {
		this.title_sub_info = title_sub_info;
	}
	
}
