package com.jeff.game.castlesmack.system;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.util.constant.Constants;

public class GameManager implements Disposable {

    private final World world;
    private Array<Island> islands;

    public GameManager(World world, boolean p1Ai, boolean p2Ai, ObjectMap<String, Texture> objectTextures) {
        this.world = world;

        Island p1HouseIsland = new Island(world, 5, 5, Constants.WIDTH_PLAYER_ISLAND, Constants.HEIGHT_PLAYER_ISLAND,
                new TextureRegion(objectTextures.get(Constants.TexConstants.ISLAND)), true);
        islands.add(p1HouseIsland);
    }

    @Override
    public void dispose() {

    }
}
