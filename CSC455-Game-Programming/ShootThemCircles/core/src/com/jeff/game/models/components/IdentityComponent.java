package com.jeff.game.models.components;

import com.badlogic.ashley.core.Component;
import com.jeff.game.models.util.Tag;

/**
 * IdentityComponent component property bag.
 */
public class IdentityComponent implements Component {

    public final Tag tag;
    public final int id;

    /**
     * Constructor.
     *
     * @param tag The tag of this component.
     * @param id  The id of this component.
     */
    public IdentityComponent(Tag tag, int id) {
        this.tag = tag;
        this.id = id;
    }
}
