package com.jeff.chaser.util.dsl

import com.badlogic.ashley.core.{Engine, Entity, EntitySystem}


object ExtendEngine {

  implicit class RichEngine(e: Engine) {

    def ++=(systems: Seq[EntitySystem]): Unit = {
      systems.foreach(e.addSystem)
    }

    def +=(entities: Seq[Entity]): Unit = {
      entities.foreach(e.addEntity)
    }

  }

}
