package com.brendler.joe.joepong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Joe on 1/14/2018.
 */

public class ObstacleManager {
    // instantiate obstacles that come on at the top of the screen and go off the bottom, and keep coming
    // higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap, obstacleGap, obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;             // horizontal gap between obstacles
        this.obstacleGap = obstacleGap;         // vertical gap between obstacles
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for(Obstacle ob : obstacles) {
            if(ob.playerCollide(player)) return true;
        }
        return false;
    }

    private void populateObstacles() {
        // fill to 4/3 screen height
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while (currY < 0) {
            // (last obstacle in array has not yet gone onto the screen)
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        if(startTime < Constants.INIT_TIME) {
            startTime = Constants.INIT_TIME;
        }
        // update based on time, rather than frames, so this will be frame-rate independent
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        for (Obstacle ob : obstacles) {
            // distance = speed * time; elapsedTime is that between obstacles
            ob.incrementY(GameplayScene.speedAdjustment(startTime, initTime) * GameplayScene.speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            // obstacle is off the bottom of the screen, so add a new one at top, and delete the old one
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            // increment score for every obstacle cleared off the bottom of the screen
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
