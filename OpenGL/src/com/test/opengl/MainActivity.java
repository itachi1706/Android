package com.test.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private GLSurfaceView mSurfaceView;
	private GLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (hasGLES20()) {
			mGLView = new GLSurfaceView(this);
			mGLView.setEGLContextClientVersion(2);
			mGLView.setPreserveEGLContextOnPause(true);
			mGLView.setRenderer(new GLES20Renderer());
		} else {
			//GLES 2.0 not supported
		}
		setContentView(mGLView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean hasGLES20() {
		ActivityManager am = (ActivityManager)
				getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return info.reqGlEsVersion >= 0x20000;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		mGLView.onResume();	//Calling onResume for the SurfaceView
		if (mSurfaceView != null) {
			mSurfaceView.onResume();
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		mGLView.onPause();	//Calling onPause for the SurfaceView
		if (mSurfaceView != null) {
			mSurfaceView.onPause();
		}
	}
}
