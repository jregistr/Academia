package metermen.client.udp

import java.net.SocketTimeoutException

import com.jeff.dsl.util.Util._
import metermen.constants.Constants
import metermen.constants.Constants.{MILIS_TO_NANOS, NANOS_TO_MILIS, NANOS_TO_SECONDS}

import scala.collection.mutable.ListBuffer

/**
  * Class to make rtt client
  */
class UDPClientRTT(size: Int, localPort: Int, destUri: String, destPort: Int, val halfTime:Boolean)
  extends UDPClient(size, localPort, destUri, destPort) {

  override def process(): (String, List[Double]) = {
    val buffer = new ListBuffer[Double]

    var i = 0
    while (i < TEST_COUNT) {
      println("I:" + i)
      resetKeeper()
      val b4 = System.nanoTime()
      var collectedTimeouts = 0l
      writePackets.foreach(writePacket => {
        loop(writePacket._2, () => {
          socket.send(writePacket._1)
        })
      })
      socket.setSoTimeout(TIME_OUT)
      while (!keeper.forall(packG => {
        packG._2 == readPackets.get(packG._1).get
      })) {
       // println("Checking")
        readPackets.foreach(packetGroup => {
          val packet = packetGroup._1
          var keeperCount = keeper.get(packet).get
          val left = packetGroup._2 - keeperCount
        //  println(s"Try to get back:$left")
          var stop = false
          var p = 0
          while (p < left && !stop) {
            try {
              socket.receive(packet)
              keeperCount += 1
              keeper += packet -> keeperCount
           //   println(s"Got Back:${keeper.get(packet).get}")
            } catch {
              case s: SocketTimeoutException =>
            //    println("Failed to get")
                collectedTimeouts += (TIME_OUT * MILIS_TO_NANOS)
                stop = true
              case e:Throwable => throw e
            }
            p += 1
          }
        })

        readPackets.foreach(packetGroup => {
          val readPack = packetGroup._1
          val left = packetGroup._2 - keeper.get(readPack).get
         // println(s"Must Send back:$left")
          val writePack = writePacketForSize(readPack.getLength)
          if (left > 0) {
            loop(left, () => {
              //  println("Send")
              socket.send(writePack)
            })
          }
        })
      }
      val delta = (System.nanoTime() - b4) - collectedTimeouts
      if(halfTime){
        buffer += size / ((delta * NANOS_TO_SECONDS) * 0.5f)
      }else{
        buffer += (delta * NANOS_TO_MILIS)
      }
      i+=1
    }

    (Constants.bytesToSize(size), buffer.toList)
  }


}
