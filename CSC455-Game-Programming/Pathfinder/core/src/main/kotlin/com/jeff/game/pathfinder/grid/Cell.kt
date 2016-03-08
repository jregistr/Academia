package com.jeff.game.pathfinder.grid

import com.jeff.game.pathfinder.util.option.*
import java.util.ArrayList

data class Cell(val x: Int, val y: Int, val cost: Int = -1, val teleTo: Option<Cell>) {

    fun neighbors(maxX: Int, maxY: Int): List<Pair<Int, Int>> {
        val buffer = ArrayList<Pair<Int, Int>>()
        if (x + 1 < maxX) {
            buffer.add(Pair(x + 1, y))
        }
        if (x - 1 >= 0) {
            buffer.add(Pair(x - 1, y))
        }
        if (y + 1 < maxY) {
            buffer.add(Pair(x, y + 1))
        }
        if (y - 1 >= 0) {
            buffer.add(Pair(x, y - 1))
        }
        return buffer
    }

}