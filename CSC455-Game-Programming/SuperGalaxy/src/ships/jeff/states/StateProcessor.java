package ships.jeff.states;


import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;

/**
 * Abstract class to represent a state processor.
 */
public abstract class StateProcessor {

    /**
     * Process the move for this state.
     *
     * @param control     The ship control.
     * @param astereroids The asteroid perceptions.
     * @param delta       Delta.
     */
    public abstract void processMove(ShipControl control, AsteroidPerception[] astereroids, double delta);

}
