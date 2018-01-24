package com.nopx.game.linegame;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.nopx.game.linegame.shapes.Diamond;
import com.nopx.game.linegame.shapes.Grid;
import com.nopx.game.linegame.shapes.Square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jolly94 on 13.12.2017.
 */

public class GameGLRenderer implements GLSurfaceView.Renderer{

    //Shapes
    private int actorCount=4;
    private Diamond[] actors=new Diamond[actorCount];
    private Grid[] grids=new Grid[actorCount];
    private float[][] centerVecs=new float[actorCount][2];

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
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Log.i("lsdajhfsd","sydhfkdhjkshdfh");
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

        // Calculate inverse View Projection Matrix to get screen coords to world coords
        Matrix.invertM(mMVPMatrixInverted,0,mMVPMatrix,0);

        float[] boundVec=vecToWorld(new float[]{1,1,0,1});
        /*
        grids[0] = new Grid(0,boundVec[1],boundVec[0],0,0.1f,3,
                5,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });

        grids[1] = new Grid(-boundVec[0],boundVec[1],0,0,0.1f,3,
                5,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });

        grids[2] = new Grid(0,0,boundVec[0],-boundVec[1],0.1f,3,
                5,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });

        grids[3] = new Grid(-boundVec[0],0,0,-boundVec[1],0.1f,3,
                5,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });

        actors[0]=new Diamond(0f,0f,0.1f,0.1f, new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        actors[1]=new Diamond(0f,0f,0.1f,0.1f, new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        actors[2]=new Diamond(0f,0f,0.1f,0.1f, new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        actors[3]=new Diamond(0f,0f,0.1f,0.1f, new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        */
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        float[] squareMoveMatrix=new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.translateM(squareMoveMatrix,0,mMVPMatrix,0,xTouch,yTouch,0);

        //Adapt shapes
        for(Grid g: grids){
            g.highlightNewCell(xTouch,yTouch);
        }

        // Draw shapes
        for(Grid g: grids){
            g.draw(mMVPMatrix);
        }
        for(Diamond actor: actors){
            actor.draw(mMVPMatrix);
        }
    }


    public void setTouchCoords(float x, float y){
       float[] vec=new float[]{x,-y,0f,1f};
       Matrix.multiplyMV(vec,0,mMVPMatrixInverted,0,vec,0);
       //Times inverted MVPMatrix and then times 3, God knows why
       this.xTouch=vec[0]*3;
       this.yTouch=vec[1]*3;
    }

    //transforms coordinates in range [-1;1] to world coordinates
    public float[] vecToWorld(float[] inVec){
        float[] worldVec = new float[4];
        worldVec[3]=1;
        for(int i =0;i<inVec.length&&i<worldVec.length;i++)
            worldVec[i]=inVec[i];
        worldVec[1]*=-1;
        Matrix.multiplyMV(worldVec,0,mMVPMatrixInverted,0,worldVec,0);
        for(int i=0;i<worldVec.length;i++)
            worldVec[i]*=3;
        return worldVec;
    }

}
