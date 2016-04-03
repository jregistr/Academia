package com.jeff.chaser.models.graph

import com.jeff.chaser.models.graph.MarkerType.MarkerType


class Marker(var mkType:MarkerType, var dist:Float, var from:Option[Node], var visited:Boolean)