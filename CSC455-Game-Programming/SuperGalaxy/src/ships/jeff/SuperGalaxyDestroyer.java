package ships.jeff;

import asteroidsfw.Vector2d;
import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.Perceptions;
import asteroidsfw.ai.ShipControl;
import asteroidsfw.ai.ShipMind;
import ships.jeff.states.AvoidProcessor;
import ships.jeff.states.InitProcessor;
import ships.jeff.states.SnipingProcessor;
import ships.jeff.states.StateProcessor;
import ships.jeff.util.Pair;

import java.util.HashMap;
import java.util.Map;


/**
 * This is the awe inspiring Super Galaxy Destroyer, Designed and manufactured
 * by the amazing and well known one man company - Jeff Space Industries.
 * It's too goooooood, HOT DAMN!!!
 */
public class SuperGalaxyDestroyer implements ShipMind {

    private ShipControl control;

    private Map<Class<? extends StateProcessor>, StateProcessor> states;

    private Pair<Double, Double> baseX;
    private Pair<Double, Double> baseY;


    @Override
    public void init(ShipControl shipControl) {
        states = new HashMap<>();
        control = shipControl;
        states.put(InitProcessor.class, new InitProcessor());
        states.put(SnipingProcessor.class, new SnipingProcessor());
        states.put(AvoidProcessor.class, new AvoidProcessor());
        baseX = new Pair<>(shipControl.pos().x(), 40.0);
        baseY = new Pair<>(shipControl.pos().y(), 40.0);
    }

    @Override
    public void think(Perceptions perceptions, double v) {
        Pair<Double, AsteroidPerception> curInfo = closestAsteroid(perceptions.asteroids(), control);
        states.get(getCurrentState(curInfo)).processMove(
                control, perceptions.asteroids(), curInfo, v
        );
        control.shooting(true);
    }

    /**
     * Method to decide which state to be in.
     *
     * @param curInfo The current close asteroid info.
     * @return The class identifying which state to be process.
     */
    private Class<? extends StateProcessor> getCurrentState(Pair<Double, AsteroidPerception> curInfo) {
        Vector2d pos = control.pos();
        Class<? extends StateProcessor> temp;
        double scaredDist = 80;
        if (control.respawnIn() > 0) {
            temp = InitProcessor.class;
        } else if (pos.x() >= baseX.key - baseX.value && pos.x() <= baseX.key + baseX.value
                && pos.y() >= baseY.key - baseY.value && pos.y() <= pos.y() + baseY.value) {
            temp = InitProcessor.class;
        } else if (curInfo.key <= scaredDist) {
            temp = AvoidProcessor.class;
        } else {
            temp = SnipingProcessor.class;
        }
        return temp;
    }

    private static Pair<Double, AsteroidPerception> closestAsteroid(AsteroidPerception[] asteroids, ShipControl control) {
        double smallest = Double.MAX_VALUE;
        AsteroidPerception closest = null;
        for (AsteroidPerception asteroid : asteroids) {
            double dst = dst(control.pos(), asteroid.pos());
            if (dst < smallest) {
                smallest = dst;
                closest = asteroid;
            }
        }
        return new Pair<>(smallest, closest);
    }

    public static double dst(Vector2d a, Vector2d b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy
        );
    }
}
