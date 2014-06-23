/**
 * 
 */
package com.mike.appmodel;

/**
 * @author mickey20142014
 *
 */
public class AppModel {

	private int ID;
	private String date;
	private String latitude;
	private String longitude;
	private String zip_code;
	private String street_address;
	private String locality;
	private String device_imageUrls;
	private String web_imageUrls;
	
	private String infoList;
	private String addressList;


	/**
	 * @param iD
	 * @param date
	 * @param latitude
	 * @param longitude
	 * @param zip_code
	 * @param street_address
	 * @param locality
	 * @param device_imageUrls
	 * @param web_imageUrls
	 */
	public AppModel(int iD, String date, String latitude, String longitude,
			String zip_code, String street_address, String locality,
			String device_imageUrls, String web_imageUrls) {
		super();
		this.ID = iD;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.zip_code = zip_code;
		this.street_address = street_address;
		this.locality = locality;
		this.device_imageUrls = device_imageUrls;
		this.web_imageUrls = web_imageUrls;
	}

	/**
	 * 
	 */
	public AppModel() {
		super();
	}
	
	public AppModel(String webUrls){
		super();
		this.web_imageUrls = webUrls;
	}

	/**
	 * @return the web_imageUrls
	 */
	public String getWeb_imageUrls() {
		return web_imageUrls;
	}

	/**
	 * @param web_imageUrls the web_imageUrls to set
	 */
	public void setWeb_imageUrls(String web_imageUrls) {
		this.web_imageUrls = web_imageUrls;
	}

	/**
	 * @return the device_imageUrls
	 */
	public String getDevice_imageUrls() {
		return device_imageUrls;
	}

	/**
	 * @param device_imageUrls the device_imageUrls to set
	 */
	public void setDevice_imageUrls(String device_imageUrls) {
		this.device_imageUrls = device_imageUrls;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the street_address
	 */
	public String getStreet_address() {
		return street_address;
	}

	/**
	 * @param street_address the street_address to set
	 */
	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	/**
	 * @return the zip_code
	 */
	public String getZip_code() {
		return zip_code;
	}

	/**
	 * @param zip_code the zip_code to set
	 */
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the infoList
	 */
	public String getInfoList() {
		return infoList;
	}

	/**
	 * @param infoList the infoList to set
	 */
	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}

	/**
	 * @return the addressList
	 */
	public String getAddressList() {
		return addressList;
	}

	/**
	 * @param addressList the addressList to set
	 */
	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

}
