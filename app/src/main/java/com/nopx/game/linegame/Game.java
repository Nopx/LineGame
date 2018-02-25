package com.nopx.game.linegame;

import android.util.Log;

import com.nopx.game.linegame.shapes.ActorPair;
import com.nopx.game.linegame.shapes.Diamond;
import com.nopx.game.linegame.shapes.Grid;
import com.nopx.game.linegame.shapes.Line;
import com.nopx.game.linegame.shapes.Path;
import com.nopx.game.linegame.shapes.Square;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 29.01.2018.
 */

public class Game {

    private float[][] colors;
    private float actorWidth=0.2f;
    private ArrayList<ActorPair> currentLevel;
    private Path[] paths;
    private HashMap<String,ActorPair> coordToActorPairMap;
    private HashMap<String,Integer> coordToActorPairIndexMap;
    private int actorTouched=-1;
    private int gridSizeX=0;
    private int gridSizeY=0;

    private float lineWidth = 0.2f;

    private Grid gameGrid;
    private GameGLRenderer renderer;
    private float[] boundVec;


    public Game(GameGLRenderer renderer, String colorsString){
        this.renderer=renderer;
        this.colors=convertColorString(colorsString);
        boundVec=renderer.vecToWorld(new float[]{1,1,0,1});
        gameGrid= new Grid(-boundVec[0],boundVec[1],boundVec[0],-boundVec[1],0.1f,3,
                5,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });
    }

    public void setNewLevel(String level){
        String cleanLevel= cleanString(level);
        //TODO outsource the semicolon
        Log.i("LEVEL",cleanLevel);
        String[] pairs=cleanLevel.split(";");
        currentLevel=new ArrayList<ActorPair>();
        coordToActorPairMap=new HashMap<>();
        coordToActorPairIndexMap=new HashMap<>();
        String[] gridDimensions=pairs[0].split(",");
        this.gridSizeX=Integer.parseInt(gridDimensions[0]);
        this.gridSizeY=Integer.parseInt(gridDimensions[1]);
        boundVec=renderer.vecToWorld(new float[]{1,1,0,1});
        gameGrid= new Grid(-boundVec[0],boundVec[1],boundVec[0],-boundVec[1],0.1f,gridSizeX,
                gridSizeY,0.05f,new float[]{ 0f, 0f, 0f, 1.0f },
                new float[]{ 1f, 1f, 1f, 1.0f });

        for(int i=1;i<pairs.length;i++){
            //Parsing Level String
            String[] pairCoords = pairs[i].split(",");
            int[] coordVals = new int[pairCoords.length];
            for(int j=0;j<pairCoords.length;j++){
                coordVals[j]=Integer.parseInt(pairCoords[j]);
            }
            //transforming to game coordinates from cell coordinates
            //TODO save cell coordinates for later reference
            float[] actor1Coords=gameGrid.cellCoordToCoord(coordVals[0],coordVals[1]);

            float[] actor2Coords=gameGrid.cellCoordToCoord(coordVals[2],coordVals[3]);
            Diamond actor1=new Diamond(actor1Coords[0],actor1Coords[1],actorWidth,actorWidth,colors[i]);
            Diamond actor2=new Diamond(actor2Coords[0],actor2Coords[1],actorWidth,actorWidth,colors[i]);
            ActorPair actorPair = new ActorPair(actor1,actor2);
            currentLevel.add(actorPair);
            coordToActorPairMap.put("x"+coordVals[0]+"y"+coordVals[1],actorPair);
            coordToActorPairMap.put("x"+coordVals[2]+"y"+coordVals[3],actorPair);
            coordToActorPairIndexMap.put("x"+coordVals[0]+"y"+coordVals[1],i-1);
            coordToActorPairIndexMap.put("x"+coordVals[2]+"y"+coordVals[3],i-1);
        }
        paths = new Path[currentLevel.size()];
    }

    public void onTouch(float xTouch, float yTouch){
        int[] cellCoords = gameGrid.coordsToCellCoord(xTouch,yTouch);
        ActorPair actorPair = coordToActorPairMap.get("x"+cellCoords[0]+"y"+cellCoords[1]);
        if(actorPair!=null){
            actorTouched = coordToActorPairIndexMap.get("x"+cellCoords[0]+"y"+cellCoords[1]);
        }
    }

    public void whileTouch(float xTouch, float yTouch){
        int[] cellCoords = gameGrid.coordsToCellCoord(xTouch,yTouch);
        if(actorTouched!=-1){
            ActorPair actorPair = currentLevel.get(actorTouched);
            if(paths[actorTouched]==null){
                float[] actualCoords = gameGrid.cellCoordToCoord(cellCoords[0],cellCoords[1]);
                Path path=new Path();
                path.setColor(actorPair.getActor1().getColor());
                path.addLine(actualCoords[0],actualCoords[1],0.3f,xTouch,yTouch);
                paths[actorTouched]=path;
            }
            Line lineTouched = paths[actorTouched].getLastLine();
            lineTouched.setEnd(xTouch,yTouch);
        }

    }

    public void onRelease(float xTouch, float yTouch){
        int[] cellCoords = gameGrid.coordsToCellCoord(xTouch,yTouch);
        actorTouched=-1;
    }

    public void draw(float[] mvpMatrix){
        gameGrid.draw(mvpMatrix);
        for(ActorPair pair: currentLevel){
            pair.draw(mvpMatrix);
        }
        for(Path path:paths){
            if(path!=null)
                path.draw(mvpMatrix);
        }
    }

    private float[][] convertColorString(String colorString){
        //Clean String only allowed numbers and ; and ,
        String cleanString = colorString.replaceAll("[^0-9,;]","");
        String[] colorStrings = cleanString.split(";");
        String[][] colorStringsSplit=new String[colorStrings.length][4];
        float[][] colorVals=new float[colorStrings.length][4];
        for(int i=0;i<colorStrings.length;i++){
            String[] rgbStrings=colorStrings[i].split(",");
            float[] rgbVals=new float[rgbStrings.length];
            for(int j=0;j<rgbVals.length;j++){
                colorVals[i][j]=Float.parseFloat(rgbStrings[j])/255;
            }
        }
        return colorVals;
    }

    private String cleanString(String dirtyString){
        return dirtyString.replaceAll("[^0-9,;]","");
    }

}
