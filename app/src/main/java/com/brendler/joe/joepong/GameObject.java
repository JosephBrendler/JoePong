package com.brendler.joe.joepong;

import android.graphics.Canvas;

/**
 * Created by Joe on 1/14/2018.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
