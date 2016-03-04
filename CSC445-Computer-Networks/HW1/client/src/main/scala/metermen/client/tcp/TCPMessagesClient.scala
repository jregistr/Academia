package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.constants.Constants
import metermen.constants.Constants.NANOS_TO_MILIS

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Class to make message client.
  */
class TCPMessagesClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val read = new Array[Byte](1)

  override def process(): ListBuffer[(String, List[Double])] = {
    val results = new ListBuffer[(String, List[Double])]()
    output.writeInt(TEST_COUNT * 3)
    output.flush()

    results += (("4 x 256KB", runTest(4, 256000)))
    results += (("2 x 512KB", runTest(2, 512000)))
    results += (("1 x 1024KB", runTest(1, 1024000)))

    socket.close()
    results
  }

  def runTest(tests: Int, size: Int): List[Double] = {
    val buffer: ArrayBuffer[Double] = ArrayBuffer()
    val write = new Array[Byte](size)

    loop(TEST_COUNT, () => {
      output.writeInt(tests)
      output.writeInt(size)

      val b4 = System.nanoTime()
      loop(tests, () => {
        output.write(write)
      })
      output.flush()

      input.readFully(read)
      buffer += ((System.nanoTime() - b4) * NANOS_TO_MILIS)
    })
    println(buffer.toList)
    buffer.toList
  }

}
