package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jeff.game.castlesmack.util.builders.BodyBuilder;

public class Cannon extends DamageAbleEntity {

    public final int playerID;

    public float maxForce;
    public float currentForce;

    public Cannon(World world, float x, float y, float width, float height, TextureRegion texture, float maxHP, float maxForce, int playerID) {
        super(world, x, y, width, height, texture, maxHP);
        this.maxForce = maxForce;
        this.playerID = playerID;
    }

    @Override
    protected Body createBody(World world, float x, float y, float width, float height) {
        return BodyBuilder.rectangleBody(world, width, height, x, y, BodyDef.BodyType.StaticBody);
    }

    public void shootProjectile(Projectile projectile) {

    }

}
