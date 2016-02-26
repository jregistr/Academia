package metermen.clients.tcp

import java.io._
import java.net.{InetAddress, Socket}
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

import com.jeff.dsl.util.Util._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * tcp echo client
  */
class TCPEchoClient(private val socket: Socket) {

  private val input = new DataInputStream(socket getInputStream())
  private val output = new DataOutputStream(socket getOutputStream())
  private val name = socket.getLocalAddress.toString.concat(
    String.format(":%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date())))

  private val testCount = 1000

  def process(): Unit = {
    val results = new ListBuffer[(Int, List[Long])]()
    results += ((1, runTest(1)))
    results += ((32, runTest(32)))
    results += ((1024, runTest(1024)))
    socket.close()
    writeToFile(results)
  }

  def runTest(size: Int): List[Long] = {
    val buffer: ArrayBuffer[Long] = ArrayBuffer()
    loop(testCount, () => {
      output.writeInt(size)
      output.write(new Array[Byte](size))
      output.flush()
      val b4 = System.nanoTime()
      input.read(new Array[Byte](size))
      buffer += (System.nanoTime() - b4)
    })
    buffer.toList
  }

  def writeToFile(results: ListBuffer[(Int, List[Long])]): Unit = {
    val file = Paths.get(s"./$name.csv").toFile
    file.createNewFile()
    file.setWritable(true)

    val writer = new PrintWriter(file)
    val iters = new ListBuffer[List[Long]]()
    var count: Int = 0
    results.foreach((x) => {
      writer.write(if (count != 0) s",${x._1}" else s"${x._1}")
      count += 1
      iters += x._2
    })
    writer.write("\n")

    loop(iters.head.size, (i) => {
      loop(iters.length, (j) => {
        writer.write(if (j == 0) s"${iters(j)(i)}" else s",${iters(j)(i)}")
      })
      writer.write("\n")
    })

    writer.flush()
    writer.close()
  }

}

object TCPEchoClient {
  def apply(address: String, port: Int): TCPEchoClient = {
    new TCPEchoClient(new Socket(InetAddress.getByName(address), port))
  }
}
