package ships.mateusz;

import asteroidsfw.ai.*;

public class InterstellarPotato implements ShipMind {
    private ShipControl shipControl;

    @Override
    public void init(ShipControl shipControl) {
        this.shipControl = shipControl;
    }

    @Override
    public void think(Perceptions perceptions, double v) {
        double x = 0;
        double y = 0;
        double diff = Double.MAX_VALUE;

        // Loop through the asteroids and select the closest one
        for(AsteroidPerception a : perceptions.asteroids()) {
            double xDiff = a.pos().x() - shipControl.pos().x();
            double yDiff = a.pos().y() - shipControl.pos().y();

            double distance = Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2));
            if(diff > distance) {
                diff = distance;
                x = a.pos().x();
                y = a.pos().y();
            }
        }

        // Move towards the closest asteroid
        goTo(x, y);

        // Don't get too close to the asteroid
        if(diff < 80) {
            shipControl.thrustBackward(true);
        } else {
            shipControl.thrustBackward(false);
        }

        // Keep on shooting
        shipControl.shooting(true);
    }

    private void goTo(double x, double y) {
        // Get the ship position
        double sx = shipControl.pos().x();
        double sy = shipControl.pos().y();

        // Get the delta between the ship and target
        double deltaX = x - sx;
        double deltaY = y - sy;

        // Get the angle between the ship and the target
        double angle = Math.toDegrees(Math.atan2(deltaY,deltaX) - Math.atan2(shipControl.direction().y(), shipControl.direction().x()));

        // Move toward the target based on the angle between the two
        if(angle < 2 && angle > -2) {
            shipControl.rotateLeft(false);
            shipControl.rotateRight(false);
            shipControl.thrustForward(true);
        } else if(angle < 0) {
            shipControl.rotateLeft(true);
            shipControl.rotateRight(false);
            shipControl.thrustForward(false);
        } else if(angle > 0) {
            shipControl.rotateLeft(false);
            shipControl.rotateRight(true);
            shipControl.thrustForward(false);
        }
    }
}
