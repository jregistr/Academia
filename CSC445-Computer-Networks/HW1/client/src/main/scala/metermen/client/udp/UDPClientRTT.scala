package metermen.client.udp

import java.net.SocketTimeoutException

import metermen.constants.Constants
import metermen.constants.Constants.NANOS_TO_MILIS

import scala.collection.mutable.ListBuffer

/**
  * Class to make rtt client
  */
class UDPClientRTT(size: Int, localPort: Int, destUri: String, destPort: Int)
  extends UDPClient(size, localPort, destUri, destPort) {

  override def process(): (String, List[Double]) = {
    val buffer = new ListBuffer[Double]

    var i = 0
    while (i < TEST_COUNT) {

      val b4 = System.nanoTime()
      writePackets.foreach(packet => {
        socket.send(packet)
      })

      socket.setSoTimeout(TIME_OUT)
      try {
        readPackets.foreach(packet => socket.receive(packet))
        buffer += (System.nanoTime() - b4) * NANOS_TO_MILIS
        i += 1
      } catch {
        case s: SocketTimeoutException =>
        case e: Exception => throw e
      }
    }

    (Constants.bytesToSize(size), buffer.toList)
  }


}
