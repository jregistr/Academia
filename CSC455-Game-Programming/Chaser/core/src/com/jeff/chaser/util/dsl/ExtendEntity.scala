package com.jeff.chaser.util.dsl

import com.badlogic.ashley.core.{Component, Entity}

import scala.collection.Seq


object ExtendEntity {

  implicit class RichEntity(e: Entity) {
    def ++=(comps: Seq[Component]): Unit = {
      comps.foreach(e.add)
    }
  }

}