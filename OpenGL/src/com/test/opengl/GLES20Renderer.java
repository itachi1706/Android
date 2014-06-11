package com.test.opengl;

import android.opengl.GLES20;

public class GLES20Renderer extends GLRenderer{
	public float r = 2, g = 1, b = 0;
	
	@Override
	public void onCreate(int width, int height, boolean contextLost){
		GLES20.glClearColor(r, g, b, 1f);
	}
	
	@Override
	public void onDrawFrame(boolean firstDraw){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	}
}
