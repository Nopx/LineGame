package com.nopx.game.linegame.shapes;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Jolly94 on 14.12.2017.
 */

public class Square extends Shape {


    protected float centerX;
    protected float centerY;
    protected float width;
    protected float height;

    public Square(float x, float y, float w, float h, float[] color){
        super();
        this.color = color;
        this.width=w;
        this.height=h;
        this.centerX=x;
        this.centerY=y;
        setSize(x,y,w,h);
    }

    public void setSize(float x, float y, float w, float h){

        this.centerX=x;
        this.centerY=y;
        this.width=w;
        this.height=h;
        float xOffset=w/2;
        float yOffset=h/2;
        // Create a buffer for vertex data
        this.buffer = new float[]{
                x-xOffset,y-yOffset,0f,
                x+xOffset,y-yOffset,0f,
                x+xOffset,y+yOffset,0f,
                x-xOffset,y+yOffset,0f};
        drawOrder = new short[]{0,1,2,0,2,3};

        //initialize vertexBuffer
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer=bb.asFloatBuffer();
        vertexBuffer.put(buffer);
        vertexBuffer.position(0);

        // initialize drawListBuffer
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    public boolean isCoordinatesInSquare(float x, float y){
        float xBound=width/2;
        float yBound=height/2;
        return x<=centerX+xBound&&x>=centerX-xBound && y<=centerY+yBound&&y>=centerY-yBound;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

}
