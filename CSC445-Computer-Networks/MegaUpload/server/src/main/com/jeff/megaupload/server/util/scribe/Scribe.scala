package com.jeff.megaupload.server.util.scribe

import java.io.FileOutputStream

import akka.actor.{Props, Actor}

/**
  * Class to describe an actor that writes data to a file.
  *
  * @param fileName The name of the file.
  */
class Scribe(fileName: String) extends Actor {

  private val stream = new FileOutputStream(fileName)

  def receive = {
    case Write(data) =>
      stream.write(data)
      stream.flush()

    case Finish =>
      stream.flush()
      stream.close()
      context stop self
  }
}

/**
  * Companion for this actor.
  */
object Scribe {

  /**
    * Method to return the props to create the scribe actor.
    *
    * @param fileName The name of the file.
    * @return The props.
    */
  def props(fileName: String): Props = Props(classOf[Scribe], fileName)
}


