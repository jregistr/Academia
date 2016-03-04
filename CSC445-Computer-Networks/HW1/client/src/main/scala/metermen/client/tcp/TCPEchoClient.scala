package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.constants.Constants
import metermen.constants.Constants.NANOS_TO_MILIS

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * tcp echo client
  */
class TCPEchoClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {


  override def process(): ListBuffer[(String, List[Double])] = {
    val results = new ListBuffer[(String, List[Double])]()
    output.writeInt(TEST_COUNT * 3)
    output.flush()
    results += (("1B", runTest(1)))
    results += (("32B", runTest(32)))
    results += (("1024B", runTest(1024)))
    socket.close()

    results
  }

  def runTest(size: Int): List[Double] = {
    val buffer: ArrayBuffer[Double] = ArrayBuffer()
    val write = new Array[Byte](size)
    val read = new Array[Byte](size)
    loop(TEST_COUNT, () => {
      output.writeInt(size)
      val b4 = System.nanoTime()
      output.write(write)
      output.flush()
      input.readFully(read)
      buffer += ((System.nanoTime() - b4) * NANOS_TO_MILIS)
    })
    buffer.toList
  }

}

