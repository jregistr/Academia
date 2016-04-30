package com.jeff.game.castlesmack.system;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.models.gameplay.Player;
import com.jeff.game.castlesmack.models.items.Island;
import com.jeff.game.castlesmack.util.builders.GameBuilder;

public class GameManager implements Disposable {

    private final World world;
   /* private final Array<Island> islands;

    private final Array<Player> players;*/


    public GameManager(World world, boolean p1AI, int additionalPlayers, ObjectMap<String, Texture> objectTextures) {
        this.world = world;

       /* islands = GameBuilder.makeIslands(world, objectTextures);
        players = GameBuilder.makePlayers(world, objectTextures);*/
    }


    @Override
    public void dispose() {

    }
}
