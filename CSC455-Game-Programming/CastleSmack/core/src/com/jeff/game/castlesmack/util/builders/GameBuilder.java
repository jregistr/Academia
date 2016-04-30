package com.jeff.game.castlesmack.util.builders;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.gameplay.Player;
import com.jeff.game.castlesmack.models.items.Cannon;
import com.jeff.game.castlesmack.models.items.House;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.util.constant.Constants;

public class GameBuilder {

    private final World world;
    private final ObjectMap<String, Texture> texMap;

    private final TextureRegion islandTex;
    private final TextureRegion houseTex;
    private final TextureRegion rockTex;
    private final TextureRegion cannonTex;

    public GameBuilder(World world, ObjectMap<String, Texture> texMap) {
        this.world = world;
        this.texMap = texMap;
        islandTex = new TextureRegion(texMap.get(Constants.TexConstants.ISLAND));
        houseTex = new TextureRegion(texMap.get(Constants.TexConstants.HOUSE));
        rockTex = new TextureRegion(texMap.get(Constants.TexConstants.ROCK));
        cannonTex = new TextureRegion(texMap.get(Constants.TexConstants.PIPE));
    }

    public Array<Island> makeIslands() {

        Island p1HouseIsland = new Island(world, 9, 18,
                Constants.WIDTH_PLAYER_ISLAND, Constants.HEIGHT_PLAYER_ISLAND,
                islandTex, true);

        Island p1GunIsland = new Island(world, 21, 18,
                Constants.WIDTH_PLAYER_GUN_ISLAND, Constants.HEIGHT_PLAYER_GUN_ISLAND,
                islandTex, true);

        Island p2HouseIsland = new Island(world, 91, 18,
                Constants.WIDTH_PLAYER_ISLAND, Constants.HEIGHT_PLAYER_ISLAND,
                islandTex, true);

        Island p2GunIsland = new Island(world, 79, 18,
                Constants.WIDTH_PLAYER_GUN_ISLAND, Constants.HEIGHT_PLAYER_GUN_ISLAND,
                islandTex, true);

        Island i1 = new Island(world, 39.6f, 18, 8, 2.5f, islandTex, true);
        Island i2 = new Island(world, 59, 18, 8, 3.5f, islandTex, true);

        Array<Island> islands = new Array<Island>(6);

        islands.addAll(p1HouseIsland, p1GunIsland, p2HouseIsland, p2GunIsland, i1, i2);
        return islands;
    }

    public Array<Player> makePlayers(boolean p1AI, int additionalPlayers) {
        final Array<Player> players = new Array<Player>(1 + additionalPlayers);

        int pID = 0;

        final House p1House = makeHouse(9f, 18.1f, pID);
        final Cannon p1Cannon = makeCannon(21, 18.1f, pID);

        players.add(new Player(p1House, p1Cannon));

        //remove loop, just hard code 2 players or find way to put houses on proper islands
        //perhaps use map of id to pairs of house and gun islands? Simply assign the player to one given as param. The rest will be random.
        for (; pID <= (players.size - 1); pID++) {

        }

        return players;
    }

    private House makeHouse(float x, float y, int playerID) {
        return new House(world, x, y,
                Constants.WIDTH_PLAYER_HOUSE,
                Constants.HEIGHT_PLAYER_HOUSE, houseTex,
                Constants.PLAYER_HOUSE_MAX_HP_START, playerID);
    }

    private Cannon makeCannon(float x, float y, int playerID){
        return new Cannon(world, x, y,
                Constants.WIDTH_PLAYER_GUN,
                Constants.HEIGHT_PLAYER_GUN,
                cannonTex,
                Constants.PLAYER_GUN_MAX_HP_START,
                Constants.PLAYER_GUN_MAX_FORCE_START,
                playerID);
    }

}
