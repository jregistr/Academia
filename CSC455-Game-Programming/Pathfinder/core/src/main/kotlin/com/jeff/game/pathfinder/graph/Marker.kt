package com.jeff.game.pathfinder.graph

import com.jeff.game.pathfinder.util.option.Option

/**
 * Enum to describe marker type
 */
enum class MarkerType{
    SOURCE,
    DEST,
    PATH,
    REGULAR
}
/**
 * Class to represent a marker for the graph algorithm calculations.
 */
data class Marker(var type: MarkerType, var distance:Double, var from: Option<Node>, var visited:Boolean)