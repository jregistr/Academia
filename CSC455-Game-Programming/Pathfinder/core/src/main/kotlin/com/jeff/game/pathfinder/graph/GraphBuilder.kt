package com.jeff.game.pathfinder.graph

import com.jeff.game.pathfinder.grid.Cell
import com.jeff.game.pathfinder.grid.Grid
import com.jeff.game.pathfinder.util.option.Some
import java.util.*

/**
 * Class to handle building a graph.
 */
object GraphBuilder {

    fun from(obj: Grid): Graph {
        val grid = obj.grid
        val map = HashMap<Pair<Int, Int>, Node>()
        grid.forEach { line ->
            line.forEach { cell ->
                if (cell.cost != -1)
                    map.put(Pair(cell.y, cell.x), Node(cell.cost))
            }
        }

        map.forEach { pair, node ->
            val cell = grid[pair.first][pair.second]
            val neighIndicies = neighborsOf(cell, grid)
            val neighs = ArrayList<Edge>()
            neighIndicies.forEach { neighs.add(Edge(node, map[it]!!)) }
            node.edges = Some(neighs)
        }
        return Graph(map)
    }

    private fun neighborsOf(cell: Cell, grid: List<List<Cell>>): List<Pair<Int, Int>> {
        val buffer = ArrayList<Pair<Int, Int>>()
        val maxY = grid.size
        val maxX = grid[0].size
        val y = cell.y
        val x = cell.x

        fun addNei(cellAt: Cell): Unit {
            if (cellAt.teleTo.defined()) {
                val otherEnd = cellAt.teleTo.get()
                buffer.add(Pair(otherEnd.y, otherEnd.x))
            } else {
                if (cellAt.cost != -1)
                    buffer.add(Pair(cellAt.y, cellAt.x))
            }
        }

        if (x + 1 < maxX) {
            addNei(grid[y][x + 1])
        }
        if (y + 1 < maxY) {
            addNei(grid[y + 1][x])
        }
        if (x - 1 >= 0) {
            addNei(grid[y][x - 1])
        }
        if (y - 1 >= 0) {
            addNei(grid[y - 1][x])
        }
        return buffer
    }

}