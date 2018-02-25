package com.nopx.game.linegame.shapes;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by root on 26.01.2018.
 */

public class Path {
    private ArrayList<Line> lines = new ArrayList<Line>();
    private float[] color = new float[]{1f,0f,0f,1f};

    public void addLine(Line line){
        line.setColor(this.color);
        lines.add(line);
    }

    public void addLine(float startX, float startY, float width, float endX, float endY){
        Log.i("line addLine",""+startX+" "+startY+" "+width+" "+endX+" "+endY);
        Line line = new Line(startX,startY,width,endX,endY,this.color);
        addLine(line);
    }

    public int getLineByCoordinates(float x, float y){
        for(int i=0;i<lines.size();i++){
            if(lines.get(i).isCoordinatesInSquare(x,y))
                return i;
        }
        return -1;
    }

    public Line removeFirstLine(){
        return lines.remove(0);
    }

    public Line removeLastLine(){
        return lines.remove(lines.size()-1);
    }

    public float[] getColor() {
        return color;
    }

    public float[] setColor(float[] newColor){
        //TODO all line colors will automatically change since they reference this variable??
        float[] colorsaver = color;
        color=newColor;
        return colorsaver;
    }

    public Line getLastLine(){
        return lines.get(lines.size()-1);
    }

    public float getStartX(){
        return lines.get(0).getStartX();
    }

    public float getStartY(){
        return lines.get(0).getStartY();
    }

    public float getEndX(){
        return lines.get(lines.size()-1).getEndX();
    }

    public float getEndY(){
        return lines.get(lines.size()-1).getEndY();
    }

    public void draw(float[] mvpMatrix){
        for(Square line:lines){
            line.draw(mvpMatrix);
        }
    }
}
