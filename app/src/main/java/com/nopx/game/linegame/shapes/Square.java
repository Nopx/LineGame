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



    public Square(float x, float y, float w, float h){
        super();


        color = new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

        float xOffset=w/2;
        float yOffset=h/2;

        // Create a buffer for vertex data
        this.buffer = new float[]{
                x-xOffset,y-yOffset,1f,
                x+xOffset,y-yOffset,1f,
                x+xOffset,y+yOffset,1f,
                x-xOffset,y+yOffset,1f};
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


}
