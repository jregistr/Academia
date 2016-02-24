package com.jeff.game.models.components;

import com.badlogic.ashley.core.Component;

/**
 * TransformComponent component container bag.
 */
public final class TransformComponent implements Component {

    public float pX, pY;
    public float rotation;
    public float sX, sY;

    /**
     * Default constructor. Initialises all values to defaults.
     */
    public TransformComponent() {
        this(0, 0);
    }

    /**
     * Constructor which takes position and defaults all other values.
     *
     * @param pX
     *         The x position.
     * @param pY
     *         The y position.
     */
    public TransformComponent(float pX, float pY) {
        this(pX, pY, 0, 1, 1);
    }

    /**
     * Full constructor.
     *
     * @param pX
     *         The x position.
     * @param pY
     *         The y position.
     * @param rotation
     *         The x rotation.
     * @param sX
     *         The x scale.
     * @param sY
     *         The y scale.
     */
    public TransformComponent(float pX, float pY, float rotation, float sX, float sY) {
        this.pX = pX;
        this.pY = pY;
        this.rotation = rotation;
        this.sX = sX;
        this.sY = sY;
    }
}
