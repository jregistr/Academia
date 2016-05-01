package com.jeff.game.castlesmack.util.data;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TurnInfo {

    public int health;
    public Vector2 housePos;
    public Vector2 gunPos;

    public Array<PlayerInfo> otherPlayers;

    public Array<IslandInfo> otherIslands;

}
