package com.jeff.game.pathfinder.grid


data class Cell(val cost: Int = -1) : Comparable<Cell> {

    override operator fun compareTo(other: Cell): Int {
        if (cost < other.cost) {
            return -1
        } else if (cost == other.cost) {
            return 0
        } else {
            return 1
        }
    }

}