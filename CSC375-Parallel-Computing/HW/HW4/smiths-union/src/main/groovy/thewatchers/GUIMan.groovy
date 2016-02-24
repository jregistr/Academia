package thewatchers

import groovyx.gpars.scheduler.Scheduler
import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.util.Pair


class GUIMan extends Parent {

    private final Map<String,Pair<Cell,Color>> jobs = new HashMap<>()
    private final Pane root
    private final float maxTemp
    private  boolean running = true


    GUIMan(int width, int height, int tileSize, int xTiles, int yTiles, double leftCorner, double rightCorner) {
        root = new Pane()
        maxTemp = leftCorner > rightCorner ? leftCorner : rightCorner
        root.with {
            setPrefSize(width, height)
            0.upto(yTiles-1){y->
                0.upto(xTiles-1){x->

                    Cell cell = new Cell(x,y,tileSize)
                    Color color

                    if((x == 0 && y == 0)){//left{
                        color = calculateColor(leftCorner)
                    }
                    else if(x == xTiles-1 && y == yTiles-1){
                        color = calculateColor(rightCorner)
                    }
                    else
                        color = cell.color

                    jobs.put("$y,$x", new Pair<Cell, Color>(cell, color))
                    getChildren().add(cell)
                }
            }
        }
       // guiMan = this
    }

    public void startTimer(){
        new Scheduler().execute({
            while (running){
                sleep(2000)
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

    public void addGUIUpdate(int y, int x, double temp){
        Pair<Cell,Color> current = jobs.get("$y,$x")
        jobs.put("$y,$x", new Pair<Cell, Color>(current.key, calculateColor(temp)))
    }

    private Color calculateColor(double temp){
        double ratio = temp/maxTemp
        if(ratio < 0.05){
            new Color(1,1,1,1)
        }else if(ratio < 0.1){
            new Color(121.0/255, 254.0/255, 254.0/255, 1)
        }else if(ratio < 0.3){
            new Color(0/255, 254.0/255, 121/255, 1)
        }else if(ratio < 0.5){
            new Color(254.0/255, 254.0/255,121.0/255, 1)
        }else if(ratio < 0.8){
            new Color(254.0/255, 254.0/255, 0.0/255, 1)
        }else {
            new Color(1, 0, 0, 1)
        }
    }



}
