package com.jeff.game.castlesmack.models;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class House extends Entity {

    public final int playerID;

    public float maxHP;
    public float currentHP;

    public House(float width, float height, TextureRegion texture, float oX, float oY, int playerID, float maxHP) {
        super(width, height, texture, oX, oY);
        this.playerID = playerID;
        this.maxHP = maxHP;
    }

    @Override
    protected void doCreateBody(World world) {

    }
}
