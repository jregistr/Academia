package com.jeff.game.pathfinder.graph

import java.util.ArrayList


class Node(val cost: Int = -1, val edges: List<Edge>) {

    fun connectedCells(): List<Node> {
        val builder = ArrayList<Node>()
        edges.forEach { builder += it.to }
        return builder.toList()
    }

}