package ships.jeff;


import asteroidsfw.ai.Perceptions;
import asteroidsfw.ai.ShipControl;
import asteroidsfw.ai.ShipMind;

public class SuperGalaxyDestroyer implements ShipMind {

    private ShipControl control;

    @Override
    public void init(ShipControl shipControl) {
        this.control = shipControl;
    }

    @Override
    public void think(Perceptions perceptions, double v) {
        control.thrustForward(true);
        control.rotateLeft(true);
        control.shooting(true);
    }
}
