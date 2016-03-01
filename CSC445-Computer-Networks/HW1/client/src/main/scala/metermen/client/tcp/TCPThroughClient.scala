package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.constants.Constants.NANOS_TO_MILIS

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Class for tcp throughput measure client.
  */
class TCPThroughClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val tc = 100000
  private var oneByteAv: Long = _

  override def process(): ListBuffer[(String, List[Double])] = {
    {
      val buffer = new Array[Byte](1)
      var total: Long = 0
      output.writeInt(tc)
      loop(tc, () => {
        val b4 = System.nanoTime()
        output.write(buffer)
        output.flush()
        input.read(buffer)
        total += (System.nanoTime() - b4) / 2
      })
      oneByteAv = total / tc
    }

    val results = new ListBuffer[(String, List[Double])]()

    {
      output.writeInt(tc * 5)
      results += (("1K", runTests(1000)))
      results += (("16K", runTests(16000)))
      results += (("64K", runTests(64000)))
      results += (("256K", runTests(256000)))
      results += (("1M", runTests(1000000)))
    }
    socket.close()

    results
  }

  private def runTests(size: Int): List[Double] = {
    val buffer: ArrayBuffer[Double] = ArrayBuffer()
    val write = new Array[Byte](size)
    val read = new Array[Byte](1)
    loop(tc, (iteration) => {
      output.writeInt(size)
      val b4 = System.nanoTime()
      output.write(write)
      output.flush()
      input.read(read)
      buffer += ((System.nanoTime() - b4) - oneByteAv) * NANOS_TO_MILIS
    })
    buffer.toList
  }

}
