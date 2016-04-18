package ships.jeff.states;


import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;
import ships.jeff.util.Pair;

/**
 * Class to represent the "init" state. This processor simply moves out of the spawn.
 */
public class InitProcessor extends StateProcessor {


    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, Pair<Double, AsteroidPerception> closest, double delta) {
        control.thrustForward(true);
    }
}
