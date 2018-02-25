package com.nopx.game.linegame.shapes;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by root on 26.01.2018.
 */

public class Line extends Square {

    protected float startX;
    protected float startY;
    protected float width;
    protected float endX;
    protected float endY;

    public float getEndY() {
        return endY;
    }

    public Line(float startX, float startY, float width, float endX, float endY, float[] color) {
        super(0,0,1,1,color);
        setSize(startX,startY,width,endX,endY);
    }

    public void setSize(float startX, float startY, float width, float endX, float endY){
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.endX = endX;
        this.endY = endY;
        updateSize();
    }

    public void setStart(float startX, float startY){
        this.startX=startX;
        this.startY=startY;
        updateSize();
    }

    public void setEnd(float endX, float endY){
        this.endX=endX;
        this.endY=endY;
        updateSize();
    }

    private void updateSize(){
        float centerX=(startX+endX)/2;
        float centerY=(startY+endY)/2;
        float newHeight;
        float newWidth;
        float xDiff = Math.abs(startX-endX);
        float yDiff = Math.abs(startY-endY);
        if(xDiff<yDiff){
            newHeight=yDiff;
            newWidth=width;
            centerX=startX;
        }
        else{
            newHeight=width;
            newWidth=xDiff;
            centerY=startY;
        }
        super.setSize(centerX,centerY,newWidth,newHeight);
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getWidth() {
        return width;
    }

    public float getEndX() {
        return endX;
    }
}
