package Application

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.GridPane

import javafx.scene.text.Font
import javafx.stage.Stage
import metro.metro.MTA

/**
 * @author Jeff Registre
 */
class MetroSimulator extends Application{

    GridPane grid
    Label sta1Lb, sta2Lb, tLb
    ProgressBar p1, p2, p3
    Button goButton


    @Override
    void start(Stage primaryStage) throws Exception {
        configure()
        grid.getChildren().addAll(sta1Lb,tLb,sta2Lb, p1, p2, p3, goButton)

        primaryStage.with {
            setTitle("Metro Simulator")
            setWidth(600)
            setHeight(368)
            setResizable(false)
            setScene(new Scene(grid, getWidth(), getHeight()))
        }

        primaryStage.show()
       // sleep(2000)
        MTA mta = MTA.getInstance()
        mta.beginOperations(p1, p2, p3, sta1Lb, sta2Lb, tLb)
        //[mta]*.join()
    }

    private void configure(){
        grid = new GridPane()
        sta1Lb = new Label("Label")
        sta2Lb = new Label("Label 2")
        tLb = new Label("Other lab")
        p1 = new ProgressBar()
        p2 = new ProgressBar()
        p3 = new ProgressBar()
        goButton = new Button()
        goButton.setOnAction({event->
             MTA mta = MTA.getInstance()
            if(mta != null)
                mta << "Go"
        })
        p1.with {
            setPrefHeight(120)
            setPrefWidth(200)
            setProgress(0)
        }
        p2.with {
            setPrefHeight(120)
            setPrefWidth(200)
            setProgress(0)
        }
        p3.with {
            setPrefHeight(120)
            setPrefWidth(200)
            setProgress(0)
        }

        sta1Lb.with {
            setPrefHeight(80)
            setPrefWidth(250)
            setFont(new Font(18))
        }

        sta2Lb.with {
            setPrefHeight(80)
            setPrefWidth(250)
            setFont(new Font(18))
        }

        tLb.with {
            setPrefHeight(80)
            setPrefWidth(250)
            setFont(new Font(18))
        }

        goButton.with {
            setPrefHeight(100)
            setPrefWidth(200)
            setText("Go Now")
        }

        grid.with {
            setPadding(new Insets(10, 10, 10, 10))
            setConstraints(sta1Lb, 0, 0)
            setConstraints(tLb, 0, 2)
            setConstraints(sta2Lb, 0, 4)
            setConstraints(p1, 1, 1)
            setConstraints(p2, 1, 2)
            setConstraints(p3, 1, 3)
            setConstraints(goButton, 2, 4)
        }
    }

}
