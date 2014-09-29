package com.example.owner;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * GPS定位
 * @author Acer
 *
 */
class GPSLocation extends LocationBase implements LocationListener{
	
	LocationService locationService;
	private Context context; 
	private Location location = null;
	
	boolean gpsFlag = false;
	
	private SharedPreferences sp = null;
	
	public GPSLocation(Context context){
		this.context = context;
		lagLngBean = new LagLng();
		locationService = new LocationService(context);
	}
	
	public boolean setCurrentLocation(){
		
		location = locationService.getLocation(LocationManager.GPS_PROVIDER, this);
		if(location != null){
			lagLngBean.setLatitude(location.getLatitude());
			lagLngBean.setLongitude(location.getLongitude());
			lastSavingTime = getCurrentDate();
			locationServiceType = 1;
			StringBuffer buffer = new StringBuffer();
			buffer.append(locationServiceType+"#"+lastSavingTime+"#"+location.getLatitude()+"#"+location.getLongitude());
			saveCurrentLocation(buffer.toString());
			gpsFlag = true;
		}else{
			gpsFlag = false;
		}
		return gpsFlag;
	}
	
	private void saveCurrentLocation(String locationString){
		sp = context.getSharedPreferences("Location", Context.MODE_PRIVATE);
		sp.edit().putString("GPSLocation", locationString).commit();
	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		System.out.println("GPS定位：==========================="+location);
		//位置改变时重新保存
		if(location != null){
			StringBuffer buffer = new StringBuffer();
			buffer.append("1#"+getCurrentDate()+"#"+location.getLatitude()+"#"+location.getLongitude());
			saveCurrentLocation(buffer.toString());
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}
	
}
