package com.jeff.chaser.models.components.ai.states

import com.jeff.chaser.models.util.SeekingInfo

/**
  * Ai state component to attach to ai in seeking state
  */
class SeekingComponent extends AiStateComponent {
  val seekingInfo: SeekingInfo = new SeekingInfo
}
