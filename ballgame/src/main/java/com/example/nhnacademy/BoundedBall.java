package com.example.nhnacademy;

import java.awt.Rectangle;
import java.awt.Color;

public class BoundedBall extends MovableBall implements Bounded {

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public void bounce(Regionable other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());

        if ((getRegion().getHeight() != intersection.getHeight())
                && (other.getRegion().getHeight() != intersection.getHeight())) {
            setDY(-getDY());
        }

        if ((getRegion().getWidth() != intersection.getWidth())
                && (other.getRegion().getWidth() != intersection.getWidth())) {
            setDX(-getDX());
        }

        int dx = 0;
        int dy = 0;

        // 볼 갇힘 및 끼임현상 방지
        if (intersection.getWidth() > intersection.getHeight()) {
            dy = getRegion().getMaxY() < other.getRegion().getMaxY() ? -1 : 1;
        } else {
            dx = getRegion().getMaxX() < other.getRegion().getMaxX() ? -1 : 1;
        }

        while (getRegion().intersects(other.getRegion())) {
            setX(getX() + dx);
            setY(getY() + dy);
        }

    }
}