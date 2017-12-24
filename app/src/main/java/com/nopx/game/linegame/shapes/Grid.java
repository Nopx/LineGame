package com.nopx.game.linegame.shapes;

import android.util.Log;

/**
 * Created by root on 24.12.2017.
 */

public class Grid extends Shape {

    //make one background square and many front squares

    private Square bg;
    private Cell[] cells;
    private float borderX;
    private float borderY;
    private float cellWidth;
    private float separationFactor=100;
    private float xBound;
    private float yBound;
    private float width;
    private float height;
    private float[] color;
    private float[] highlightedColor;
    private Cell lastHoveredCell;
    private int cellsX;
    private int cellsY;

    public Grid(float xBottomLeft, float yBottomLeft, float xTopRight, float yTopRight,
                float z, int cellsX, int cellsY, float borderWidth, float[] bgColor, float[] color){
        super();
        this.color=color;
        this.cellsX=cellsX;
        this.cellsY=cellsY;
        highlightedColor = new float[color.length];

        for(int i=0;i<3;i++){
            highlightedColor[i]=color[i]*1.5f;
            if(highlightedColor[i]>1f) highlightedColor[i]=1f;
        }
        float centerX=(xBottomLeft+xTopRight)/2;
        float centerY=(yBottomLeft+yTopRight)/2;
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

        for(int x=0;x<cellsX;x++){
            for(int y=0; y<cellsY;y++){
                centerX=borderX+x*cellWidth+(x-1)*separation+cellWidth/2-width/2;
                centerY=borderY+y*cellWidth+(y-1)*separation+cellWidth/2-height/2;
                cells[y*cellsX+x]=new Cell(centerX,centerY,cellWidth,cellWidth,color,highlightedColor);
                cells[y*cellsX+x].setGridx(x);
                cells[y*cellsX+x].setGridY(y);
            }
        }
        lastHoveredCell=cells[0];
    }

    @Override
    public void draw(float[] mvpMatrix){
        bg.draw(mvpMatrix);
        for(Square cell:cells){
            cell.draw(mvpMatrix);
        }
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
        return x<xBound&&x>-xBound&&y<yBound&&y>-yBound;
    }

    private Cell coordToCell(float x, float y){
        int calcX=(int)Math.floor((x+xBound-borderX)/cellWidth);
        int calcY=(int)Math.floor((y+yBound-borderY)/cellWidth);
        return cells[calcY*cellsX+calcX];
    }

}
