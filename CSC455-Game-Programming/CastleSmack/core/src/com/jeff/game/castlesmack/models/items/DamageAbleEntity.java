package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public abstract class DamageAbleEntity extends Entity {

    public float maxHP;
    public float currentHP;

    public DamageAbleEntity(World world, float x, float y, float width, float height, TextureRegion texture, float maxHP) {
        super(world, x, y, width, height, texture);
        this.maxHP = maxHP;
        currentHP = maxHP;
    }

}
