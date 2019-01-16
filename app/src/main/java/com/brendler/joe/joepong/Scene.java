package com.brendler.joe.joepong;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Joe on 1/16/2018.
 */

public interface Scene {

    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
