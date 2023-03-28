package com.paopaolee.monitor.bean;

import java.awt.*;

/**
 * @author paopaolee
 */
public class OcrRectangle extends Rectangle {

    private int x;

    private int y;

    private int width;

    private int height;

    public OcrRectangle() {
        super();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
        setRect(x, getY(), getWidth(), getHeight());
    }

    public void setY(int y) {
        this.y = y;
        setRect(getX(), y, getWidth(), getHeight());
    }

    public void setWidth(int width) {
        this.width = width;
        setRect(getX(), getY(), width, getHeight());
    }

    public void setHeight(int height) {
        this.height = height;
        setRect(getX(), getY(), getWidth(), height);
    }
}
