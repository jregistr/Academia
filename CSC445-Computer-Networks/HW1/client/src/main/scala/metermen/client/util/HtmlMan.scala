package metermen.client.util

import java.io.{FileOutputStream, OutputStreamWriter, BufferedWriter, File}
import java.nio.file.attribute.{BasicFileAttributes, FileAttribute}
import java.nio.file.{Files, Paths}

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.util.Random

/**
  * Class to write the data onto an html page.
  */
object HtmlMan {

  private val GLYPHS = List(
    "glyphicon glyphicon-send",
    "glyphicon glyphicon-plane",
    "glyphicon glyphicon-cloud",
    "glyphicon glyphicon-signal"
  )

  private val random = new Random()

  class Graph(val name: String, val averages: List[(String, Double)])

  class GraphGroup(val name: String, val graphs: List[Graph])

  //, groups: List[GraphGroup]

  def generatePage(title: String, groups: List[GraphGroup]): Unit = {
    val document: Document = Jsoup.parse(new File(getClass.getClassLoader.getResource("template.html").getFile), "UTF-8")

    document.title(title)
    val dataBuilder = new StringBuilder()
    val groupBuilder = new StringBuilder()
    val makerBuilder = new StringBuilder()
    val linkBuilder = new StringBuilder()

    groups.foreach(group => {
      val graphBuilder = new StringBuilder()
      linkBuilder.append(makeGroupLink(group.name))

      group.graphs.foreach(graph => {
        val graphID = group.name.concat(graph.name)

        dataBuilder.append(makeGraphJson(graph.name, graph.averages) + "\n")
        graphBuilder.append(makeGraphPanel(graphID, graph.name))
        makerBuilder.append(makeCreateBarChart(graphID, graphID, graph.name))
      })
      groupBuilder.append(makeGroup(graphBuilder.mkString))
    })

    // document.body().append(groupBuilder.mkString)
    document.getElementById("groups").append(groupBuilder.mkString)
    document.body().append(makeScriptTag(dataBuilder.mkString))
    document.body().append(makeScriptTag(makeGraphMaker(makerBuilder.mkString)))
    document.getElementById("group-links").append(linkBuilder.mkString)

    //document.body().append(builder.mkString)

    val path = Files.createDirectory(Paths.get(s"./${title.replace(" ", "")}Folder")).toString
    val file = new File(path.concat(s"/${title.replace(" ", "")}.html"))
    file.setWritable(true)
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))
    writer.write(document.outerHtml())
    writer.flush()
  }

  private def makeCreateBarChart(varName: String, canvID: String, dataName: String): String = {
    val builder = new StringBuilder()
    builder.append(s"var $varName = document.getElementById(")
    builder.append("\"" + canvID + "\").getContext(" + "\"2d\");\n")
    builder.append(s"var ${varName}Chart = new Chart($varName).Bar($dataName, options);\n")
    builder.mkString
  }

  private def makeGraphJson(varName: String, averages: List[(String, Double)]): String = {
    val build = new StringBuilder()
    build.append(s"var $varName = {\n")
    build.append("labels:[")
    var first = true
    averages.foreach(x => {
      if (first) {
        first = false
        build.append("\"" + x._1 + "\"")
      } else {
        build.append(",\"" + x._1 + "\"")
      }
    })
    build.append("],\n")

    build.append("datasets:[\n{")
    build.append("label:\"Something\",\n")
    build.append("fillColor:")
    build.append("\"rgba(220,220,220,0.5)\",\n")
    build.append("strokeColor:")
    build.append("\"rgba(220,220,220,0.8)\",\n")
    build.append("highlightFill:")
    build.append("\"rgba(220,220,220,0.75)\",\n")
    build.append("highlightStroke:")
    build.append("\"rgba(220,220,220,1)\",\n")
    build.append("data:[")

    first = true
    averages.foreach(x => {
      if (first) {
        first = false
        build.append("" + x._2)
      } else {
        build.append("," + x._2)
      }
    })

    build.append("]\n")
    build.append("}\n")
    build.append("]\n")
    build.append("}\n")

    build.mkString
  }

  private def makeScriptTag(inside: String): String = {
    val builder = new StringBuilder()
    builder.append("<script>\n")
    builder.append(inside)
    builder.append("\n</script>\n")
    builder.mkString
  }

  private def makeGroupLink(groupName: String): String = {
    val builder = new StringBuilder()
    builder.append("<li> <a href=\"#\">")
    builder.append("<span class=\"" + GLYPHS(random.nextInt(GLYPHS.length)) + "\"></span>")
    builder.append(groupName)
    builder.append("</a>")
    builder.append("</li>")
    builder.mkString
  }

  private def makeGraphPanel(id: String, graphName: String): String = {
    val builder = new StringBuilder()

    builder.append("<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n")
    builder.append("<div class=\"panel panel-default\">\n")
    builder.append("<div class=\"panel-heading\">\n")
    builder.append(graphName + "\n")
    builder.append("</div>\n")

    builder.append("<div class=\"panel-body\">\n")
    builder.append("<canvas id=\"" + id + "\"" + " width=\"300\" height=\"300\"></canvas>\n")
    builder.append("</div>\n")
    builder.append("</div>\n")
    builder.append("</div>\n")

    builder.mkString
  }

  private def makeGroup(inside: String): String = {
    val builder = new StringBuilder()
    builder.append("<div>\n")
    builder.append(inside + "\n")
    builder.append("</div>\n")
    builder.mkString
  }

  private def makeGraphMaker(inside: String): String = {
    val builder = new StringBuilder()
    builder.append("var GraphMaker = (function () {\n")
    builder.append(inside)
    builder.append("})\n")
    builder.mkString
  }

}
