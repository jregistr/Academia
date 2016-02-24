package controllers

import Dijks.Graph
import com.google.gson.Gson
import com.google.gson.JsonArray
import models.Vertex

class GraphController {

    def index() {
        //Set<String> urls = ["uyuuyy","zzzz","weqweq","poofl","dawdawd","vvvvv","aaad22"]
        def urls = Vertex.getAll().url
       /* Random random = new Random()
        for(int i = 0; i < 1000; i ++){
            urls.add(Long.toString(random.nextLong()))
        }*/
        render(view: 'graphing',model: [urls:urls])
    }

    def findPath(String start, String end){
        println "Path From:" + start + "\nTo:" + end
        Graph graph = new Graph()
        graph.findPath(start)

        /*def verts = Vertex.getAll()
        println "Num of Verts:" + verts.size()
        verts.each {x->
            def thePath = graph.backtrace(x.url)
            if(!x.url.equals("http://en.wikipedia.org/wiki/Hash_table") && thePath.size() == 1){
                throw new IllegalArgumentException("Something wrong");
            }
        }*/

        def trace = graph.backtrace(end)
        JsonArray array = new JsonArray()
        if(trace){
            trace.each {x->
                array.add(x.toJsonObject())
            }
        }
        render(contentType: "application/json",text:array)
    }
//new Graph().kruskals()
    def spanning(){
        println "Spanning"
        render(contentType: "application/json",text:"{\"kruskals\" :" + new Graph().kruskals() + "}")
    }

}
