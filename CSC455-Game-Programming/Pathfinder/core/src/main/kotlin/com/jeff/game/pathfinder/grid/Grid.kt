package com.jeff.game.pathfinder.grid

import com.jeff.game.pathfinder.util.option.Some
import java.io.File
import java.util.*

/**
 * Class to represent a grid.
 */
class Grid private constructor(val grid: List<List<Cell>>) {

    fun print():Unit{
        grid.forEach {
            it.forEach { cell ->
                print(cell.toString())
            }
            println()
        }
    }

    companion object Builder {
        fun of(file: File): Grid {
            var y = 0
            var teles = HashMap<String, Pair<Int, Int>>()
            val regexTele = Regex("T[0-9]*")
            val regexDigit = Regex("[1-9]{1,1}")
            val regexNonPass = Regex("[F]")
           // println("File:" + file)
            val lines = file.readLines()
            val list = ArrayList<ArrayList<Cell>>()

            lines.forEach { line ->
                val split = line.split(" ")
                val curList = ArrayList<Cell>()
                list.add(curList)
                var x = 0
                split.forEach { part ->
                    if (part.matches(regexDigit)) {//regular cell
                        curList.add(Cell("bla", y, x, part.toInt()))
                    } else if (part.matches(regexTele)) {//teleport cell
                        if(teles.containsKey(part)){//Already encountered otherside of teleport
                            val prev = teles[part]!!
                            var f = Cell(part, prev.first, prev.second, 0)
                            var s = Cell(part, y, x, 0)
                            f.teleTo = Some(s)
                            s.teleTo = Some(f)
                            teles.remove(part)
                            list[prev.first][prev.second] = f
                            curList.add(s)
                        }else{//haven't encountered teleport endpoint
                            teles.put(part, Pair(y, x))
                            curList.add(Cell("bla", y, x, 0))
                        }
                    } else if (part.matches(regexNonPass)) {
                        curList.add(Cell("bla", y, x,-1))
                    } else {
                        throw IllegalArgumentException("Unexpected cell type")
                    }
                    x++
                }
                y++
            }
            return Grid(list)
        }
    }

}