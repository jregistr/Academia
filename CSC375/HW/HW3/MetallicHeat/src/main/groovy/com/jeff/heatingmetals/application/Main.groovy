package com.jeff.heatingmetals.application

import com.jeff.heatingmetals.util.Tile
import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

import java.util.concurrent.ThreadLocalRandom


class Main extends Application {

    private static int width;
    private static int height;
    private static int tileSize;

    private static double lct,rct
    private Tile[][] grid;

    public static void main(String[] args) {
        width = Integer.parseInt(args[0])
        height = Integer.parseInt(args[1])
        tileSize = Integer.parseInt(args[2])
        lct = Double.parseDouble(args[3])
        rct = Double.parseDouble(args[4])
        launch(Main, args)
    }

    private Parent makeUI(){
        Pane root = new Pane()
        root.setPrefSize(width, height)

        final int X_TILES = width/tileSize,
                Y_TILES = height/tileSize

        Tile[][] grid = new Tile[X_TILES][Y_TILES]

        0.upto(X_TILES- 1){x->
            0.upto(Y_TILES -1){y->
                Tile tile = new Tile(x,y,tileSize)
                grid[x][y] = tile
                root.getChildren().add(tile)
            }
        }
        this.grid = grid

        root
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        primaryStage.with {
            setScene(new Scene(makeUI()));
            show()
            new Anvil(grid, lct, rct)
        }
    }

}
