package com.jeff.chaser.models.graph

import com.badlogic.gdx.utils.{Array => LibArray}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object Graph {

  private val graph: LibArray[Node] = GraphBuilder.make()

  def findPath(from: (Float, Float), to: (Float, Float)): Path = {
    clear()
    val nodeFrom = graph.find(node => node.within(from._1, from._2))
    val nodeTo = graph.find(node => node.within(to._1, to._2))

    if (nodeFrom.isDefined && nodeTo.isDefined) {
      findPath(nodeFrom.get, nodeTo.get)
    } else {
      null
    }
  }

  private def findPath(start: Node, end: Node): Path = {
    val minKeep = new ListBuffer[Node]()
    if (start.edges != null && start.edges.nonEmpty) {
      start.marker = new Marker(MarkerType.SRC, 0f, None, true)
      val neights = start.edges
      neights.foreach(neiEdge => {
        neiEdge.to.marker = new Marker(MarkerType.REG, neiEdge.from.cost,
          Some(start), false)
        minKeep += neiEdge.to
      })

      def pop(): Option[Node] = {
        if (minKeep.nonEmpty) {
          minKeep.sortBy(node => node.marker.dist)
          Some(minKeep.remove(0))
        } else {
          None
        }
      }

      var curNodeOpt: Option[Node] = None
      var found = false
      while (minKeep.nonEmpty && !found) {
        curNodeOpt = pop()
        if (curNodeOpt.isDefined) {
          if (curNodeOpt.get == end) {
            found = true
          } else {
            curNodeOpt.get.marker.visited = true
            curNodeOpt.get.marker.mkType = MarkerType.VISITED
            addConnectedNodes(curNodeOpt.get, minKeep)
          }
        } else {
          throw new IllegalArgumentException
        }
      }
    }
    backTrace(start, end)
  }

  private def clear() = graph.foreach(n => n.marker = null)

  private def addConnectedNodes(node: Node, minKeep: ListBuffer[Node]): Unit = {
    val edges = node.edges
    if (edges != null) {
      val possibles = edges.filter(
        edge => (edge.to.marker == null) ||
          (edge.to.marker != null && !edge.to.marker.visited)
      )

      if (possibles.nonEmpty) {
        possibles.foreach(possible => {
          val cn = possible.to
          cn.marker match {
            case null =>
              cn.marker = new Marker(MarkerType.REG, node.marker.dist,
                Some(node), false)
              minKeep += cn
            case _ =>
              val newDist = node.marker.dist + possible.from.cost
              if (newDist < cn.marker.dist) {
                cn.marker.dist = newDist
                cn.marker.from = Some(node)
              }
          }
        }
        )
      }
    }
  }

  private def backTrace(start: Node, end: Node): Path = {
    val array = new LibArray[(Float, Float)]()
    if (end.marker != null && end.marker.from != null) {
      end.marker.mkType = MarkerType.DST
      var part = end.marker.from
      while (part.isDefined) {
        array.add((part.get.cX, part.get.cY))
        part = part.get.marker.from
      }
    }
    new Path(array)
  }

}
