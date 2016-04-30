package com.jeff.game.castlesmack.util.builders;


import com.badlogic.gdx.physics.box2d.*;

/**
 * Class to build certain common bodies.
 */
public class BodyBuilder {

    /**
     * Generates a rectangle with the given parameters.
     *
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     * @return A new shape.
     */
    public static Shape rectangleShape(float w, float h) {
        PolygonShape shape = new PolygonShape();
        float w2 = w / 2.0f;
        float h2 = h / 2.0f;
        shape.set(new float[]
                {
                        -w2, h2,
                        -w2, -h2,
                        w2, h2,
                        w2, -h2
                });
        return shape;
    }

    /**
     * Constructors a rectangular body with the given parameters.
     *
     * @param world The world to create the body.
     * @param w     The width of the body.
     * @param h     The height of the body.
     * @param x     The x position for the body.
     * @param y     The y position for the body.
     * @param type  The type of body to create.
     * @return A new body constructed based on given parameters.
     */
    public static Body rectangleBody(World world, float w, float h, float x, float y, BodyDef.BodyType type) {
        FixtureDef def = new FixtureDef();
        Shape shape = rectangleShape(w, h);
        def.shape = shape;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);
        body.createFixture(def);
        shape.dispose();
        return body;
    }

}
