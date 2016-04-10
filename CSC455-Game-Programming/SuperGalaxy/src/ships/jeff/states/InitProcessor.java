package ships.jeff.states;


import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;

/**
 * Class to represent the "init" state. This processor simply moves out of the spawn.
 */
public class InitProcessor extends StateProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, double delta) {
        control.thrustForward(true);
    }
}
