package com.example.owner;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


/**
 * 手机网络定位
 * @author Acer
 *
 */
class NetworkLocation extends LocationBase implements LocationListener{
	
	LocationService locationService;
	private Context context; 
	private Location location = null;
	
	boolean netFlag = false;
	
	private SharedPreferences sp = null;
	
	public NetworkLocation(Context  context){
		this.context = context;
		locationService = new LocationService(context);
		lagLngBean = new LagLng();
	}
	
	public boolean setCurrentLocation(){
		
		//判断网络是否可用
		if(checkNetWorkState()){
			if(location == null)
				location = locationService.getLocation(LocationManager.NETWORK_PROVIDER, this);
			if(location != null){
				lagLngBean.setLatitude(location.getLatitude());
				lagLngBean.setLongitude(location.getLongitude());
				lastSavingTime = getCurrentDate();
				StringBuffer buffer = new StringBuffer();
				buffer.append(locationServiceType+"#"+lastSavingTime+"#"+location.getLatitude()+"#"+location.getLongitude());
				saveCurrentLocation(buffer.toString());
				netFlag = true;
			}else{
				netFlag = false;
			}
		}
		return netFlag;
	}
	
	private void saveCurrentLocation(String locationString){
		sp = context.getSharedPreferences("Location", Context.MODE_PRIVATE);
		sp.edit().putString("NetWorkLocation", locationString).commit();
	}

	private  boolean checkNetWorkState(){
		boolean flag = false;
		try {
			// 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
			ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获得网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已连接
					if (info.getState() == NetworkInfo.State.CONNECTED)
					{
						if(info.getType() == ConnectivityManager.TYPE_WIFI){
							locationServiceType = 2;
						}else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
							locationServiceType = 3;
						}
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		System.out.println("NetWork定位：==============================="+location);
		//位置改变时重新保存
		if(location != null && locationServiceType >0){
			StringBuffer buffer = new StringBuffer();
			buffer.append(locationServiceType+"#"+getCurrentDate()+"#"+location.getLatitude()+"#"+location.getLongitude());
			saveCurrentLocation(buffer.toString());
		}		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
