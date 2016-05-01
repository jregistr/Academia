package com.jeff.game.castlesmack.models.gameplay;


import com.jeff.game.castlesmack.util.data.MoveState;
import com.jeff.game.castlesmack.util.data.TurnInfo;

public abstract class Controller {

    public boolean shoot;
    public MoveState moveState;

    public Controller() {

    }

    public final void turnStart(TurnInfo info) {
        shoot = false;
        moveState = MoveState.NEUTRAL;
        processTurn(info);
    }

    protected abstract void processTurn(TurnInfo info);

    public abstract void update();

}
