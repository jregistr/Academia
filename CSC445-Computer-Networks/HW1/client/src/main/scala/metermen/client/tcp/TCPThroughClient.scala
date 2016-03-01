package metermen.client.tcp

import com.jeff.dsl.util.Util._
import metermen.client.util.CSVMan

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

/**
  * Class for tcp throughput measure client.
  */
class TCPThroughClient(address: String, port: Int, name: String) extends TCPClient(address, port, name) {

  private val tc = 5
  private var oneByteAv: Long = _

  override def process(): Unit = {
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

    val results = new ListBuffer[(Int, List[Long])]()

    {

      output.writeInt(tc * 3)
      results += ((1000, runTests(1000)))
      results += ((16000, runTests(16000)))
      results += ((64000, runTests(64000)))
    }
    socket.close()
    CSVMan.write(fileName(), results)

  }

  private def runTests(size: Int): List[Long] = {
    val buffer: ArrayBuffer[Long] = ArrayBuffer()
    val write = new Array[Byte](size)
    val read = new Array[Byte](1)
    loop(tc, () => {
      val b4 = System.nanoTime()
      output.writeInt(size)
      output.write(write)
      output.flush()
      input.read(read)
      buffer += ((System.nanoTime() - b4) - oneByteAv)
    })
    println(buffer.toList)
    buffer.toList
  }

}
