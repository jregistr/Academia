package ships.jeff.states;


import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;

/**
 * Avoiding.
 */
public class AvoidProcessor extends StateProcessor {
    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, double delta) {
        control.rotateLeft(false);
        control.rotateRight(false);
        control.thrustBackward(true);
        control.thrustForward(false);
    }
}
