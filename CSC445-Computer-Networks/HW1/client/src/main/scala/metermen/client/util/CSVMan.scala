package metermen.client.util

import java.io.PrintWriter
import java.nio.file.Paths

import com.jeff.dsl.util.Util._

import scala.collection.mutable.ListBuffer

/**
  * Class to manage writing data down to a csv file.
  */
object CSVMan {

  def write(name: String, results: ListBuffer[(Int, List[Long])]): Unit = {
    val file = Paths.get(name).toFile
    file.createNewFile()
    file.setWritable(true)

    val writer = new PrintWriter(file)
    val iters = new ListBuffer[List[Long]]()
    var count: Int = 0
    results.foreach((x) => {
      writer.write(if (count != 0) s",${x._1}" else s"${x._1}")
      count += 1
      iters += x._2
    })

    writer.write("\n")

    loop(iters.head.size, (i) => {
      loop(iters.length, (j) => {
        writer.write(if (j == 0) s"${iters(j)(i)}" else s",${iters(j)(i)}")
      })
      writer.write("\n")
    })

    writer.flush()
    writer.close()

  }

}
