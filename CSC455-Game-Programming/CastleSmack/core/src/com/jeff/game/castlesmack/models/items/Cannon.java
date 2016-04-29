package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Cannon extends Entity {

    public float currentHP;
    public float maxHP;

    public float maxForce;
    public float currentForce;

    public Cannon(World world, float x, float y, float width, float height, TextureRegion texture, float maxHP, float maxForce) {
        super(world, x, y, width, height, texture);
        this.maxHP = maxHP;
        this.maxForce = maxForce;
    }

    @Override
    protected Body createBody(World world, float x, float y, float width, float height) {
        return null;
    }

    public void shootProjectile(Projectile projectile) {

    }

}
