package com.example.owner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 定位Bean
 * @author Acer
 *
 */
public class LocationBase {
	
	//private long latitude;//经度
	//private long longitude;//纬度
	LagLng lagLngBean;//经纬度
	String lastSavingTime;//上次保存时间
	int locationServiceType;//定位类型  1表示GPS，2表示NetWork Wifi,3表示Network 手机网络
	
	/*
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	*/
	
	public String getLastSavingTime() {
		return lastSavingTime;
	}
	public LagLng getLagLngBean() {
		return lagLngBean;
	}
	public void setLagLngBean(LagLng lagLngBean) {
		this.lagLngBean = lagLngBean;
	}
	public void setLastSavingTime(String lastSavingTime) {
		this.lastSavingTime = lastSavingTime;
	}
	public int getLocationServiceType() {
		return locationServiceType;
	}
	public void setLocationServiceType(int locationServiceType) {
		this.locationServiceType = locationServiceType;
	}
	
	public static String getCurrentDate(){
		String currentDate = "";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ,Locale.CHINA);
		currentDate = format.format(date);
		return currentDate;
	}
}
