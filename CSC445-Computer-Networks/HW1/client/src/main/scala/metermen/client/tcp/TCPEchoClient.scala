package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.constants.Constants.NANO_TO_MILIS
import metermen.client.util.CSVMan

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * tcp echo client
  */
class TCPEchoClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val testCount = 10

  override def process(): Unit = {
    val results = new ListBuffer[(Int, List[Double])]()
    output.writeInt(testCount * 3)
    output.flush()
    results += ((1, runTest(1)))
    results += ((32, runTest(32)))
    results += ((1024, runTest(1024)))
    socket.close()
    //CSVMan.write(fileName(), results)
  }

  def runTest(size: Int): List[Double] = {
    val buffer: ArrayBuffer[Double] = ArrayBuffer()
    loop(testCount, () => {
      output.writeInt(size)
      val b4 = System.nanoTime()
      output.write(new Array[Byte](size))
      output.flush()
      input.read(new Array[Byte](size))
      buffer += ((System.nanoTime() - b4) * NANO_TO_MILIS)
    })
    println(buffer.toList)
    buffer.toList
  }

}

