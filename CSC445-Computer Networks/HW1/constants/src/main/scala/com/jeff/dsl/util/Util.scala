package com.jeff.dsl.util

/**
  * Util dSLs
  */
object Util {

  def loop(to:Int, func:()=>Unit):Unit = {
    doLoop(to, (Int) =>{func()})
  }

  def loop(to:Int, func:(Int)=>Unit):Unit = {
    doLoop(to, func)
  }

  private def doLoop(stop:Int, func:(Int)=>Unit):Unit ={
    for(i <- 0 until stop){
      func(i)
    }
  }

}
