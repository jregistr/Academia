package com.jeff.game.castlesmack.models.gameplay;


public abstract class Controller {

    public final Player player;

    public Controller(Player player) {
        this.player = player;
    }

    public void incrRotationPressed(boolean positive) {
    }

    public void incrForcePressed(boolean positive) {
    }

    public void firePressed(boolean positive) {
    }

}
