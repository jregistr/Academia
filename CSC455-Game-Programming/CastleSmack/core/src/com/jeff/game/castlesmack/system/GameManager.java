package com.jeff.game.castlesmack.system;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.gameplay.AI;
import com.jeff.game.castlesmack.models.gameplay.Controller;
import com.jeff.game.castlesmack.models.gameplay.Human;
import com.jeff.game.castlesmack.models.gameplay.Player;
import com.jeff.game.castlesmack.models.items.House;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.models.items.Projectile;
import com.jeff.game.castlesmack.util.builders.GameBuilder;
import com.jeff.game.castlesmack.util.data.*;

import java.util.Random;

public class GameManager implements Disposable {

    private enum State {
        INTURN,
        BETWEEN_TURN
    }


    private final World world;
    private final Array<Island> otherIslands;
    //    private final Array<Pair<Controller, Player>> players;
    private final Array<Gamer> players;

    private int lastTurnIndex = -1;
    private int turnIndex = 0;
    public int playerWinId = -1;

    private State state = State.BETWEEN_TURN;
    private Array<Pair<Island, Float>> targetPositions = new Array<Pair<Island, Float>>(5);
    private boolean calculatedPositions = false;
    private Array<Pair<Object, Object>> collisions = new Array<Pair<Object, Object>>(3);
    private Array<Projectile> inactiveProjectiles = new Array<Projectile>(2);
    private Random random = new Random();

    private static final float MOVE_DOWN_SPEED = 5;
    private static final float CANNON_ROTATE_SPEED = 20;//20 DEGREES PER SECOND

    //make a map mapping otherIslands to a pair of direction to time. if this map is empty, we just entered the inbetween so make it.

    public GameManager(World world, boolean p1AI, ObjectMap<String, Texture> objectTextures) {
        this.world = world;

        otherIslands = new Array<Island>(5);

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

        otherIslands.addAll();

        final Array<Player> playerArray = builder.makePlayers(playerIslands);

        players = new Array<Gamer>();

        Controller c1 = p1AI ? new Human() : new AI();
        Controller c2 = new AI();

        players.add(new Gamer(c1, playerArray.first()));
        players.add(new Gamer(c2, playerArray.get(1)));

    }

    public void runCollisions() {

        if (collisions.size == 0)
            return;

        for (Pair<Object, Object> collision : collisions) {
            Object raw1 = collision._1;
            Object raw2 = collision._2;
            if ((raw1 instanceof Island && raw2 instanceof Projectile) || raw1 instanceof Projectile && raw2 instanceof Island) {
                Projectile projectile = (Projectile) (raw1 instanceof Projectile ? raw1 : raw2);
                projectile.body.setActive(false);
                inactiveProjectiles.add(projectile);
            } else if ((raw1 instanceof House && raw2 instanceof Projectile) || (raw1 instanceof Projectile && raw2 instanceof House)) {
                Projectile projectile;
                House house;
                if (raw1 instanceof House) {
                    house = (House) raw1;
                    projectile = (Projectile) raw2;
                } else {
                    house = (House) raw2;
                    projectile = (Projectile) raw1;
                }

                house.currentHP -= projectile.damage;
                projectile.body.setActive(false);
                inactiveProjectiles.add(projectile);

                if (house.currentHP <= 0) {
                    playerWinId = house.playerID == players.first().player.id ?
                            players.get(1).player.id :
                            players.first().player.id;
                }

            }
        }
    }

    public void update(float delta) {
        if (state == State.BETWEEN_TURN) {
            if (!calculatedPositions) {
                for (Island island : otherIslands) {
                    targetPositions.add(new Pair<Island, Float>(island, (float) random.nextInt(50)));
                }

                for (Gamer gamer : players) {
                    Player player = gamer.player;
                    targetPositions.add(new Pair<Island, Float>(player.houseIsland, (float) random.nextInt(50)));
                    targetPositions.add(new Pair<Island, Float>(player.cannonIsland, (float) random.nextInt(50)));
                }

                calculatedPositions = true;
            }

            float add = MOVE_DOWN_SPEED * delta;

            if (targetPositions.size > 0) {
                for (Pair<Island, Float> targetPosition : targetPositions) {
                    Vector2 curPos = targetPosition._1.body.getPosition();

                    //TODO move it to somewhere around the position.

                }
            } else {
                //go into in turn state
                state = State.INTURN;
            }

        } else {
            if (playerWinId == -1) {
                Gamer currentTurnGamer = players.get(turnIndex);
                Controller controller = currentTurnGamer.controller;
                Player player = currentTurnGamer.player;

                if (currentTurnGamer.timesFired > 0) {
                    incrementTurn();
                } else if (lastTurnIndex != turnIndex) {//turn start
                    PlayerInfo yours = makePlayerInfo(player);
                    Array<Vector2> oislands = new Array<Vector2>(3);
                    for (Island island : otherIslands) {
                        oislands.add(island.body.getPosition());
                    }

                    Array<PlayerInfo> otherPlayerInfos = new Array<PlayerInfo>(3);
                    for (Gamer gamer : players) {
                        Player p = gamer.player;
                        if (p != player) {
                            otherPlayerInfos.add(makePlayerInfo(p));
                        }
                    }

                    TurnInfo turnInfo = new TurnInfo();
                    turnInfo.yours = yours;
                    turnInfo.otherPlayers = otherPlayerInfos;
                    turnInfo.otherIslandPos = oislands;

                    controller.turnStart(turnInfo);
                    lastTurnIndex = turnIndex;
                } else {

                    float angle = player.cannon.body.getAngle() * MathUtils.radDeg;

                    //TODO constrain angle between 0 and 180. :)
                    if (angle > 0 && angle < 180) {
                        if (controller.cannonMoveState == MoveState.POSITIVE) {
                            //TODO Rotate cannon by rotation speed
                        } else if (controller.cannonMoveState == MoveState.NEGATIVE) {
                            //TODO same as above except the other way.
                        }
                    }


                }
            }
        }
    }

    private PlayerInfo makePlayerInfo(Player player) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.cannonHp = player.cannon.currentHP;
        playerInfo.cannonPos = player.cannon.body.getPosition();
        playerInfo.playerId = player.id;
        playerInfo.houseHp = player.house.currentHP;
        playerInfo.housePos = player.house.body.getPosition();
        return playerInfo;
    }

    private void incrementTurn() {
        turnIndex += 1;
        if (turnIndex == players.size) {
            turnIndex = 0;
        }

        for (Gamer gamer : players) {
            gamer.timesFired = 0;
        }
        calculatedPositions = false;
        state = State.BETWEEN_TURN;
    }

    @Override
    public void dispose() {

    }
}
