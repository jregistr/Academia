package com.jeff.chaser.models.components.util

import com.badlogic.ashley.core.{Component, Entity, Family}


class AttachedComponent(val familyRequirement: Family, val entity: Entity) extends Component
