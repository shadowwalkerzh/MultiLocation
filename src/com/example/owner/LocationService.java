package com.example.owner;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

public class LocationService extends Service {
	
	protected LocationManager locationManager;
	Location location;
	
	private static final long MIN_DISTANCE_FOR_UPDATE = 10;
	private static final long MIN_TIME_FOR_UPDATE = 1000 * 60;
	
	public LocationService(Context context){
		locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
	}
	
	public Location getLocation(String provider,LocationListener locationListener){
		if(locationManager.isProviderEnabled(provider)){
			if(provider == LocationManager.GPS_PROVIDER)
				locationManager.requestLocationUpdates(provider, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, locationListener);
			else if(provider == LocationManager.NETWORK_PROVIDER)
				locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
			System.out.println("进入1:"+provider);
		}
		
		if(locationManager != null){
			location = locationManager.getLastKnownLocation(provider);
			if(location != null){
				System.out.println("进入2:成功");
				return location;
			}
		}
		System.out.println("进入2:失败");
		return null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
