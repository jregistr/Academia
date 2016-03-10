package com.jeff.game.pathfinder.grid

import com.jeff.game.pathfinder.util.option.*
import java.util.ArrayList

/**
 * Class to represent a cell in a grid.
 */
class Cell(val y: Int, val x: Int, val cost: Int = -1) {

    var teleTo: Option<Cell> = None()
    set(value) {
        if(!field.defined()){
            field = value
        }else{
            throw IllegalStateException()
        }
    }

    override fun toString(): String {
        return "[$y,$x, ${
            if(teleTo.defined()){
                "T"
//                "(${teleTo.get().y}, ${teleTo.get().x})"
            }else if(cost == -1){
                "F"
            }else{
                cost.toString()
            }
        }]"
    }
}