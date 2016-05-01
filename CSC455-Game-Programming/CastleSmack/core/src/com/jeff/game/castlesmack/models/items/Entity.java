package com.jeff.game.castlesmack.models.items;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.jeff.game.castlesmack.util.constant.Constants.meterToPix;

public abstract class Entity {

    public float x;
    public float y;
    public float rotation;

    private float oX;
    private float oY;

    public final TextureRegion texture;
    public final Body body;

    private final float pixWidth;
    private final float pixHeight;

    public Entity(World world, float x, float y, float width, float height, TextureRegion texture) {
        this.texture = texture;
        pixWidth = meterToPix(width);
        pixHeight = meterToPix(height);
        oX = width / 2.0f;
        oY = height / 2.0f;
        body = createBody(world, x, y, width, height);
        body.setUserData(this);
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
    }

    protected abstract Body createBody(World world, float x, float y, float width, float height);

    public void draw(Batch batch) {
        batch.draw(texture, meterToPix(x), meterToPix(y), oX, oY, pixWidth, pixHeight, 1, 1, rotation);
    }

}
