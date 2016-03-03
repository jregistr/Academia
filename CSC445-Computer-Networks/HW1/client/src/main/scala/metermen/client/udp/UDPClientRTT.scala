package metermen.client.udp

import scala.collection.mutable.ListBuffer

/**
  * Class to make rtt client
  */
class UDPClientRTT(size: Int, uri: String, port: Int, name: String) extends UDPClient(size, uri, port, name) {

  override def process(): (String, List[Double]) = {
    val buffer = new ListBuffer[Double]

    (name, buffer.toList)
  }

}
