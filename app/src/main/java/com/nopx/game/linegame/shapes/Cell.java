package com.nopx.game.linegame.shapes;

/**
 * Created by root on 24.12.2017.
 */

public class Cell extends Square {

    boolean highlighted=false;
    float[] highlightColor;
    float[] unhighlightedColor;
    int gridx;
    int gridY;

    public Cell(float x, float y, float w, float h, float[] color, float[] highlightColor){
        super( x, y, w, h, color);
        this.highlightColor=highlightColor;
        this.unhighlightedColor=color;
    }

    public boolean isHighlighted(){
        return this.highlighted;
    }

    public void toggleHighlight(){
        if(isHighlighted())
            unhighlight();
        else
            highlight();
    }

    public void unhighlight(){
        this.color=this.unhighlightedColor;
        setSize(centerX,centerY,width,height);
        this.highlighted=false;
    }

    public void highlight(){
        this.color=this.highlightColor;
        setSize(centerX,centerY,width*0.95f,height*0.95f);
        this.highlighted=true;
    }


    public int getGridx() {
        return gridx;
    }

    public void setGridx(int gridx) {
        this.gridx = gridx;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

}
