package com.jeff.chaser.models.graph


class Node(val cost: Float, val cX: Float, val cY: Float, val fromX: Float, val toX: Float,
           val fromY: Float, val toY: Float) {

  var edges: List[Edge] = _
  var marker: Marker = _

  def within(x: Float, y: Float): Boolean = (x >= fromX && x <= toX) && (y >= fromY && y <= toY)

}
