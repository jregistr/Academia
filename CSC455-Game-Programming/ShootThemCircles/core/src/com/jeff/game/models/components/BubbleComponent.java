package com.jeff.game.models.components;


import com.badlogic.ashley.core.Component;

public class BubbleComponent implements Component{

    public final float radiusToCenter, width, height;

    public int firstType;
    public int secondType;
    public float rotation;

    public BubbleComponent(float radiusToCenter, float width, float height, float rotation, int firstType, int secondType) {
        this.radiusToCenter = radiusToCenter;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.firstType = firstType;
        this.secondType = secondType;
    }
}
