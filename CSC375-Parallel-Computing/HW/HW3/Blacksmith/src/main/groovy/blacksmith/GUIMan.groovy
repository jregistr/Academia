package blacksmith

import groovyx.gpars.scheduler.Scheduler
import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.util.Pair


class GUIMan extends Parent{

    private Map<String,Pair<Cell,Color>> jobs = new HashMap<>()

    public static final int screenWidth = 1600
    public static final int screenHeight = 800
    public static final int tileSize = 10

    private Pane root
    final int hCount, vCount
    boolean running = true

    private static GUIMan instance

    static GUIMan getInstance() {
        if(instance == null)
           instance = new GUIMan(screenWidth, screenHeight, tileSize)
        instance
    }

    private GUIMan(int width, int height, int ts) {
        root = new Pane()
        final int X_TILES = width/ts,
              Y_TILES = height/ts
        hCount = X_TILES
        vCount = Y_TILES

        println "X:$Y_TILES,Y:$X_TILES"

        root.with {
            setPrefSize(width, height)
            0.upto(Y_TILES-1){y->
                0.upto(X_TILES-1){x->

                    Cell cell = new Cell(x,y,ts)
                    Color color
                    if((x == 0 && y == 0)){//left
                        color = Smithy.calcColor(Smithy.lct)
                    }else if(x == X_TILES-1 && y == Y_TILES-1){
                        color = Smithy.calcColor(Smithy.rct)
                    }else {
                        color = cell.color
                    }

                    jobs.put("$y,$x", new Pair<Cell, Color>(cell, color))
                    getChildren().add(cell)
                }
            }
        }

        new Scheduler().execute({
            while (running){
                sleep(1000)
                doRender()
            }
        })

    }

    private void doRender(){
        Map<String,Pair<Cell,Color>> workLoad = (Map<String,Pair<Cell,Color>>)jobs.clone()
        workLoad.values().each {Pair<Cell,Color> pair ->
            pair.key.color = pair.value
        }
    }

    public void addGUIUpdate(int y, int x, Color color){
        Pair<Cell,Color> current = jobs.get("$y,$x")
        jobs.put("$y,$x", new Pair<Cell, Color>(current.key, color))
    }

}
