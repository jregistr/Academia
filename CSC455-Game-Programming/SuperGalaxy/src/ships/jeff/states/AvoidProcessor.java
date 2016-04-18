package ships.jeff.states;


import asteroidsfw.Vector2d;
import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;
import ships.jeff.util.Pair;

/**
 * Avoiding asteroids.
 */
public class AvoidProcessor extends StateProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, Pair<Double, AsteroidPerception> closest, double delta) {
        Vector2d meToAsteroid = closest.value.pos().$minus(control.pos());
        double value = control.direction().cross(meToAsteroid);
        if(value < 0.0d){
            control.rotateLeft(false);
            control.rotateRight(true);
        }else {
            control.rotateLeft(true);
            control.rotateRight(false);
        }
        control.thrustBackward(false);
        control.thrustForward(true);
    }
}
