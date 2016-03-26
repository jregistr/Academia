package com.jeff.chaser.models.components.view

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.ObjectMap
import com.jeff.chaser.models.util.AnimInfo


class AnimatorComponent(initState: Object, var nextState: Object, val states: ObjectMap[Object, AnimInfo]) extends Component {

  var curState = initState

}
