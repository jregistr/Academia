package ships.jeff.states;


import asteroidsfw.Vector2d;
import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.ShipControl;
import ships.jeff.SuperGalaxyDestroyer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * State for snipping those asteroids.
 */
public class SnipingProcessor extends StateProcessor {

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private final float maxTrans = 1.5f;
    private final float minTrans = 0.2f;

    private float stateLimit = 0;
    private float transLimit = 0;

    private float transCounter = 0;
    private float stateCounter = 0;

    private boolean left = true;

    @Override
    public void processMove(ShipControl control, AsteroidPerception[] astereroids, double delta) {
        if (transCounter >= transLimit) {//done transitioning
            if (stateCounter >= stateLimit) {
                left = random.nextInt(2) > 0;
                stateCounter = 0;
                transCounter = 0;
                transLimit = (random.nextFloat() * maxTrans) + minTrans;
                stateLimit = (random.nextFloat() * 5) + 1.1f;
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
