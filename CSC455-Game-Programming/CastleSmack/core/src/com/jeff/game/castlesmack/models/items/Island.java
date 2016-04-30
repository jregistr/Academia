package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.jeff.game.castlesmack.util.builders.BodyBuilder;

public class Island extends Entity {

    public final boolean relocates;

    public Island(World world, float x, float y, float width, float height, TextureRegion texture, boolean relocates) {
        super(world, x, y, width, height, texture);
        this.relocates = relocates;
    }

    @Override
    protected Body createBody(World world, float x, float y, float width, float height) {
        return BodyBuilder.rectangleBody(world, width, height, x, y, BodyDef.BodyType.StaticBody);
    }

}
