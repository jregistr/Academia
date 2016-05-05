package com.jeff.game.castlesmack.util.builders;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.gameplay.Player;
import com.jeff.game.castlesmack.models.items.Cannon;
import com.jeff.game.castlesmack.models.items.House;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.util.constant.Constants;
import com.jeff.game.castlesmack.util.data.Pair;

public class GameBuilder {

    private final World world;

    private final TextureRegion islandTex;
    private final TextureRegion houseTex;
    private final TextureRegion cannonTex;

    public GameBuilder(World world, ObjectMap<String, Texture> texMap) {
        this.world = world;
        islandTex = new TextureRegion(texMap.get(Constants.TexConstants.ISLAND));
        houseTex = new TextureRegion(texMap.get(Constants.TexConstants.HOUSE));
        cannonTex = new TextureRegion(texMap.get(Constants.TexConstants.PIPE));
    }

    public Pair<Island, Island> makeHouseGunIslands(Pair<Vector2, Vector2> housePosToGunPos) {
        Vector2 hp = housePosToGunPos._1;
        Vector2 ip = housePosToGunPos._2;

        Island houseIsland = makeIsland(hp.x, hp.y,
                Constants.WIDTH_PLAYER_ISLAND,
                Constants.HEIGHT_PLAYER_ISLAND, true);

        Island gunIsland = makeIsland(ip.x, ip.y,
                Constants.WIDTH_PLAYER_GUN_ISLAND,
                Constants.HEIGHT_PLAYER_GUN_ISLAND, true);

        return new Pair<Island, Island>(houseIsland, gunIsland);
    }

    public Array<Player> makePlayers(Array<Pair<Island, Island>> confs) {
        if (confs.size < 2) {
            throw new IllegalArgumentException("Must have at least two players");
        }

        final Array<Player> players = new Array<Player>(confs.size);

        for (int i = 0; i < confs.size; i++) {
            Pair<Island, Island> conf = confs.get(i);
            Island hpI = conf._1;
            Island ipI = conf._2;

            Vector2 hp = new Vector2(hpI.body.getPosition().x, hpI.body.getPosition().y + (Constants.HEIGHT_PLAYER_HOUSE - 0.3f));
            Vector2 ip = new Vector2(ipI.body.getPosition().x, ipI.body.getPosition().y + (Constants.HEIGHT_PLAYER_GUN - 2.0f));

            House house = makeHouse(hp.x, hp.y, i);
            Cannon cannon = makeCannon(ip.x, ip.y, i);

            players.add(new Player(house, cannon, hpI, ipI, i));
        }

        return players;
    }

    public Island makeIsland(float x, float y, float width, float height, boolean moves) {
        return new Island(world, x, y, width, height, islandTex, moves);
    }

    private House makeHouse(float x, float y, int playerID) {
        return new House(world, x, y,
                Constants.WIDTH_PLAYER_HOUSE,
                Constants.HEIGHT_PLAYER_HOUSE, houseTex,
                Constants.PLAYER_HOUSE_MAX_HP_START, playerID);
    }

    private Cannon makeCannon(float x, float y, int playerID) {
        return new Cannon(world, x, y,
                Constants.WIDTH_PLAYER_GUN,
                Constants.HEIGHT_PLAYER_GUN,
                cannonTex,
                Constants.PLAYER_GUN_MAX_HP_START,
                Constants.PLAYER_GUN_MAX_FORCE_START,
                playerID);
    }

}
