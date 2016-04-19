package com.jeff.megaupload.server

import java.io.ByteArrayOutputStream

import scala.collection.mutable.ListBuffer


object Main {
  def main(args: Array[String]) {
    val window = new Array[Array[Byte]](100)
    window(0) = Array(1.toByte)
    window(1) = Array(2.toByte)
    window(2) = Array(3.toByte)
    window(4) = Array(5.toByte)
    window(5) = Array(6.toByte)

    print("[")
    window.foreach(p => {
      if (p != null) {
        p.foreach(pp => {
          print(pp + ",")
        })
      } else {
        print("null,")
      }
    })
    print("]\n")


    var last = -1
    val buffer = new ListBuffer[Array[Byte]]
    val stream = new ByteArrayOutputStream
    last = slideWindow(window, buffer, stream, last)

    print("[")
    buffer.foreach(p => {
      if (p != null) {
        p.foreach(pp => {
          print(pp + ",")
        })
      }
    })
    print("]\n")
    println(s"STREAM OUT SIZE:${stream.toByteArray.length}")

    last = slideWindow(window, buffer, stream, last)
    last = slideWindow(window, buffer, stream, last)

    window(3) = Array(4.toByte)

    last = slideWindow(window, buffer, stream, last)

    print("[")
    buffer.foreach(p => {
      if (p != null) {
        p.foreach(pp => {
          print(pp + ",")
        })
      }
    })
    print("]\n")

    println(last)
    println(s"STREAM OUT SIZE:${stream.toByteArray.length}")

  }

  private def slideWindow(window: Array[Array[Byte]], output: ListBuffer[Array[Byte]], stream: ByteArrayOutputStream, lastHighest: Int): Int = {
    val start = lastHighest + 1
    var current = window(start)
    var highest = lastHighest

    for (i <- start until window.length if current != null) {
      current = window(i)
      if (current != null) {
        output += current
        stream.write(current)
        highest = i
      }
    }
    highest
  }
}
