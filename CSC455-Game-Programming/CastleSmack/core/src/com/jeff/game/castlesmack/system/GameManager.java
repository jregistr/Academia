package com.jeff.game.castlesmack.system;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.gameplay.AI;
import com.jeff.game.castlesmack.models.gameplay.Controller;
import com.jeff.game.castlesmack.models.gameplay.Human;
import com.jeff.game.castlesmack.models.gameplay.Player;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.util.builders.GameBuilder;
import com.jeff.game.castlesmack.util.data.Pair;

public class GameManager implements Disposable {

    private enum State {
        INTURN,
        BETWEEN_TURN
    }

    private final World world;
    private final Array<Island> islands;
    private final Array<Pair<Controller, Player>> players;

    private int turnIndex = 0;

    private State state = State.BETWEEN_TURN;
    private Array<Pair<Island, Float>> targetPositions = new Array<Pair<Island, Float>>(5);
    private boolean calculatedPositions = false;

    //make a map mapping islands to a pair of direction to time. if this map is empty, we just entered the inbetween so make it.

    public GameManager(World world, boolean p1AI, ObjectMap<String, Texture> objectTextures) {
        this.world = world;

        islands = new Array<Island>(5);

        final GameBuilder builder = new GameBuilder(world, objectTextures);

        Array<Pair<Island, Island>> playerIslands = new Array<Pair<Island, Island>>(2);
        Pair<Island, Island> p1Islands = builder.makeHouseGunIslands(new Pair<Vector2, Vector2>(
                new Vector2(9, 18),
                new Vector2(21, 18)
        ));

        Pair<Island, Island> p2Islands = builder.makeHouseGunIslands(new Pair<Vector2, Vector2>(
                new Vector2(91, 18),
                new Vector2(79, 18)
        ));

        playerIslands.add(p1Islands);
        playerIslands.add(p2Islands);

        islands.addAll(p1Islands._1, p1Islands._2, p2Islands._1, p2Islands._2);

        final Array<Player> playerArray = builder.makePlayers(playerIslands);

        players = new Array<Pair<Controller, Player>>(2);

        Controller c1 = p1AI ? new Human() : new AI();
        Controller c2 = new AI();

        players.add(new Pair<Controller, Player>(c1, playerArray.first()));
        players.add(new Pair<Controller, Player>(c2, playerArray.get(1)));

    }

    public void preUpdate(Array<Pair<Object, Object>> collisions) {
        for (Pair<Object, Object> collision : collisions) {

        }
    }

    public void postUpdate(float delta) {

    }

    private void incrementTurn() {
        turnIndex += 1;
        if (turnIndex == players.size) {
            turnIndex = 0;
        }
    }

    @Override
    public void dispose() {

    }
}
