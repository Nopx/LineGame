package com.nopx.game.linegame;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.nopx.game.linegame.shapes.Square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jolly94 on 13.12.2017.
 */

public class GameGLRenderer implements GLSurfaceView.Renderer{

    //Shapes
    private Square square;

    //Projection
    private final float[] mMVPMatrixInverted = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    float xTouch=0;
    float yTouch=0;
    float xTouchFactor=1f;
    float yTouchFactor=1f;
    float zTouch=-3f; //also eyeZ
    int width=1;
    int height=1;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        GLES20.glClearColor(0f,0f,0f,1f);
        square = new Square(0f,0f,0.1f,0.1f);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glViewport(0, 0, width, height);

        this.width=width;
        this.height=height;

        float ratio = (float) width / height;

        //Matrix is adapted here and applied in onDrawFrame()
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, zTouch, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.invertM(mMVPMatrixInverted,0,mMVPMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float[] squareMoveMatrix=new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.translateM(squareMoveMatrix,0,mMVPMatrix,0,xTouch,yTouch,0);

        Log.i("DRAWFRAME","WORKING "+xTouch+" "+yTouch+" -- "+width+" "+height);
        // Draw shape
        square.draw(squareMoveMatrix);
    }


    public void setTouchCoords(float x, float y){
       float[] vec=new float[]{x,-y,0f,1f};
       Matrix.multiplyMV(vec,0,mMVPMatrixInverted,0,vec,0);
       //Times inverted MVPMatrix and then times 3, God knows why
       this.xTouch=vec[0]*3;
       this.yTouch=vec[1]*3;
    }

}
