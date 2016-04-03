package com.jeff.chaser.models.components.ai.states

import com.badlogic.gdx.math.Vector2

/**
  * Ai state component to attach to ai in seeking state
  */
class SeekingComponent extends AiStateComponent {
  val target: Vector2 = new Vector2()
}
