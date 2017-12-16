package com.nopx.game.linegame.shapes;

import android.opengl.GLES20;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Jolly94 on 14.12.2017.
 */

public class Circle implements Shape {

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    float[] vertexBuffer;
    int vertexCount;

    public Circle(int vertexCount){
        this.vertexCount=vertexCount;
        float radius = 1.0f;
        float center_x = 0.0f;
        float center_y = 0.0f;
        float center_z = 0.0f;

        // Create a buffer for vertex data
        float buffer[] = new float[vertexCount*COORDS_PER_VERTEX]; // (x,y) for each vertex
        int idx = 0;

        // Center vertex for triangle fan
        buffer[idx++] = center_x;
        buffer[idx++] = center_y;
        buffer[idx++] = center_z;

        // Outer vertices of the circle
        int outerVertexCount = vertexCount-1;

        for (int i = 0; i < outerVertexCount; ++i){
            float percent = (i / (float) (outerVertexCount-1));
            float rad = (float)(percent * 2*Math.PI);

            //Vertex position
            float outer_x = (float)(center_x + radius * cos(rad));
            float outer_y = (float)(center_y + radius * sin(rad));
            float outer_z = 0;

            buffer[idx++] = outer_x;
            buffer[idx++] = outer_y;
            buffer[idx++] = outer_z;
        }
        vertexBuffer=buffer;
    }

    public void draw(int program){
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        //TODO make vertexbuffer an INT array
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        int colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(colorHandle);
    }
}
