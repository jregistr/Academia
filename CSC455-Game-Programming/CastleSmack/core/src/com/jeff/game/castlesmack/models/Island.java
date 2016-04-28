package com.jeff.game.castlesmack.models;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Island extends Entity {

    public final boolean relocates;

    public Island(float width, float height, TextureRegion texture, float oX, float oY, boolean relocates) {
        super(width, height, texture, oX, oY);
        this.relocates = relocates;
    }


    @Override
    protected void doCreateBody(World world) {

    }
}
