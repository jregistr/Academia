package metermen.client

import metermen.client.tcp.{TCPMessagesClient, TCPThroughClient, TCPEchoClient}
import metermen.client.util.HtmlMan
import metermen.client.util.HtmlMan.{Graph, GraphGroup}

import scala.io.StdIn.{readLine => getInput}

/**
  * Client main
  */
object ClientMain {
  def main(args: Array[String]) {
    println("Welcome to client manager.")
    println("What Address to connect to??")
    val add = "localhost"

    println("What port to use for connection??")
    val port = 7000

    println("Now which client do you want to start??")
    println("Choices are: TCP-RTT[0], TCP-Throughput[1]")
    val client = 2

    /*client match {
      case 0 => new TCPEchoClient(add, port, "TCP-RTT").process()
      case 1 => new TCPThroughClient(add, port, "TCP-Though").process()
      case 2 => new TCPMessagesClient(add, port, "TCP-Message").process()
      case _ => throw new IllegalArgumentException
    }*/

    /*val graph = new Graph("Graph_1", List(
      "A" -> 10,
      "B" -> 12,
      "C" -> 30,
      "D" -> 17
    ))

    val graph2 = new Graph("Graph_2", List(
      "E" -> 7,
      "F" -> 2,
      "G" -> 40,
      "H" -> 10
    ))

    val graphGroup = new GraphGroup("Group_1", List(graph, graph2))

    val grap3 = new Graph("Graph_1", List(
      "A" -> 10,
      "B" -> 12,
      "C" -> 30,
      "D" -> 17
    ))

    val grap4 = new Graph("Graph_2", List(
      "A" -> 10,
      "B" -> 12,
      "C" -> 30,
      "D" -> 17
    ))


    HtmlMan.generatePage("Grapherz", List(graphGroup, new GraphGroup("Group_2", List(grap3, grap4))))*/

  }
}
