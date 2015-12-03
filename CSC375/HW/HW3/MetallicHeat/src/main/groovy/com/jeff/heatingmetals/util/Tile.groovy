package com.jeff.heatingmetals.util

import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle


class Tile extends StackPane{
    int x,y,s

    private Rectangle rect = new Rectangle(width: s -0.5, height: s- 0.5)


    Tile(int x, int y, int s) {
        this.x = x
        this.y = y
        this.s = s
        rect.setStroke(Color.LIGHTGRAY)

        getChildren().add(rect)
        setTranslateX x * s
        setTranslateY y * s
    }

    private void setX(int x) {}

    private void setY(int y) {}

    private void setS(int s) {}

    public void setRed(int r){rect.setFill(new Color(r,0,0,1))}

}
