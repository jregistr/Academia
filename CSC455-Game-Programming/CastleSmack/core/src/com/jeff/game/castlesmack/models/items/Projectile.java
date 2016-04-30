package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jeff.game.castlesmack.util.builders.BodyBuilder;

public class Projectile extends Entity {

    public float damage;
    private float weight;

    public Projectile(World world, float x, float y, float width, float height, TextureRegion texture, float damage, float weight) {
        super(world, x, y, width, height, texture);
        this.damage = damage;
        this.weight = weight;
    }

    @Override
    protected Body createBody(World world, float x, float y, float width, float height) {
        return BodyBuilder.rectangleBody(world, width, height, x, y, BodyDef.BodyType.DynamicBody);
    }
}
