package com.jeff.game.castlesmack.models.gameplay;


import com.jeff.game.castlesmack.models.items.Cannon;
import com.jeff.game.castlesmack.models.items.House;

public class Player {

    public final House house;
    public final Cannon cannon;

    public Player(House house, Cannon cannon) {
        this.house = house;
        this.cannon = cannon;
    }
}
