package thewatchers

import javafx.application.Platform
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class Cell extends StackPane{

    int x,y,s
    Color color = Color.WHITE

    private Rectangle rectangle = new Rectangle(width: s, height: s)

    Cell(int x, int y, float s) {
        this.x = x
        this.y = y
        this.s = s

        rectangle.setFill(color)
        //rectangle.setStrokeWidth(0)
        rectangle.setStroke(Color.GRAY)
        getChildren().add rectangle
        setTranslateX x * s
        setTranslateY y * s
    }

    void setColor(Color color) {
        this.color = color
        Platform.runLater({
            rectangle.setFill(color)
        })
    }

}