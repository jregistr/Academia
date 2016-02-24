package thewatchers

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Main extends Application{


    public static GUIMan guiMan

    public static void main(String[] args){
        final double lct = 100000, rct = 50000
        final int w = 1400, h = 700, ts = 5
        final int xTiles = w/ts, yTiles = h/ts

        //RHO:24
        //PI:32
        //WOLF:64

        int totalPoolCount = 32 + 64;
        double fP = 32.0/totalPoolCount

        int firstStart = 0
        int firstEnd = xTiles * fP

        int secondStart = firstEnd + 1
        int secondEnd = xTiles -1


        guiMan = new GUIMan(w,h,ts,xTiles,yTiles,lct,rct)
        new Thread({launch(Main, args)}).start()

        Server.instance.create(7000, firstStart, firstEnd, secondStart, secondEnd, yTiles, lct, rct, lct > rct ? lct : rct, guiMan)
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        primaryStage.with{
            setScene(new Scene(guiMan))
            guiMan.startTimer()
            show()
        }
    }

}