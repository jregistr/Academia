package com.jeff.game.castlesmack.util.data;


import com.jeff.game.castlesmack.models.gameplay.Controller;
import com.jeff.game.castlesmack.models.gameplay.Player;

public class Gamer {

    public final Controller controller;
    public final Player player;
    public int timesFired = 0;

    public Gamer(Controller controller, Player player) {
        this.controller = controller;
        this.player = player;
    }
}
