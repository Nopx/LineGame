package com.nopx.game.linegame.shapes;

import android.util.Log;

/**
 * Created by root on 24.12.2017.
 */

public class Grid extends Shape {

    //make one background square and many front squares

    private Square bg;
    private Square[] gridOverlay;
    private Cell[] cells;
    private float borderX;
    private float borderY;
    private float cellWidth;
    private float separationFactor=50;
    private float xBound;
    private float yBound;
    private float width;
    private float height;
    private float[] color;
    private float[] highlightedColor;
    private Cell lastHoveredCell;
    private int cellsX;
    private int cellsY;
    private float centerX;
    private float centerY;
    private float xBottomLeft;
    private float yBottomLeft;
    private float yTopRight;
    private float xTopRight;

    public Grid(float xBottomLeft, float yBottomLeft, float xTopRight, float yTopRight,
                float z, int cellsX, int cellsY, float borderWidth, float[] bgColor, float[] color){
        super();
        this.xBottomLeft=xBottomLeft;
        this.yBottomLeft=yBottomLeft;
        this.xTopRight=xTopRight;
        this.yTopRight=yTopRight;
        this.color=color;
        this.cellsX=cellsX;
        this.cellsY=cellsY;
        highlightedColor = new float[color.length];

        for(int i=0;i<3;i++){
            highlightedColor[i]=color[i]*0.5f;
            if(highlightedColor[i]>1f) highlightedColor[i]=1f;
        }
        centerX=(xBottomLeft+xTopRight)/2;
        centerY=(yBottomLeft+yTopRight)/2;
        width=Math.abs(xBottomLeft-xTopRight);
        height = Math.abs(yBottomLeft-yTopRight);
        bg = new Square(centerX,centerY,width,height,bgColor);

        cells=new Cell[cellsX*cellsY];
        cellWidth = Math.min((width-2*borderWidth)/cellsX,(height-2*borderWidth)/cellsY);
        float separation = cellWidth/separationFactor;
        borderX = (width-(cellsX*cellWidth)-(cellsX-1)*separation)/2;
        borderY = (height-(cellsY*cellWidth)-(cellsY-1)*separation)/2;

        xBound=width/2;
        yBound=height/2;

        for(int y=0; y<cellsY;y++){
            for(int x=0;x<cellsX;x++){
                //TODO fix start position
                centerX=xTopRight+borderX+x*cellWidth+(x-1)*separation+cellWidth/2;
                centerY=yBottomLeft+borderY+y*cellWidth+(y-1)*separation+cellWidth/2;
                cells[y*cellsX+x]=new Cell(centerX,centerY,cellWidth,cellWidth,color,highlightedColor);
                cells[y*cellsX+x].setGridx(x);
                cells[y*cellsX+x].setGridY(y);
            }
        }

        gridOverlay=new Square[cellsX+cellsY-2];
        float overlayWidth=separation;
        float cellsTogetherHeight =(cellWidth*cellsY+(cellsY-1)*separation+borderY);
        float cellsTogetherWidth = (cellWidth*cellsX+(cellsX-1)*separation+borderX);
        float centerYOverlayVertical=cellsTogetherHeight/2-height/2;
        float centerXOverlayHorizontal=cellsTogetherWidth/2-width/2;
        for(int i=0;i<cellsX-1;i++){
            float centerXOverlayVertical=borderX+(i+1)*cellWidth-width/2;
            gridOverlay[i]=new Square(centerXOverlayVertical,centerYOverlayVertical,separation,cellsTogetherHeight,color);
        }
        for(int i=cellsX-1;i<cellsX+cellsY-2;i++){
            float centerYOverlayHorizontal=borderY+(i+1-cellsX)*cellWidth-height/2;
            gridOverlay[i]=new Square(centerXOverlayHorizontal,centerYOverlayHorizontal,cellsTogetherWidth,separation,color);
        }
        lastHoveredCell=cells[(cellsY/2)*cellsX+(cellsX/2)];
        cells[(cellsY/2)*cellsX+(cellsX/2)].highlight();
    }

    @Override
    public void draw(float[] mvpMatrix){
        bg.draw(mvpMatrix);
        for(Square cell:cells){
            cell.draw(mvpMatrix);
        }/*
        for(Square overlay:gridOverlay){
            overlay.draw(mvpMatrix);
        }*/
    }

    public void highlightNewCell(float x, float y){
        try {
            if (isOnGrid(x, y)) {
                Cell cell = coordToCell(x, y);
                if (cell!=null && lastHoveredCell != cell) {
                    //Log.i("OHNOO!",""+cell.getGridx()+", "+cell.getGridY()+" -- "+lastHoveredCell.getGridx()+", "+lastHoveredCell.getGridY());
                    if(((Math.abs(cell.getGridx()-lastHoveredCell.getGridx())<=1.0)
                            && (Math.abs(cell.getGridY()-lastHoveredCell.getGridY())<=1.0))
                            && ((Math.abs(cell.getGridx()-lastHoveredCell.getGridx())==1.0)
                            ^  (Math.abs(cell.getGridY()-lastHoveredCell.getGridY())==1.0))) {
                        //TODO make highlighting reversable
                        cell.toggleHighlight();
                        lastHoveredCell = cell;
                    }
                }
            }
        }
        catch(Exception e){
            Log.e("ERROR","exception",e);
        }
    }

    public boolean isOnGrid(float x, float y){

        Log.i("BORDERS","borderxy: "+xTopRight+", "+yTopRight+", "+xBottomLeft+", "+yBottomLeft+", "+x+", "+y);
        return x>xTopRight&&x<xBottomLeft&&y<yTopRight&&y>yBottomLeft;
    }

    private Cell coordToCell(float x, float y){
        float calcXF = x<0?x+width+Math.abs(borderX):x;
        float calcYF = y<0?y+height+Math.abs(borderY):y;
        int calcX=(int)Math.floor((calcXF-borderX)/cellWidth);
        int calcY=(int)Math.floor((calcYF-borderY)/cellWidth);
        //return cells[0];
        return cells[calcY*cellsX+calcX];
    }

}
