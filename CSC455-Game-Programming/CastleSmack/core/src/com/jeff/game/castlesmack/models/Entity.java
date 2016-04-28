package com.jeff.game.castlesmack.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

    public float x;
    public float y;
    public float rotation;

    public final float width;
    public final float height;
    public final TextureRegion texture;

    private final float oX;
    private final float oY;

    private boolean created = false;

    public Entity(float width, float height, TextureRegion texture, float oX, float oY) {
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.oX = oX;
        this.oY = oY;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, oX, oY, width, height, 1, 1, rotation);
    }

    public void createAndAddBody(World world){
        if(!created){
            doCreateBody(world);
            created = true;
        }else {
            throw new IllegalStateException("Already Created");
        }
    }

    protected abstract void doCreateBody(World world);

}
