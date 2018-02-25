package com.nopx.game.linegame.shapes;

/**
 * Created by root on 26.01.2018.
 */

public class ActorPair {

    public Square getActor1() {
        return actor1;
    }

    public Square getActor2() {
        return actor2;
    }

    private Square actor1;
    private Square actor2;

    public ActorPair(Square actor1, Square actor2){
        this.actor1=actor1;
        this.actor2=actor2;
    }

    public void draw(float[] mvpMatrix){
        actor1.draw(mvpMatrix);
        actor2.draw(mvpMatrix);
    }
}
