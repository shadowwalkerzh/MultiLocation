package com.example.owner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvLocation,tvLocationMethod,tvLocationState;
	
	private int locationType;
	private double latitude = 0;
	private double longitude = 0;
	private String method = "默认";
	private String state = "搜索中";
	
	private LocationServiceProvider lsp = null;
	private LagLng lagLng = null;
	
	private ProgressDialog pd = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvLocation = (TextView)findViewById(R.id.location);
		tvLocationMethod = (TextView)findViewById(R.id.location_method);
		tvLocationState = (TextView)findViewById(R.id.location_state);
		pd = new ProgressDialog(this);
		pd.setMessage("搜索中");
		pd.setCancelable(true);
		
		lsp = new LocationServiceProvider(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(checkNetWorkState()){
			pd.show();
			getLocation();
		}else{
			showSettingNetwork(this);
			getLocation();
		}
	}
	
	private void updateUI() {
		tvLocation.setText("经纬度("+latitude+","+longitude+")");
		tvLocationMethod.setText("定位方式("+method+")");
		tvLocationState.setText("定位状态("+state+")");
	}
	

	private void getLocation(){
		lagLng = lsp.getCurrentLocation();
		locationType = lagLng.getLocationServiceType();
		state = "待机中";
		latitude = lagLng.getLatitude();
		longitude = lagLng.getLongitude();
		switch (locationType) {
		case 1:
			method = "GPS";			
			break;
		case 2:
			method = "WIFI";
			break;
		case 3:
			method = "手机网络";
			break;
		default:
			method = "默认";
			break;
		}
		updateUI();
		pd.dismiss();
					
	}
		
	private  boolean checkNetWorkState(){
		boolean flag = false;
		try {
			// 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
			ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获得网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已连接
					if (info.getState() == NetworkInfo.State.CONNECTED)
					{
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public void showSettingNetwork(final Context context){
		new AlertDialog.Builder(context)
		.setTitle("提示")
		.setMessage("亲，你还没有设置网络哦，网络定位速度更快，要马上去设置吗？")
		.setPositiveButton("是的", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		})
		.setNegativeButton("不，待会儿", null)
		.create().show();
	}
	
}
