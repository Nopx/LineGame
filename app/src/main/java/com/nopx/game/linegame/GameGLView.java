package com.nopx.game.linegame;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Jolly94 on 13.12.2017.
 */

public class GameGLView extends GLSurfaceView {

    private final GameGLRenderer gameRenderer;

    public GameGLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        gameRenderer = new GameGLRenderer();
        setRenderer(gameRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
