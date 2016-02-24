package com.jeff.game.models.components;

import com.badlogic.ashley.utils.ImmutableArray;
import com.jeff.game.models.util.Tag;

/**
 * Circle component container bag.
 */
public final class CircleColliderComponent extends ColliderComponent {

    public float radius;

    /**
     * Simple constructor that takes parameter(s) and defaults all other fields.
     *
     * @param radius
     *         The radius of the collider.
     */
    public CircleColliderComponent(float radius, ImmutableArray<Tag> collideAbleTags) {
        this(0, 0, radius, collideAbleTags);
    }

    /**
     * Constructor.
     *
     * @param x
     *         The x offset.
     * @param y
     *         The y offset.
     * @param radius
     *         The radius of the collider.
     */
    public CircleColliderComponent(float x, float y, float radius, ImmutableArray<Tag> collideAbleTags) {
        super(collideAbleTags);
        this.radius = radius;
        this.centerX = x;
        this.centerY = y;
    }

}
