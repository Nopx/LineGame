package com.nopx.game.linegame;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Jolly94 on 13.12.2017.
 */

public class GameGLView extends GLSurfaceView {

    private final GameGLRenderer gameRenderer;

    public GameGLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        gameRenderer = new GameGLRenderer();
        setRenderer(gameRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //TODO fix touch position
        float x= (e.getX()-getWidth()/2)/(getWidth()/2);
        float y= (e.getY()-getHeight()/2)/(getHeight()/2);
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                gameRenderer.setTouchCoords(x,y);
                requestRender();
        }
        return true;
    }


}
