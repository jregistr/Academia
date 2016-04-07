package com.jeff.chaser.models.graph

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ObjectMap
import com.jeff.chaser.util.Constants

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object GraphBuilder {

  def make() = {
    val wC = 100
    val hC = 50
    val wC2 = wC / 2.0f
    val hC2 = hC / 2.0f

    val cellW = Gdx.graphics.getWidth / wC.toFloat
    val cellH = Gdx.graphics.getHeight / hC.toFloat

    val grid = new ObjectMap[(Int, Int), Node]()

    for (y <- 0 until hC.toInt) {
      val h = cellH * y
      for (x <- 0 until wC.toInt) {
        val w = cellW * x
        val node = new Node(1, w + wC2, h + hC2, w,
          w + cellW, h, h + cellH)
        grid.put((y, x), node)
      }
    }
/*
    //grid.foreach(out => println(s"${out.value.fromX}, ${out.value.fromY}"))


    val grid = new ObjectMap[(Int, Int), Node]()

    pgrid.foreach(out => {
      if (!(out.value.fromX >= Constants.HOUSE_X && out.value.fromX <= Constants.HOUSE_X + Constants.HOUSE_WIDTH &&
        out.value.fromY >= Constants.HOUSE_Y && out.value.fromY <= Constants.HOUSE_Y + Constants.HOUSE_HEIGHT)) {
        grid.put(out.key, out.value)
      }
    })*/

    def make(y: Int, x: Int, from: Node): Edge = {
      val node = grid.get((y, x))
      if (node != null) {
        new Edge(from, node)
      } else {
        null
      }

      /*node match {
        case null => throw new NullPointerException
        case _ => new Edge(from, node)
      }*/
    }

    def addNei(node: ((Int, Int), Node)) = {
      val list = new ListBuffer[Edge]()
      val pos = node._1
      if (pos._2 + 1 < wC) {
        val out = make(pos._1, pos._2 + 1, node._2)
        if (out != null) {
          list += out
        }
      }

      if (pos._1 + 1 < hC) {
        val out = make(pos._1 + 1, pos._2, node._2)
        if (out != null) {
          list += out
        }
      }

      if (pos._2 - 1 >= 0) {
        val out = make(pos._1, pos._2 - 1, node._2)
        if (out != null) {
          list += out
        }
      }

      if (pos._1 - 1 >= 0) {
        val out = make(pos._1 - 1, pos._2, node._2)
        if (out != null) {
          list += out
        }
      }
      node._2.edges = list.toList
    }

    grid.foreach(p => addNei((p.key, p.value)))

    grid.values().toArray
  }


}
