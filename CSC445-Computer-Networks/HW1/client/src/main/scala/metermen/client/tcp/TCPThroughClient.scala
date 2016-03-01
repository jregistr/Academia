package metermen.client.tcp

import java.util

import com.jeff.dsl.util.Util._
import metermen.client.util.CSVMan
import metermen.constants.Constants.NANO_TO_MILIS

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

/**
  * Class for tcp throughput measure client.
  */
class TCPThroughClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val tc = 100
  private var oneByteAv: Long = _

  override def process(): Unit = {
    {
      val buffer = new Array[Byte](1)
      var total: Long = 0
      output.writeInt(tc)
      loop(tc, () => {
        val b4 = System.nanoTime()
        output.write(buffer)
       // output.flush()
        input.read(buffer)
        total += (System.nanoTime() - b4) / 2
      })
      oneByteAv = total / tc
    }

    val results = new ListBuffer[(Int, List[Double])]()

    {

      output.writeInt(tc * 5)
      results += ((100, runTests(64000)))
      results += ((100, runTests(64000)))
      results += ((100, runTests(64000)))
      results += ((100, runTests(64000)))
      results += ((100, runTests(64000)))


    }
    socket.close()
  }

  private def runTests(size: Int): List[Double] = {
    val buffer: ArrayBuffer[Double] = ArrayBuffer()
    val write = new Array[Byte](size)
    util.Arrays.fill(write, 1.toByte)
    val read = new Array[Byte](1)
    loop(tc, (iteration) => {
      println(size)
      println("Iter:" + iteration)
      output.writeInt(size)
     // output.flush()
      val b4 = System.nanoTime()

      output.write(write, 0, size)
      output.flush()
      //input.read(new Array[Byte](1))
      input.readByte()
      buffer += Math.abs(((System.nanoTime() - b4) - oneByteAv) * NANO_TO_MILIS)
    })
    println(buffer.toList)
    buffer.toList
  }

}
