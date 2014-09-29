package com.example.owner;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 定位信息Provider
 * @author Acer
 *
 */
public class LocationServiceProvider {
	
	private NetworkLocation networkLocation = null;
	private GPSLocation gpsLocation = null;
	private Context context;
	private LagLng lagLng;
	private SharedPreferences sp = null;
	public LocationServiceProvider(Context context){
		this.context = context;
		networkLocation = new NetworkLocation(context);
		gpsLocation = new GPSLocation(context);
	}
	
	public LagLng getCurrentLocation(){
		sp = context.getSharedPreferences("Location", Context.MODE_PRIVATE);
		String NetworkLocation = sp.getString("NetWorkLocation", "");
		String GPSLocation = sp.getString("GPSLocation", "");
		lagLng = new LagLng();
		//判断network是否可用
		if(networkLocation.setCurrentLocation()){
			lagLng.setLocationServiceType(networkLocation.locationServiceType);
			lagLng.setLatitude(networkLocation.lagLngBean.getLatitude());
			lagLng.setLongitude(networkLocation.lagLngBean.getLongitude());
			return lagLng;
		}else if(gpsLocation.setCurrentLocation()){//判断GPS是否可用
			lagLng.setLocationServiceType(gpsLocation.locationServiceType);
			lagLng.setLatitude(gpsLocation.lagLngBean.getLatitude());
			lagLng.setLongitude(gpsLocation.lagLngBean.getLongitude());
			return lagLng;
		}else if(!"".equals(GPSLocation)){//判断存储的GPS位置是否可用
			lagLng.setLocationServiceType(Integer.parseInt(GPSLocation.split("#")[0]));
			lagLng.setLatitude(Double.parseDouble(GPSLocation.split("#")[2]));
			lagLng.setLongitude(Double.parseDouble(GPSLocation.split("#")[3]));
			return lagLng;
		}else if(!"".equals(NetworkLocation)){//判断存储的network位置是否可用
			lagLng.setLocationServiceType(Integer.parseInt(NetworkLocation.split("#")[0]));
			lagLng.setLatitude(Double.parseDouble(NetworkLocation.split("#")[2]));
			lagLng.setLongitude(Double.parseDouble(NetworkLocation.split("#")[3]));
			return lagLng;
		}else{//使用默认经纬度
			lagLng.setLocationServiceType(0);
			lagLng.setLatitude(-36.880595);
			lagLng.setLongitude(174.797636);
		}
		
		return lagLng;
	}
	
}
