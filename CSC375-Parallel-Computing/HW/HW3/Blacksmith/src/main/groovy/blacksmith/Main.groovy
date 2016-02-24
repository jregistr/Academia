package blacksmith

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Main extends Application{

    private static final double lct = 100000, rct = 50000
    private static GUIMan guiMan

    public static void main(String[] args){
        guiMan = GUIMan.instance
        new Thread({
            launch(Main, args)
        }).start()
        sleep(3000)
        new Smithy(guiMan.hCount, guiMan.vCount, lct, rct).execute()
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        primaryStage.with{
            setScene(new Scene(guiMan))
            show()
        }
    }

    @Override
    void stop() throws Exception {
        System.exit(0)
    }
}