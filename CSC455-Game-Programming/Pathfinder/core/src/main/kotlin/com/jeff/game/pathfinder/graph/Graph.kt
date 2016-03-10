package com.jeff.game.pathfinder.graph

import com.jeff.game.pathfinder.util.option.None
import com.jeff.game.pathfinder.util.option.Option
import com.jeff.game.pathfinder.util.option.Some
import java.util.*


class Graph internal constructor(val graph: Map<Pair<Int, Int>, Node>) {

    fun clearMarkers(){
        graph.values.forEach {
            it.marker = None()
        }
    }

    fun print() {
        graph.forEach {
            val pos = it.key
            val node = it.value
            print("Node(${pos.first},${pos.second}) -> [")
            node.connectedCells().get().forEach { connected -> print("(${connected.y}, ${connected.x}),") }
            print("]")
            println("")
        }
    }

    fun findPath(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        val minKeep = ArrayList<Node>()
        val origin = graph[from]!!
        val destination = graph[to]!!
        destination.marker = Some(Marker(MarkerType.DEST, Double.MAX_VALUE, None(), false))
        if (origin.edges.defined() && origin.edges.get().size > 0) {
            origin.marker = Some(Marker(MarkerType.SOURCE, 0.0, None(), true))
            val neighs = origin.connectedCells().get()
            neighs.forEach { nei ->
                nei.marker = Some(Marker(MarkerType.REGULAR, nei.cost.toDouble(), Some(origin), false))
                minKeep.add(nei)
            }
        } else {
            return
        }

        val pop: () -> Option<Node> = {
            if (minKeep.size > 0) {
                minKeep.sort { node1, node2 -> node1.marker.get().distance.compareTo(node2.marker.get().distance) }
                Some(minKeep.removeAt(0))
            } else {
                None()
            }
        }

        var curNodeOpt: Option<Node>
        var found = false

        while (minKeep.size > 0 && !found) {
            curNodeOpt = pop()
            if (curNodeOpt.defined()) {
                curNodeOpt.get().marker.get().visited = true
                if (curNodeOpt.get() == destination) {
                    found = true
                }
                addConnectedCells(curNodeOpt.get(), minKeep)
            } else {
                println("Nothing to pop")
                break
            }
        }

        backTrace(destination)
    }

    private fun addConnectedCells(it:Node, minKeep:ArrayList<Node>){
        val neisOpt = it.connectedCells()
        if (neisOpt.defined()) {
            val neighs = neisOpt.get()
            val possibles = neighs.filter { !it.marker.defined() || (it.marker.defined() && !it.marker.get().visited) }
            if (possibles.size > 0) {
                possibles.forEach { possible: Node ->
                    if (!possible.marker.defined()) {
                        //haven't seen this node yet
                        possible.marker = Some(Marker(MarkerType.REGULAR, it.marker.get().distance + possible.cost, Some(it), false))
                        minKeep.add(possible)
                    } else {
                        val newDist = it.marker.get().distance + possible.cost
                        if (newDist < possible.marker.get().distance) {
                            possible.marker.get().distance = newDist
                            possible.marker.get().from = Some(it)
                        }
                    }
                }
            }
        }
    }

    private fun backTrace(end: Node) {
        if (end.marker.defined() && end.marker.get().from.defined()) {
            var part = end.marker.get().from
            while (part.defined()) {
                part.get().marker.get().type = MarkerType.PATH
                part =  part.get().marker.get().from
            }
        }
    }

}