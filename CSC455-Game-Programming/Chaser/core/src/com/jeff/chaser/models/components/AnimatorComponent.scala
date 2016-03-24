package com.jeff.chaser.models.components

import com.badlogic.gdx.graphics.g2d.Animation


class AnimatorComponent(initState: Any, var nextState: Any, states: Map[Any, Animation]) {

  var curState = initState

}
