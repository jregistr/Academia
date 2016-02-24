package com.jeff.game.models.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.jeff.game.util.Function;


/**
 * Class to define abstract entity system.
 */
public abstract class AbstractEntitySystem extends EntitySystem {

    protected ImmutableArray<Entity> entities;

    protected abstract Family getFamily();

    /**
     * Constructor requiring systems to have priority level.
     *
     * @param priority
     *         The priority level.
     */
    public AbstractEntitySystem(int priority) {
        super(priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update(float deltaTime);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addedToEngine(final Engine engine) {
        final Family family = getFamily();
        final Function<Engine, Void> function = new Function<Engine, Void>() {
            @Override
            public Void apply(Engine engine) {
                entities = engine.getEntitiesFor(family);
                return null;
            }
        };

        function.apply(engine);

        engine.addEntityListener(new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                function.apply(engine);
            }

            @Override
            public void entityRemoved(Entity entity) {
                function.apply(engine);
            }
        });

        addedToEngineAdditional(engine);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removedFromEngine(Engine engine) {
        setProcessing(false);
        entities = null;
        removedFromEngineAdditional(engine);
    }

    /**
     * Method called for implementations of this class that wish to take additonal actions than the
     * ones specified here.
     *
     * @param engine
     *         The engine.
     */
    protected void removedFromEngineAdditional(Engine engine) {

    }

    /**
     * Method called for implementations of this class that wish to take additonal actions than the
     * ones specified here.
     *
     * @param engine
     *         The engine.
     */
    protected void addedToEngineAdditional(Engine engine) {

    }

}
