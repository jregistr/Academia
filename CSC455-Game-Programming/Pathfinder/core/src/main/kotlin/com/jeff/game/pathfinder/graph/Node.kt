package com.jeff.game.pathfinder.graph

import com.jeff.game.pathfinder.grid.Cell
import com.jeff.game.pathfinder.util.option.None
import com.jeff.game.pathfinder.util.option.Option
import com.jeff.game.pathfinder.util.option.Some
import java.util.ArrayList

/**
 * Class to represent a node.
 * @constructor Default constructor
 */
class Node(val y:Int, val x:Int, val cost:Int) {

    var marker: Option<Marker> = None()
    var edges: Option<List<Edge>> = None()
    set(value) {
        if(!edges.defined()){
            edges = value
        }else{
            throw IllegalStateException()
        }
    }

    /**
    * Method to return list of cells connected from this node by an edge.
     */
    fun connectedCells(): Option<List<Node>> {
        if(edges.defined()){
            val builder = ArrayList<Node>()
            edges.get().forEach { builder.add(it.to) }
            return Some(builder.toList())
        }else{
            return None()
        }
    }

}