package com.nopx.game.linegame.shapes;

import android.opengl.Matrix;

/**
 * Created by root on 23.01.2018.
 */

public class Diamond extends Square {

    public Diamond(float x, float y, float w, float h, float[] color) {
        super(x, y, w, h, color);
    }

    @Override
    public void draw(float[] mvpMatrix){
        float[] newMVPMatrix=new float[mvpMatrix.length];
        Matrix.rotateM(newMVPMatrix,0,mvpMatrix,0,45f,0,0,-1.0f);
        actualDraw(newMVPMatrix);
    }
}
