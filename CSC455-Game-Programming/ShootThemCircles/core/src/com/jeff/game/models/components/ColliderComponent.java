package com.jeff.game.models.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.jeff.game.models.util.Tag;

/**
 * Class to define abstract collide-able component.
 */
public abstract class ColliderComponent implements Component {

    public float centerX, centerY;
    public final ImmutableArray<Tag> collideAbleTags;
    public boolean collided = false;
    public boolean enabled = true;

    public ColliderComponent(ImmutableArray<Tag> collideAbleTags) {
        this.collideAbleTags = collideAbleTags;
    }
}
