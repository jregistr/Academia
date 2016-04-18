package ships.jeff.states;


import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;
import ships.jeff.util.Pair;

import java.util.concurrent.ThreadLocalRandom;

/**
 * State for snipping those asteroids.
 */
public class SnipingProcessor extends StateProcessor {

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private final float maxTrans = 1.7f;
    private final float minTrans = 0.2f;

    private float stateLimit = 0;
    private float transLimit = 0;

    private float transCounter = 0;
    private float stateCounter = 0;

    private boolean left = true;


    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, Pair<Double, AsteroidPerception> closest, double delta) {
        if (transCounter >= transLimit) {//done transitioning
            if (stateCounter >= stateLimit) {
                left = random.nextInt(2) > 0;
                stateCounter = 0;
                transCounter = 0;
                transLimit = (random.nextFloat() * maxTrans) + minTrans;
                stateLimit = (random.nextFloat() * 3.1f) + 0.5f;
            } else {
                stateCounter += delta;
            }
            control.rotateRight(false);
            control.rotateLeft(false);
        } else {//trans
            transCounter += delta;
            if (left) {
                control.rotateLeft(true);
            } else {
                control.rotateRight(true);
            }
        }
    }
}
