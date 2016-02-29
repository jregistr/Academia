package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.client.util.CSVMan

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * tcp echo client
  */
class TCPEchoClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val testCount = 1000

  override def process(): Unit = {
    val results = new ListBuffer[(Int, List[Long])]()
    results += ((1, runTest(1)))
    results += ((32, runTest(32)))
    results += ((1024, runTest(1024)))
    socket.close()
    CSVMan.write(fileName(), results)
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

}

